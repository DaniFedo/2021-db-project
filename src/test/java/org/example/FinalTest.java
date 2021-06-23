package org.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FinalTest {
    private static final String groupTable = "GroupProduct";
    private static final String productTable = "Product";

    private void DBInitialization()
    {

        Table.create(productTable);
        Table.create(groupTable);

    }
    @Test
    public void messageEncodingAndDecodingTest() throws Exception
    {
        Database.connect();
        /*DBInitialization();

        Table.addGroup("testGroup", "testGroupDesc");

        Table.addGroup("testGroup1", "testGroupDesc");

        Table.addProduct("test", null, null, 3.5, "testGroup");
        Table.addProduct("test1", null, null, 2.5, "testGroup1");
        Table.addProduct("test2", null, null, 10, "testGroup");
        Table.updateProduct("test1", "newTest", "", "", 13, "");
        Table.updateProduct("test2", "", "", "", 15, "");

        Table.updateGroup("testGroup", "newTestGroup", "");

        Table.showProduct("", "", "", 0, "", -1);
        Table.updateProduct("test", "newTestName", "newNest", "newWork"
        ,1000, "testGroup1");
        Table.showAllProducts();*/


        Database.close();

        Message message = new Message(5, "kappa, 5, 7");

        byte[] result = message.messagePackaging();
        System.out.println(message);

        StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);
        client1.sendPackage(result);
        client1.receive();
        client1.stopConnection();



    }
}
