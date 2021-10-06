package com.itismeucci.bencini;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public ServerThread(Socket socket){
        this.client = socket;
    }

    public void run(){
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        for(;;){
            stringaRicevuta = inDalClient.readLine();
            if (stringaRicevuta == null || stringaRicevuta.equals("FINE")) {
                outVersoClient.writeBytes(stringaRicevuta+"=> SERVER IN CHIUSURA" + '\n');
                System.out.println("Echo sul server di chiusura: " + stringaRicevuta);
                break;
            }
            else{
                outVersoClient.writeBytes(stringaRicevuta+" (ricevuta e trasmessa)" + '\n');
                System.out.println("6 Echo sul server: " + stringaRicevuta);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket" + client);
        client.close();
    }
}
