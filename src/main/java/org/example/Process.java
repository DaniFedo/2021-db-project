package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Process {
    //was testing + Encryptor(learned how to use synchronized!!!! great)
    //static int counter = 0;

//class for testing server part

    //was testing + Encryptor
    /*public static void test()
    {
        counter ++;
    }*/
    public static void main(String args[]) throws Exception {
        //encrypted income of Packets in bytes
        BlockingQueue<byte[]> queueEncrypted = new LinkedBlockingQueue<>(100);

        //decrypted Packets through class Decryptor
        BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

        //Packets after Processor
        BlockingQueue<Packet> queueAnswered = new LinkedBlockingQueue<>(100);


        //doubt we need this
        //Packet poisonPill = new Packet((byte)1, 1, new Message(1,1,"poison"));

        FakeNetwork f = new FakeNetwork();
        byte[] packetTest;

        //fulfilling our BQ
        for(int i = 0; i < 98; i++)
        {
            packetTest = f.generate();
            queueEncrypted.put(packetTest);
        }

        for(int i = 0; i < 3; i++) {
            new Thread(new Decryptor(queueEncrypted, queueDecrypted)).join();
        }

        for(int i = 0; i < 3; i++)
        {
                //queueDecrypted.put(poisonPill);
            new Thread(new Processor(queueDecrypted, queueAnswered)).join();
        }

        for(int i = 0; i < 3; i++)
        {
            new Thread(new Encryptor(queueAnswered)).join();
        }


    }
}
