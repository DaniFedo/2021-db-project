package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class StoreClientTCP {
        private Socket clientSocket;
        private OutputStream out;
        private InputStream in;

        public void startConnection(String ip, int port) throws IOException {
            clientSocket = new Socket(ip, port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            System.out.println("connected and isClosed is: " + clientSocket.isClosed());
        }

        public void sendPackage(byte[] packet) throws IOException {
            out.write(packet);
            System.out.println("Send from: " + this.clientSocket + " " + Arrays.toString(packet));

        }
        public byte[] receive() throws IOException {
            byte[] maxPacketBuffer = new byte[Packet.maxLength];

            in.read(maxPacketBuffer);
            System.out.println(this.clientSocket + " received a " + Arrays.toString(maxPacketBuffer));
            return maxPacketBuffer;
        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("Closed and isClosed is: " + clientSocket.isClosed());
        }

}
