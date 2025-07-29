package org.example.Multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class ChatClient {
    public static void main(String[] args) throws IOException {
        final String HOST = "localhost";
        final int PORT = 1234;
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter your name: ");
            String username = input.readLine();
            serverWriter.println(username);

            new Thread(()->{
                String response;
                try{
                    while((response = serverReader.readLine()) !=null) {
                        System.out.println(response);
                    }
                }catch (IOException e){
                    System.out.println("Disconnected from server");
                }
            }).start();
            String msg;
            while ((msg = input.readLine()) != null) {
                serverWriter.println(msg);
            }
        }catch (UnknownHostException e){
            throw new RuntimeException();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        }
    }
