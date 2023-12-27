package com.teamc6.chatSystem.serverSocket;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.model.CommandObj;
import com.teamc6.chatSystem.model.InitObj;
import com.teamc6.chatSystem.model.MessageObj;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;


@Getter
@Setter


public class ClientHandler implements Runnable {

    private ChatServer server;
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private long clientId;
    private String clientUsername;

    public ClientHandler(ChatServer server) {
     this.server = server;
    }

    public void init(Socket socket, ChatServer server) {
        try {
            this.server = server;
            this.socket = socket;
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            this.reader = new ObjectInputStream(socket.getInputStream());


            Object obj = reader.readObject();
            InitObj info = (InitObj) obj ;
            this.clientId = info.userId();
            this.clientUsername = info.userName();

        } catch (IOException e) {
            closeEverything(socket, reader, writer);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        while (socket.isConnected()){
            try{
                Object obj = reader.readObject();
                MessageObj messageFromClient = (MessageObj)obj ;
                Message message = new Message(this.clientUsername, messageFromClient.message());
                message.setGroupChat(server.getGroupChatService().findById(server.getGroupChatID()));
                server.getMessageService().save(message);
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void broadcastMessage(Object messageToSend){
        for (ClientHandler clientHandler : server.clientHandlers){
            try{
                clientHandler.writer.writeObject(messageToSend);
                clientHandler.writer.flush();
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
            }
        }
    }

    public  void removeClientHandler(){
        server.clientHandlers.remove(this);
    }

    public void closeEverything(Socket socket, ObjectInputStream reader, ObjectOutputStream writer){
        removeClientHandler();
        try{
            if(reader != null){
                reader.close();
            }
            if(writer != null){
                writer.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
