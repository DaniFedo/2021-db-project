package org.example;

import java.util.concurrent.BlockingQueue;

public class Encryptor{

    public static byte[] encode(Packet operatedPacket) throws Exception {

        operatedPacket.message.encode(operatedPacket.secretKey);

        return operatedPacket.packetPackaging();
    }
    /*private final BlockingQueue<Packet> decryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> sendingPacketBlockingQueue;

    public Encryptor(BlockingQueue<Packet> decryptedPacketBlockingQueue, BlockingQueue<Packet> sendingPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = decryptedPacketBlockingQueue;
        this.sendingPacketBlockingQueue = sendingPacketBlockingQueue;
        this.start();
    }

    @Override
    public void run() {
        try {
            if(decryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();

            while (!decryptedPacketBlockingQueue.isEmpty()) {
                Packet packet = decryptedPacketBlockingQueue.take();

                packet.message.encode(packet.secretKey);

                sendingPacketBlockingQueue.put(packet);

                Thread.currentThread().interrupt();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
