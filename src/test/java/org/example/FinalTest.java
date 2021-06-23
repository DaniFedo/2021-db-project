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
        Table.updateGroup("newTestGroup2", "testGroup2","");
        Table.updateGroup("newTestGroup2", "testGroup", "");
        /*Table.addGroup("testGroup2", "");
        Table.addProduct("test3","","",10,"testGroup2");
*/

    }
    @Test
    public void messageEncodingAndDecodingTest() throws Exception
    {
        Database.connect();
        DBInitialization();
        Database.close();

        Message message2 = new Message(17, "\"test\",\"newTest\",\"newDesc\",,10.0,\"newTestGroup\"");
        Message message3 = new Message(17, "\"test3\",\"newTest3\",\"newDesc3\",\"newManuf\",10.0,\"newTestGroup2\"");
                //"\"test3\",\"newTest3\",\"newDescription\",\"newManuf\",5.0,\"newTestGroup2\"");




       StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);

        client1.sendPackage(message2.messagePackaging());
        client1.receive();

        client1.sendPackage(message3.messagePackaging());
        client1.receive();
        client1.stopConnection();








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


    }
}
