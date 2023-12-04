package com.teamc6.chatSystem.config;


import com.teamc6.chatSystem.serverSocket.ChatServer;
import com.teamc6.chatSystem.serverSocket.ClientHandler;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.serviceImpl.GroupChatServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class Config {
    @Bean
    public ServerSocket serverSocket() throws IOException {
        System.out.println("new socket");
        return new ServerSocket(0);
    }
   // @Bean
   // public ClientHandler clientHandler(){
   //     return new ClientHandler(clientHandler().getGroupChatService());
  // }

//    public ChatServer chatServer() throws IOException {
//        System.out.println("create chat Server");
//        return new ChatServer(chatServer().getMessageService(), getServerSocket());
//    }
}
