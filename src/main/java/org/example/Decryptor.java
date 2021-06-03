package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Decryptor extends Thread{
    private final BlockingQueue<byte[]> encryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;

    public Decryptor(BlockingQueue<byte[]> packetBlockingQueue, BlockingQueue<Packet> encryptedPacketBlockingQueue) {
        this.encryptedPacketBlockingQueue = packetBlockingQueue;
        this.decryptedPacketBlockingQueue = encryptedPacketBlockingQueue;
        this.start();
    }

    @SneakyThrows
    public void run() {
        try {
            if(encryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();
            while (!encryptedPacketBlockingQueue.isEmpty()) {

                byte[] packetEncoded = encryptedPacketBlockingQueue.take();

                Packet packet = new Packet(packetEncoded);

                decryptedPacketBlockingQueue.put(packet);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
