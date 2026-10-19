package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocketHandler implements Runnable{
    private final Socket s;
    Map<String,Integer> disponibilita = new HashMap<>();
    ArrayList<String> user= new ArrayList<>();


    public SocketHandler(Socket so){
        s=so;

        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
    }
 
    @Override
    public void run() {
        String nome;
        boolean var=true;
        String scelta;
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Socket autoCloseableSocket = s;
        ) {
            out.println("Welcome");

            do{
                out.println("Login: ");
                nome =in.readLine();
                
                if(user.contains(nome)){
                    out.println("ERR USERINUSE");
                }else{
                    out.println("OK");
                    var=false;
                }
            }while(var);
            user.add(nome);

            while(true){
                scelta=in.readLine();

                switch (scelta) {
                case "N":
                    String str="";
                    for(String s : disponibilita.keySet()){
                        str += s +": " + disponibilita.get(s)+ "\n";
                    }
                    out.println(str);
                    str="";
                    break;
                case "BUY":
                    
                    
                    break;
                case "QUIT":
                    return;
            
                default:
                    break;
            }

            }

            
            
            
             
        } catch (Exception e) {
            System.out.println("Errore di I/O nella gestione del client: " + e.getMessage());
        }

    }
}


