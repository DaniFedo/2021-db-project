//package org.example;
//
//import org.junit.Test;
//
//public class UDPTest {
//
//    @Test
//    public void whenCanSendAndReceivePacket_thenCorrect() throws Exception {
//        new StoreServerUDP().start();
//        StoreClientUDP client = new StoreClientUDP();
//        StoreClientUDP client1 = new StoreClientUDP();
//        StoreClientUDP client2 = new StoreClientUDP();
//        MessageGenerator generator = new MessageGenerator();
//
//        client.sendMessage(generator.generateForClient(client));
//        client.receive();
//
//        client.sendMessage(generator.generateForClient(client));
//        client.receive();
//
//        client1.sendMessage(generator.generateForClient(client));
//        client1.receive();
//
//        client2.sendMessage(generator.generateForClient(client));
//        client2.receive();
//
//        client.close();
//        client1.close();
//        client2.close();
//
//    }
//}