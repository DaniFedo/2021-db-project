package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Decryptor implements Runnable{
    private final BlockingQueue<Packet> encryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;

    public Decryptor(BlockingQueue<Packet> packetBlockingQueue, BlockingQueue<Packet> encryptedPacketBlockingQueue) {
        this.encryptedPacketBlockingQueue = packetBlockingQueue;
        this.decryptedPacketBlockingQueue = encryptedPacketBlockingQueue;
    }

    @SneakyThrows
    public void run() {
        try {
            while (!encryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = encryptedPacketBlockingQueue.take();
                /* if (pac.equals(poisonPill)) {
                    return;
                }*/
                packet.message.decode(packet.secretKey);
                decryptedPacketBlockingQueue.put(packet);

                System.out.println(Thread.currentThread().getName() + ' ' + packet.message);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
