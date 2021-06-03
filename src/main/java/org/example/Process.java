package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Process {

    public byte[] process(byte[] Packet) throws InterruptedException {

        //encrypted income of Packets in bytes
        BlockingQueue<byte[]> queueEncrypted = new LinkedBlockingQueue<>(100);

        //decrypted Packets through class Decryptor
        BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

        //Packets after Processor
        BlockingQueue<Packet> queueAnswered = new LinkedBlockingQueue<>(100);

        queueEncrypted.put(Packet);

        for(int i = 0; i < 3; i++) {
            new Thread(new Decryptor(queueEncrypted, queueDecrypted)).join();
        }

        for(int i = 0; i < 3; i++)
        {
            new Thread(new Processor(queueDecrypted, queueAnswered)).join();
        }

        for(int i = 0; i < 3; i++) {
            new Thread(new Encryptor(queueAnswered)).join();
        }

        byte[] output = queueAnswered.take().packetPackaging();

        return output;
    }
}
