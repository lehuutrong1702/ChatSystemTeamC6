package com.teamc6.serverSocket;

import com.teamc6.chatSystem.record.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer implements Runnable{
    private final ServerSocket serverSocket;
    public  ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
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
        return new Connection(serverSocket.getInetAddress(),serverSocket.getLocalPort());
    }
}
