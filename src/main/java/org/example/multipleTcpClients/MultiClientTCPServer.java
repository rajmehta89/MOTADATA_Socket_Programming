package org.example.multipleTcpClients;

import java.io.*;
import java.net.*;

public class MultiClientTCPServer {
    public static void main(String[] args) {
        try {
            // Server listens on port 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Waiting for clients...");

            // Continuously accept new client connections
            while (true) {
                // Accepting client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Create a new thread to handle the client
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler class to handle each client
    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                // Create input and output streams
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // Communicate with the client
                String clientMessage;
                while ((clientMessage = input.readLine()) != null) {
                    System.out.println("Received from client: " + clientMessage);

                    // Respond back to the client
                    output.println("Server received: " + clientMessage);
                }

                // Close streams and socket
                input.close();
                output.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
