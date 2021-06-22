package org.example;


import org.sqlite.core.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

    static Connection connection;

    public static void connect()
    {
        try
        {
            String url = "jdbc:sqlite:" + DBWorkspace.dbName;
            connection = DriverManager.getConnection(url);

            //System.out.println("Database " + DBWorkspace.dbName + " connected to the SQLite");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close()
    {
        try
        {
            connection.close();
            //System.out.println("Database " + DBWorkspace.dbName + " closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
