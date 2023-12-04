package com.teamc6.chatSystem.serverSocket;

import com.teamc6.chatSystem.record.Connection;
import com.teamc6.chatSystem.service.GroupChatService;
import com.teamc6.chatSystem.service.MessageService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class ChatServer implements Runnable{
    public GroupChatService groupChatService;
    public MessageService messageService;
    private static final Map<Long, ChatServer> map = new HashMap<Long, ChatServer>();
    private long groupChatID;
    private ServerSocket serverSocket;
    public  ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
//    public ChatServer(ServerSocket serverSocket) {
//        this.serverSocket = serverSocket;
//    }

    public static ChatServer getChatServer(Long id) {
        return map.get(id);
    }

    public static void setMap(Long id, ChatServer chatServer) {
        chatServer.setGroupChatID(id);
        map.put(id, chatServer);
    }

    public long getGroupChatID() {
        return groupChatID;
    }

    public void setGroupChatID(long groupChatID) {
        this.groupChatID = groupChatID;
    }

    @Override
    public void run(){
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!("+socket.getPort()+")");

                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandlers.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeServerSocket();
        }
    }

    public void closeServerSocket(){
        try{
            if(serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {

        try {
            return new Connection( Inet4Address.getLocalHost().getHostAddress(),serverSocket.getLocalPort());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
