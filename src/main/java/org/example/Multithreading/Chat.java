package org.example.Multithreading;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Chat{
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) throws IOException {
        final int PORT = 1234;
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Waiting for client on port " + PORT);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler client = new ClientHandler(socket, clients);
                clients.add(client);
                new Thread(client).start();
            }
        }

    }

}
