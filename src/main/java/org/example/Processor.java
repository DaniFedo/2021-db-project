package org.example;

import java.util.concurrent.BlockingQueue;

public class Processor extends Thread {
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> answeredPacketBlockingQueue;
    //private final Packet poisonPill;

    public Processor(BlockingQueue<Packet> packetBlockingQueue, BlockingQueue<Packet> answeredPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = packetBlockingQueue;
        this.answeredPacketBlockingQueue = answeredPacketBlockingQueue;
        this.start();
    }

    @Override
    public void run() {
        try {
            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();

                packet.message.message = "OK";
                answeredPacketBlockingQueue.put(packet);


                //for test
                System.out.println("processor__ " + Thread.currentThread().getName() + ' ' + packet.message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
