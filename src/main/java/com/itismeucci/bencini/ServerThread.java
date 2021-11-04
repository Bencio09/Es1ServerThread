package com.itismeucci.bencini;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.Socket;

public class ServerThread extends Thread{
    MultiServer multiServer = null;
    Socket client = null;
    String stringaRicevuta = null;
    String stringaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;


    public ServerThread(Socket socket, MultiServer multiServer ){
        this.client = socket;
        this.multiServer = multiServer;
    }



    public void run(){
        try {
            comunica();
        } catch (Exception e) {
           System.out.println("Server chiuso da un altro thread" + '\n');
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
            } else if(stringaRicevuta.equals("STOP")){
                
                System.out.println("Echo sul server di chiusura: " + stringaRicevuta);
                outVersoClient.writeBytes(stringaRicevuta+"=> IL SERVER STA PER ESSERE STOPPATO" + '\n');
                outVersoClient.writeBytes("FERMATI");
                multiServer.stop();
                break;
            }
            else{
                stringaModificata = stringaRicevuta.toUpperCase();
                outVersoClient.writeBytes(stringaModificata+" (ricevuta e trasmessa)" + '\n');
                System.out.println("6 Echo sul server: " + stringaRicevuta);
            }
        }
        if(stringaRicevuta.equals("FINE")) {
           close();
        }
    }


    public void close() throws Exception{
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 Chiusura socket" + client);
        client.close();
    }
}
