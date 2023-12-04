package com.teamc6.serverSocket;

import com.teamc6.chatSystem.record.Connection;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatServer implements Runnable{
    private static final Map<Long, ChatServer> map = new HashMap<Long, ChatServer>();
    private final ServerSocket serverSocket;
    public  ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static ChatServer getChatServer(Long id) {
        return map.get(id);
    }

    public static void setMap(Long id, ChatServer chatServer) {
        map.put(id, chatServer);
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
