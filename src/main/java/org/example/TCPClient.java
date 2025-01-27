package org.example;

import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Connecting to the server on localhost at port 12345
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");

            // Create input and output streams
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read user input and send it to the server
            System.out.print("Enter message to send to the server: ");
            String message = userInput.readLine();
            output.println(message);

            // Receive and print the server's response
            String serverResponse = input.readLine();
            System.out.println("Server says: " + serverResponse);

            // Close the streams and socket
            userInput.close();
            output.close();
            input.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
