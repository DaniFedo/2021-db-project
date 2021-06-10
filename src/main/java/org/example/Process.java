package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Process {

    public static byte[] process(byte[] Packet) throws Exception {
/*
        //encrypted income of Packets in bytes
        BlockingQueue<byte[]> queueEncrypted = new LinkedBlockingQueue<>(100);

        //decrypted Packets through class Decryptor
        BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

        //Packets after Processor
        BlockingQueue<Packet> queueAnswered = new LinkedBlockingQueue<>(100);

        BlockingQueue<Packet> queueForSending = new LinkedBlockingQueue<>(100);

        queueEncrypted.put(Packet);*/

        Packet decryptedPacket = Decryptor.decrypt(Packet);

        Packet processedPacket = Processor.operate(decryptedPacket);


        return Encryptor.encode(processedPacket);



    }
}
