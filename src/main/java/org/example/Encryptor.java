package org.example;

import java.util.concurrent.BlockingQueue;

public class Encryptor implements Runnable{

    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;

    public Encryptor(BlockingQueue<Packet> decryptedPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = decryptedPacketBlockingQueue;
    }

    @Override
    public void run() {
        try {
            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();
                /* if (pac.equals(poisonPill)) {
                    return;
                }*/
                if(packet.message.message == "OK") {

                    System.out.println("message before " + packet.message.message);
                    packet.message.encode(packet.secretKey);
                    System.out.println("encryptor " + Thread.currentThread().getName() + ' ' + packet.message);
                    packet.message.decode(packet.secretKey);
                    System.out.println("decoded after: " + packet.message.message);
                }
                else decryptedPacketBlockingQueue.put(packet);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
