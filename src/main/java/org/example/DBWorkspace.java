package org.example;

import org.sqlite.core.DB;

import javax.xml.crypto.Data;

public class DBWorkspace {

    public final static String dbName =  "database.db";
    public final static String tableName = "myTable";

    public static void main(String args[])
    {
        Database.connect();

        Table.create();

        //Table.addProduct("first");

        Table.addProduct("second");

        /*Table.deleteProduct("first");

        Table.deleteProduct(6);

        Table.deleteProduct(5, "second");*/

        //Table.updateProduct(7, "newTitle");

        Table.showAllProducts();

        Table.showProductsInRange(3);

        Table.showProductsInRange(4, 3);

        Table.showProductByTitle("second");

        Table.showProductByTitle("second", 4);

        Table.showProductById(7);
        Database.close();

    }
}
