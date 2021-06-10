package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.BlockingQueue;

public class Decryptor{
   /* private final BlockingQueue<byte[]> encryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> decryptedPacketBlockingQueue;

    public Decryptor(BlockingQueue<byte[]> packetBlockingQueue, BlockingQueue<Packet> encryptedPacketBlockingQueue) {
        this.encryptedPacketBlockingQueue = packetBlockingQueue;
        this.decryptedPacketBlockingQueue = encryptedPacketBlockingQueue;
    }*/

    public static Packet decrypt(byte[] inputPacket) throws Exception {

        Packet packetOutput = new Packet(inputPacket);
        return packetOutput;
    }

   /* @SneakyThrows
    public void run() {
        try {
            //if(encryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();
            while (!encryptedPacketBlockingQueue.isEmpty()) {

                byte[] packetEncoded = encryptedPacketBlockingQueue.take();

                Packet packet = new Packet(packetEncoded);

                decryptedPacketBlockingQueue.put(packet);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }*/

}
