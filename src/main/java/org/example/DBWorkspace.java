package org.example;

import org.sqlite.core.DB;

import javax.xml.crypto.Data;

public class DBWorkspace {

    public final static String dbName =  "database.db";
    public static final String productTableName = "GroupProduct";
    public static final String tableName = "Product";
    //public final static String tableName = "myTable";

    public static void main(String args[]) throws Exception {


        //Table.create();

        //Table.addProduct("first");

        Message message = new Message(5, 123, "TestProduct");
        Packet packet = new Packet((byte)1,1, message);

        StoreClientTCP client1 = new StoreClientTCP();
        client1.startConnection("localhost", 2305);

        client1.sendPackage(packet.packetPackaging());
        client1.receive();

        client1.stopConnection();

        //Table.addProduct("second");

        /*Table.deleteProduct("first");

        Table.deleteProduct(6);

        Table.deleteProduct(5, "second");*/

        //Table.updateProduct(7, "newTitle");

        /*Table.showAllProducts();

        Table.showProductsInRange(3);

        Table.showProductsInRange(4, 3);

        Table.showProductByTitle("second");

        Table.showProductByTitle("second", 4);

        Table.showProductById(7);*/

    }
}
