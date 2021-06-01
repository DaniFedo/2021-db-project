package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Data d = new Data();

        Worker w3 = new Worker(3, d);
        Worker w2 = new Worker(2, d);
        Worker w1 = new Worker(1, d);

        w1.join();
        w2.join();
        w3.join();
        System.out.println("end of main...");
    }
}
