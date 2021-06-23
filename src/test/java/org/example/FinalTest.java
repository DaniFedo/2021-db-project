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
        Table.addProduct("Watermelon", "test", "test", 1.15, "Round");
        Table.addProduct("dasda", "test", "test", 2.5, "naksndkadl");
        Table.addGroup("newRound", "newDesc");
        Table.addProduct("Melon", "test", "test", 3.5, "newRound");
        Table.updateProduct("Watermelon", "", "", "", 5.5, "Round");

        Table.updateProductAmount("Watermelon", 10);
        Table.updateProductAmount("Melon", -21);
        Table.updateProductAmount("Melon", 10);
        ;
        //Table.showAllProducts("Round");
        //Table.updateProductAmount("Melon", -5);
        Table.fullPrice();
        Table.fullPrice("Round");
        Table.fullPrice("newRound");
        //Table.showProduct("Watermelon", "", "", "", "", -1);
        /*Table.updateProductAmount("Watermelon", 10);
        Table.updateProductAmount("Watermelon", -7);
*/


        /*Table.addProduct("Watermelon2", "test", "test", "test", "Round");
        Table.updateGroup("Round", "NewName", "newDescr");*/
        //Table.deleteGroup("Round");
        /*Table.updateProduct("Watermelon", "anotherNewName", "",
                "", "", "");
        Table.showProduct("Watermelon", "test",
                "test", "test", "Round", 9);*/
        //Table.deleteProduct("Watermelon");
        Database.close();


    }
}
