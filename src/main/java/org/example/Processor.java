package org.example;

import java.util.concurrent.BlockingQueue;

public class Processor extends Thread{
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> answeredPacketBlockingQueue;

    public Processor(BlockingQueue<Packet> packetBlockingQueue, BlockingQueue<Packet> answeredPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = packetBlockingQueue;
        this.answeredPacketBlockingQueue = answeredPacketBlockingQueue;
        this.start();
    }

    @Override
    public void run() {
        try {
            if(decryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();
            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();

                packet.message.message = "OK";
                answeredPacketBlockingQueue.put(packet);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
