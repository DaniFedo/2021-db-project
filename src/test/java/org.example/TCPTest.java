///*
//package org.example;
//
//import org.junit.Test;
//
//public class TCPTest {
//    @Test
//    public void givenClient1_whenServerResponds_thenCorrect() throws Exception {
//        MessageGenerator f = new MessageGenerator();
//
//        StoreClientTCP client1 = new StoreClientTCP();
//        client1.startConnection("localhost", 2305);
//
//        StoreClientTCP client2 = new StoreClientTCP();
//        client2.startConnection("localhost", 2305);
//
//        StoreClientTCP client3 = new StoreClientTCP();
//        client3.startConnection("localhost", 2305);
//
//        client1.sendPackage(f.generateForClient(client1));
//        client1.receive();
//        System.out.println("-------------");
//
//        client1.sendPackage(f.generateForClient(client1));
//        client1.receive();
//        System.out.println("-------------");
//
//        client2.sendPackage(f.generateForClient(client1));
//        client2.receive();
//        System.out.println("-------------");
//
//        client1.sendPackage(f.generateForClient(client1));
//        client1.receive();
//        System.out.println("-------------");
//
//        client3.sendPackage(f.generateForClient(client1));
//        client3.receive();
//
//
//        client1.stopConnection();
//        client2.stopConnection();
//        client3.stopConnection();
//
//    }
//
//}
//*/
