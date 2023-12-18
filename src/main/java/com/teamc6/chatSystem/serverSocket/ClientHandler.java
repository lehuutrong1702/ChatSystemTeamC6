package com.teamc6.chatSystem.serverSocket;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.model.MessageObj;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.Socket;


@Getter
@Setter


public class ClientHandler implements Runnable {

    private ChatServer server;
    private Socket socket;
    private ObjectInputStream reader;
    private ObjectOutputStream writer;
    private String clientId;
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

        } catch (IOException e) {
            closeEverything(socket, reader, writer);
        }
    }

    @Override
    public void run(){
        while (socket.isConnected()){
            try{
                System.out.println("Get Messages");
                Object obj = reader.readObject();
                System.out.println(obj.toString());
                MessageObj messageFromClient = (MessageObj)obj ;
                System.out.println(messageFromClient.message());
                Message message = new Message(messageFromClient.userName(), messageFromClient.message());
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

    public  void broadcastMessage(MessageObj messageToSend){
        System.out.println("broadcast");
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
