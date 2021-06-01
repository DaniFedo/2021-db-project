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

            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();

                if(packet.message.message == "OK") {

                    packet.message.encode(packet.secretKey);

                    FakeNetwork fakeNetwork = new FakeNetwork();
                    fakeNetwork.sendMessage(packet);

                    //was testing
                    //synchronized (this){Process.test();}

                    //for test
                    //System.out.println("encryptor__ " + Thread.currentThread().getName() + ' ' + packet.message);
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
