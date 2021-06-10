package org.example;

import org.junit.Test;

import javax.xml.crypto.Data;
public class DBTest {
    private void DBInitialization()
    {
        Database.connect();

        Table.create();

        Database.close();
    }

    @Test
    public void DBFullTest() throws Exception {
        DBInitialization();

        MessageGenerator f = new MessageGenerator();

        //packages - add
        Message message = new Message(5, 213, "Buckwheat");
        Packet packet = new Packet((byte)1,1, message);

        Message message2 = new Message(5, 123, "Watermelon");
        Packet packet2 = new Packet((byte)1,2, message2);

        Message message3 = new Message(5, 123, "Pineapple");
        Packet packet3 = new Packet((byte)1,1, message3);

        Message message4 = new Message(5, 123, "Apple");
        Packet packet4 = new Packet((byte)1,2, message4);

        //packages - read
        Message message5 = new Message(9, 312, "ShowAll");
        Packet packet5 = new Packet((byte)1,3, message5);

        Message message6 = new Message(9, 123, "2");
        Packet packet6 = new Packet((byte)1,4, message6);


        //package - update
        Message message7 = new Message(17, 213, "1,Watermelon");
        Packet packet7 = new Packet((byte)1,5, message7);


        //packages - delete
        Message message8 = new Message(33, 213, "1");
        Packet packet8 = new Packet((byte)1,5, message8);

        Message message9 = new Message(33, 213, "3,Pineapple");
        Packet packet9 = new Packet((byte)1,5, message9);

        Message message10 = new Message(33, 213, "Apple");
        Packet packet10 = new Packet((byte)1,5, message10);

        //packages - show
        Message message11 = new Message(65, 213, "2");
        Packet packet11 = new Packet((byte)1,1, message11);

        Message message12 = new Message(65, 123, "2,1");
        Packet packet12 = new Packet((byte)1,2, message12);

        Message message13 = new Message(65, 123, "Watermelon");
        Packet packet13 = new Packet((byte)1,1, message13);

        Message message14 = new Message(65, 123, "5, Watermelon");
        Packet packet14 = new Packet((byte)1,2, message14);

        StoreClientTCP client1 = new StoreClientTCP();
        StoreClientTCP client2 = new StoreClientTCP();
        StoreClientTCP client3 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);
        client2.startConnection("localhost", 2305);
        client3.startConnection("localhost", 2305);


        client2.sendPackage(packet.packetPackaging());
        client2.receive();

        client1.sendPackage(packet2.packetPackaging());
        client1.receive();

        client1.sendPackage(packet3.packetPackaging());
        client1.receive();

        client1.sendPackage(packet4.packetPackaging());
        client1.receive();

        client1.sendPackage(packet6.packetPackaging());
        client1.receive();

        client2.sendPackage(packet7.packetPackaging());
        client2.receive();

        client3.sendPackage(packet5.packetPackaging());
        client3.receive();

        client2.sendPackage(packet11.packetPackaging());
        client2.receive();

        client1.sendPackage(packet12.packetPackaging());
        client1.receive();

        client3.sendPackage(packet13.packetPackaging());
        client3.receive();

        client1.sendPackage(packet14.packetPackaging());
        client1.receive();

        client2.sendPackage(packet8.packetPackaging());
        client2.receive();

        client1.sendPackage(packet9.packetPackaging());
        client1.receive();

        client3.sendPackage(packet10.packetPackaging());
        client3.receive();

        client1.sendPackage(packet5.packetPackaging());
        client1.receive();



        client1.stopConnection();
        client2.stopConnection();
        client3.stopConnection();
    }
}
