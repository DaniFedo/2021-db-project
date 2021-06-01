package org.example;

import java.util.concurrent.BlockingQueue;

public class Processor implements Runnable {
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;

    public Processor(BlockingQueue<Packet> packetBlockingQueue) {
        this.decryptedPacketBlockingQueue = packetBlockingQueue;
    }

    @Override
    public void run() {
        try {
            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();
                if(packet.message.message == "OK") {decryptedPacketBlockingQueue.put(packet); return;}
                /* if (pac.equals(poisonPill)) {
                    return;
                }*/
                packet.message.message = "OK";
                decryptedPacketBlockingQueue.put(packet);

                System.out.println("processor " + Thread.currentThread().getName() + ' ' + packet.message);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
