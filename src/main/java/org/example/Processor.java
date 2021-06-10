package org.example;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;

public class Processor{
    /*private final BlockingQueue<Packet> decryptedPacketBlockingQueue;
    private final BlockingQueue<Packet> answeredPacketBlockingQueue;

    public Processor(BlockingQueue<Packet> packetBlockingQueue, BlockingQueue<Packet> answeredPacketBlockingQueue) {
        this.decryptedPacketBlockingQueue = packetBlockingQueue;
        this.answeredPacketBlockingQueue = answeredPacketBlockingQueue;
        this.start();
    }*/
    public static Packet operate(Packet inputPacket) throws Exception {

        MessageDecryptor.decryptMessage(inputPacket.message);

        inputPacket.message.message = "Answered in server part.";


        return inputPacket;
    }

    /*@Override
    public void run() {
        try {
            //if(decryptedPacketBlockingQueue.isEmpty()) Thread.currentThread().interrupt();

            while (!decryptedPacketBlockingQueue.isEmpty()) {

                Packet packet = decryptedPacketBlockingQueue.take();

                Packet packetForTest = packet;
                MessageDecryptor.decryptMessage(packet.message);

                System.out.println(packetForTest.equals(packet));
                answeredPacketBlockingQueue.put(packet);

                //Thread.currentThread().interrupt();

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
