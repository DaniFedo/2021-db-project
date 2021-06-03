package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class StoreClientTCP {
        public Socket clientSocket;
        private OutputStream out;
        private InputStream in;

        public void startConnection(String ip, int port) throws IOException {
            clientSocket = new Socket(ip, port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

        }

        public void sendPackage(byte[] packet) throws Exception {
            out.write(packet);
            System.out.println("Client with ID " + clientSocket.getLocalPort()+ " send " + Arrays.toString(packet));

        }
        public byte[] receive() throws Exception {
            byte[] maxPacketBuffer = new byte[Packet.maxLength];
            in.read(maxPacketBuffer);
            System.out.println("Client with ID " + clientSocket.getLocalPort() + " received a " + Arrays.toString(maxPacketBuffer));
            Packet packetForOutput = new Packet(maxPacketBuffer);
            System.out.println("Client with ID " + clientSocket.getLocalPort() + " received a message: " + packetForOutput.message.message);
            return maxPacketBuffer;
        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }

}
