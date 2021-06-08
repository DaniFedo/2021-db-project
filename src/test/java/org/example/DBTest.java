package org.example;

import org.junit.Test;

public class DBTest {
    @Test
    public void DBFullTest() throws Exception {

        MessageGenerator f = new MessageGenerator();

        StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);

        client1.sendPackage(f.generateForClient(client1));
        client1.receive();
    }
}
