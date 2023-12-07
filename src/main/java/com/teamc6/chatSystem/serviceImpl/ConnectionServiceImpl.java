package com.teamc6.chatSystem.serviceImpl;

import ch.qos.logback.core.net.server.Client;
import com.teamc6.chatSystem.record.Connection;
import com.teamc6.chatSystem.serverSocket.ChatServer;
import com.teamc6.chatSystem.serverSocket.ClientHandler;
import com.teamc6.chatSystem.service.ConnectionService;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;

@Service
public class ConnectionServiceImpl implements ConnectionService {

   private ChatServer chatServer;
 //   private ClientHandler clientHandler;
    private GroupChatService groupChatService;
    private MessageService messageService;
    public ConnectionServiceImpl(GroupChatService groupChatService,MessageService messageService) {
     //   this.clientHandler = clientHandler;
        this.messageService = messageService;
        this.groupChatService = groupChatService;
    }

    @Override
    public Connection getConnection(Long id) {
        System.out.println("connection");
        this.chatServer = ChatServer.getChatServer(id);
        if(chatServer == null){ // First connection
            try {
           //     ClientHandler clientHandler = new ClientHandler();
                ServerSocket sk = new ServerSocket(0);
                chatServer = new ChatServer(sk, this.groupChatService,this.messageService);
                Thread thread = new Thread(chatServer);
                ChatServer.setMap(id, chatServer);
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return chatServer.getConnection();
    }
}
