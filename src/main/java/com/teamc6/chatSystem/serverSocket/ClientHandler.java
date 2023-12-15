package com.teamc6.chatSystem.serverSocket;

import com.teamc6.chatSystem.entity.Message;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;



@Getter
@Setter


public class ClientHandler implements Runnable {

    private ChatServer server;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientId;
    private String clientUsername;

    public ClientHandler(ChatServer server) {
     this.server = server;
    }

    public void init(Socket socket, ChatServer server) {
        try {
            this.server = server;
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = reader.readLine();
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            closeEverything(socket, reader, writer);
        }
    }

    @Override
    public void run(){
        String messageFromClient;
        while (socket.isConnected()){
            try{
                messageFromClient = reader.readLine();
                Message message = new Message(clientUsername, messageFromClient);
                message.setGroupChat(server.getGroupChatService().findById(server.getGroupChatID()));
                server.getMessageService().save(message);
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
                break;
            }
        }
    }

    public  void broadcastMessage(String messageToSend){
        System.out.println("broadcast");
        for (ClientHandler clientHandler : server.clientHandlers){
            try{
                if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.writer.write(messageToSend);
                    clientHandler.writer.newLine();
                    clientHandler.writer.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, reader, writer);
            }
        }
    }

    public  void removeClientHandler(){
        server.clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader reader, BufferedWriter writer){
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
