package org.example;


import org.junit.Test;

import java.util.concurrent.*;

public class secondPartTest {

    //this class may also be implemented as a fake sender, but task did not mention that we have
    //to use threads in creating messages
    public class sender extends Thread {
        private final BlockingQueue<byte[]> testBQ;
        MessageGenerator f = new MessageGenerator();

        public sender(BlockingQueue<byte[]> testBQ) {
            this.testBQ = testBQ;
            this.start();
        }

        public void run() {
            try {
                for (int i = 0; i < 5; i++) testBQ.put(f.generateClassic());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    //this tests executes my Decryptor, Encryptor and Processor.
    //including the fact that my mentioned classes are already "extends Thread", so their constructors
    //have "this.start()", they are also being executed by ExecutorService, which leads to bigger
    //amount of threads and workload
        @Test
        public void analyzingOfFullProcessTest() throws InterruptedException {

            BlockingQueue<byte[]> queueEncrypted = new LinkedBlockingQueue<>(100);

            BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

            BlockingQueue<Packet> queueAnswered = new LinkedBlockingQueue<>(100);

            for (int i = 0; i < 3; i++) {
                new Thread(new sender(queueEncrypted)).join();
            }

            System.out.println(queueEncrypted.take());

            //previous test
            /*  for(int i = 0; i < 3; i++) {
            new Thread(new Decryptor(queueEncrypted, queueDecrypted)).join();
        }

        for(int i = 0; i < 2; i++)
        {
            new Thread(new Processor(queueDecrypted, queueAnswered)).join();
        }

        for(int i = 0; i < 3; i++)
        {
            new Thread(new Encryptor(queueAnswered)).join();
        }*/

            ExecutorService executor = Executors.newFixedThreadPool(10);

            for(int i = 0; i < 3; i++) {
                executor.execute(new Decryptor(queueEncrypted, queueDecrypted));
            }

            for(int i = 0; i < 2; i++)
            {
                executor.execute(new Processor(queueDecrypted, queueAnswered));
            }

            for(int i = 0; i < 4; i++)
            {
                executor.execute(new Encryptor(queueAnswered));
            }
            executor.shutdown();
        }
    }


