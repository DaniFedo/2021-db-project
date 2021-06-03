package org.example;

import java.util.concurrent.BlockingQueue;

public class Encryptor extends Thread{

    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;
    //private final Packet poisonPill;

    public Encryptor(BlockingQueue<Packet> decryptedPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = decryptedPacketBlockingQueue;
        this.start();

    }

    @Override
    public void run() {
        try {
            if(decryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();

            while (!decryptedPacketBlockingQueue.isEmpty()) {
                Packet packet = decryptedPacketBlockingQueue.take();

                if(packet.message.message == "OK") {

                    packet.message.encode(packet.secretKey);
                }
                decryptedPacketBlockingQueue.put(packet);

                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
