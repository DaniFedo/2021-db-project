package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

//ServerClient -> TCPTest

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
            System.out.println("Length of the sent package: " +packet.length);
            System.out.println("Client with ID " + clientSocket.getLocalPort()+ " send " + Arrays.toString(packet));

        }
        public void receive() throws Exception {
            byte[] maxPacketBuffer = new byte[Message.maxLength];
            in.read(maxPacketBuffer);
            Message message = new Message(maxPacketBuffer);
            System.out.println("Client with ID " + clientSocket.getLocalPort() + " received a " + message);

            System.out.println("-------------------");
        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }

}
