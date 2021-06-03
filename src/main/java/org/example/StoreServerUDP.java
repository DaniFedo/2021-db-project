package org.example;

import lombok.SneakyThrows;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class StoreServerUDP extends Thread {

    private final DatagramSocket socket;
    private final byte[] received = new byte[Packet.maxLength];

    public StoreServerUDP() throws SocketException {
        socket = new DatagramSocket(2305);
    }

    @SneakyThrows
    public void run() {

        while (true) {
            DatagramPacket packet = new DatagramPacket(received, received.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            byte[] receivedBytes = packet.getData();

            System.out.println("Received(at server): " + Arrays.toString(receivedBytes));

            Packet packetReceived = new Packet(receivedBytes);

            System.out.println("Message receivedBytes: " + packetReceived.message.message);

            Process process = new Process();
            receivedBytes = process.process(receivedBytes);
            packetReceived = new Packet(receivedBytes);

            System.out.println("Message sent: " + packetReceived.message.message);

            DatagramPacket packetToSend = new DatagramPacket(receivedBytes, receivedBytes.length, address, port);

            System.out.println("Sent from server: " + Arrays.toString(receivedBytes));

            socket.send(packetToSend);
        }
    }
}