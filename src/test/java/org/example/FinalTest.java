package org.example;

import org.junit.Test;

import javax.xml.crypto.Data;

public class FinalTest {
    private static final String groupTable = "GroupProduct";
    private static final String productTable = "Product";

    private void DBInitialization()
    {
        Database.connect();

        Table.create(productTable);
        Table.create(groupTable);

        Database.close();
    }
    @Test
    public void messageEncodingAndDecodingTest() throws Exception
    {
        DBInitialization();

        Database.connect();
        Table.addGroup("Round", "round products");
        Table.addProduct("Watermelon", "test", "test", "test", "Round");
        /*Table.updateProductAmount("Watermelon", 10);
        Table.updateProductAmount("Watermelon", -7);
*/


        /*Table.addProduct("Watermelon2", "test", "test", "test", "Round");
        Table.updateGroup("Round", "NewName", "newDescr");*/
        //Table.deleteGroup("Round");
        Table.updateProduct("Watermelon", "anotherNewName", "",
                "", "", "");
        Table.showProduct("Watermelon", "test",
                "test", "test", "Round", 9);
        //Table.deleteProduct("Watermelon");
        Database.close();


    }
}
