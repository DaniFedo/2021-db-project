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
            while (!encryptedPacketBlockingQueue.isEmpty()) {

                byte[] packetEncoded = encryptedPacketBlockingQueue.take();

                Packet packet = new Packet(packetEncoded);

                decryptedPacketBlockingQueue.put(packet);

                //for test
                System.out.println("decryptor__ " + Thread.currentThread().getName() + ' ' + packet.message);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
