package org.example;

import org.junit.Test;

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
        DBInitialization();

        Table.addGroup("testGroup", "testGroupDesc");

        Table.addGroup("testGroup1", "testGroupDesc");
        //Table.addGroup("testGroup1", "");*/
        Table.addProduct("test", null, null, 3.5, "testGroup");
        Table.addProduct("test1", null, null, 2.5, "testGroup1");
        Table.addProduct("test2", null, null, 10, "testGroup");
        Table.updateProduct("test1", "newTest", "", "", 13, "");
        Table.updateProduct("test2", "", "", "", 15, "");

        Table.updateGroup("testGroup", "newTestGroup", "");

        Table.showProduct("", "", "", 0, "", -1);
        Table.updateProduct("test", "newTestName", "newNest", "newWork"
        ,1000, "testGroup1");
        Table.showAllProducts();


        Database.close();


    }
}
