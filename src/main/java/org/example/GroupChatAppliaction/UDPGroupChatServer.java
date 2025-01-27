package org.example.GroupChatAppliaction;

import java.net.*;
import java.util.*;

public class UDPGroupChatServer {
    private static final int PORT = 9876;
    private static List<InetSocketAddress> clients = new ArrayList<>();  // Track client addresses and ports

    public static void main(String[] args) {
        DatagramSocket serverSocket = null;

        try {
            serverSocket = new DatagramSocket(PORT);
            System.out.println("UDP Server is running and listening on port " + PORT);

            while (true) {
                byte[] receiveData = new byte[1024];

                // Receive message from any client
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                System.out.println("Received message: " + message);

                // Add new client if not already in the list
                InetSocketAddress clientSocketAddress = new InetSocketAddress(clientAddress, clientPort);
                if (!clients.contains(clientSocketAddress)) {
                    clients.add(clientSocketAddress);
                }

                // Send the message to all clients (broadcast)
                for (InetSocketAddress client : clients) {
                    if (!client.getAddress().equals(clientAddress) || client.getPort() != clientPort) {
                        byte[] sendData = message.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, client.getAddress(), client.getPort());
                        serverSocket.send(sendPacket);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        }
    }
}
