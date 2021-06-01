package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Process {
    static BlockingQueue<Packet> queueEncrypted = new LinkedBlockingQueue<>(100);

    static BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

    public static void main(String args[]) throws Exception {
        FakeNetwork f = new FakeNetwork();
        Packet packetTest;
       /* packetTest.message.decode(packetTest.secretKey);
        packetTest1.message.decode(packetTest1.secretKey);

        //System.out.println(packetTest.message.message + ' ' + packetTest.bSrc + ' ' + packetTest.bPktId);
        //packetTest.message.decode(secretKey);
        System.out.println(packetTest1.message.message);
        System.out.println(packetTest.message.message);*/
        for(int i = 0; i < 100; i++)
        {
            packetTest = f.generate();
            queueEncrypted.put(packetTest);
        }

        for(int i = 0; i < 5; i++)
        {
            new Thread(new Decryptor(queueEncrypted, queueDecrypted)).start();

            new Thread(new Processor(queueDecrypted)).start();

            new Thread(new Encryptor(queueDecrypted)).start();
        }




    }
}
