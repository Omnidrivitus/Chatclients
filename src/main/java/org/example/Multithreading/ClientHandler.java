package org.example.Multithreading;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Set<ClientHandler> clients;

    public ClientHandler(Socket socket,  Set<ClientHandler> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;

        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void message(String msg) {
        writer.println(msg);
    }

    @Override
    public void run() {
        try {
            String name = reader.readLine();
            Broadcast(name + ": new joined chat");
            String msg;
            while ((msg = reader.readLine()) != null) {
                Broadcast(name + ": " + msg);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }finally {
            try{
                socket.close();
                clients.remove(this);
                Broadcast("A user has left the chat");
            }catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void Broadcast(String msg) {
        for (ClientHandler client : clients) {
            client.message(msg);

        }
    }
}
// BufferReader,Buffer writer
//finally
//DBMS (database management system)
//JDBC (java database connectivity)
//Servlet
//JSP
//Thymeleaf
//Bootstrap
//mvc-> Model view controller
