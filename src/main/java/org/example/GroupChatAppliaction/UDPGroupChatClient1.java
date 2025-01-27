package org.example.GroupChatAppliaction;

import java.net.*;
import java.io.*;

public class UDPGroupChatClient1 {
    private static final String SERVER_IP = "localhost";  // Use server IP here
    private static final int SERVER_PORT = 9876;
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;
    private BufferedReader userInput;

    public UDPGroupChatClient1() {
        try {
            clientSocket = new DatagramSocket();
            serverAddress = InetAddress.getByName(SERVER_IP);
            userInput = new BufferedReader(new InputStreamReader(System.in));

            // Listen for incoming messages from the server in a separate thread
            new Thread(this::listenForMessages).start();

            System.out.println("Connected to server. You can start chatting!");

            while (true) {
                System.out.print("Enter message: ");
                String message = userInput.readLine();
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        }
    }

    // Listen for incoming messages from the server
    private void listenForMessages() {
        try {
            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("New message: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Send a message to the server
    private void sendMessage(String message) {
        try {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
            clientSocket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UDPGroupChatClient1();
    }
}
