package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandler implements Runnable{

    public SocketHandler(Socket so){

    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Socket autoCloseableSocket = s;
        ) {
            
            } 
        } catch (IOException e) {
            System.out.println("Errore di I/O nella gestione del client: " + e.getMessage());
        }

}


