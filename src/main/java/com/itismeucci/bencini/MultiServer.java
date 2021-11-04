package com.itismeucci.bencini;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServer {

    ArrayList<ServerThread> threadList = new ArrayList<>();
    ServerSocket serverSocket = null;

    public void start(){
        try {
            serverSocket = new ServerSocket(6789);
            for(;;){
                System.out.println("1 Server in attesa...");
                Socket socket = serverSocket.accept();
                System.out.println("3 Server socket " + socket);
                ServerThread serverThread = new ServerThread(socket, this);
                serverThread.start();

                threadList.add(serverThread);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'instanza del server");
            System.exit(1);
        }
    }

    public void stop() {

        // per ogni elemento della lista chiudere il socket
        for(int i = 0; i < threadList.size(); i++){
            try {
                threadList.get(i).close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
