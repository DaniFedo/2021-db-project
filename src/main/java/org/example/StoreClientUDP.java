package org.example;

import java.net.*;
import java.util.Arrays;

public class StoreClientUDP {
    public DatagramSocket socket;
    private final InetAddress address;

    public StoreClientUDP() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public void sendMessage(byte[] packet) throws Exception
    {
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, address, 2305);

        System.out.println("Client with ID " + socket.getLocalPort()+ " send " + Arrays.toString(packet));

        socket.send(datagramPacket);
    }

    public void receive() throws Exception{
        byte[] receivedBytes = new byte[Packet.maxLength];
        DatagramPacket datagramPacket = new DatagramPacket(receivedBytes, receivedBytes.length);

        socket.receive(datagramPacket);

        receivedBytes = datagramPacket.getData();

        System.out.println("Client with ID " + socket.getLocalPort() + " received a " + Arrays.toString(receivedBytes));
        Packet packetForOutput = new Packet(receivedBytes);
        System.out.println("Client with ID " + socket.getLocalPort() + " received a message: " + packetForOutput.message.message);
        System.out.println("---------------------------");

    }


    public void close() {socket.close();}

}