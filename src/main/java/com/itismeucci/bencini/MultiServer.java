package com.itismeucci.bencini;

import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            for(;;){
                System.out.println("1 Server in attesa...");
                Socket socket = serverSocket.accept();
                System.out.println("3 Server socket " + socket);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'instanza del server");
            System.exit(1);
        }
    }
}
