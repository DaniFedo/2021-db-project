package org.example;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class secondPartTest {
    //this class may also be implemented as a fake sender, but task did not mention that we have
    //to use threads in creating messages
    public class sender extends Thread{
        private final BlockingQueue<byte[]> testBQ;
        FakeNetwork f = new FakeNetwork();

        public sender(BlockingQueue<byte[]> testBQ) {
            this.testBQ = testBQ;
            this.start();
        }

        public void run()
        {
            try
            {
                for(int i = 0; i < 5; i++) testBQ.put(f.generate());
            }
            catch(InterruptedException e)
            {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @Test
    public void analyzingOfFullProcessTest() throws InterruptedException {

        BlockingQueue<byte[]> queueEncrypted = new LinkedBlockingQueue<>(100);

        BlockingQueue<Packet> queueDecrypted = new LinkedBlockingQueue<>(100);

        BlockingQueue<Packet> queueAnswered = new LinkedBlockingQueue<>(100);

        for(int i = 0; i < 2; i++) {
            new Thread(new sender(queueEncrypted)).join();
        }

        System.out.println(queueEncrypted.take());
        for(int i = 0; i < 3; i++) {
            new Thread(new Decryptor(queueEncrypted, queueDecrypted)).join();
        }

        for(int i = 0; i < 3; i++)
        {
            new Thread(new Processor(queueDecrypted, queueAnswered)).join();
        }

        for(int i = 0; i < 3; i++)
        {
            new Thread(new Encryptor(queueAnswered)).join();
        }

    }
}
