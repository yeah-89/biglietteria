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
    private final Object lock=new Object();


    public SocketHandler(Socket so){
        s=so;

        disponibilita.put("gold", 10);
        disponibilita.put("pit", 30);
        disponibilita.put("parterre", 60);
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
                    String inp= in.readLine();
                    String parti[] = inp.split(" ", 2);

                    
                    synchronized(lock){
                        if(parti[0].toLowerCase()!="gold" && parti[0].toLowerCase()!="pit" && parti[0].toLowerCase()!="parterre"){
                            out.println("ERR UNKNOWNTYPE");
                        }else{
                            if(parti.length==1){
                                parti[1]="1";
                            }
                            try {
                                if(disponibilita.get(parti[0])>= Integer.parseInt((parti[1]))){
                                    disponibilita.put(parti[0], (disponibilita.get(parti[1])- Integer.parseInt((parti[1]))));
                                    out.println("OK");
                                }else{
                                    out.println("KO");
                                }
                            } catch (Exception e) {
                                out.println("ERR SYNTAX");
                            } 
                        }
                    }                
                    break;
                case "QUIT":
                    return;
            
                default:
                    out.println("ERR UNKNOWNCMD");
                    break;
            }

            }

            
            
            
             
        } catch (Exception e) {
            System.out.println("Errore di I/O nella gestione del client: " + e.getMessage());
        }

    }
}


