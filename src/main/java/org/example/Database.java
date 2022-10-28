package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

    static Connection connection;

    public static void connect() {
        try {
            String url = "jdbc:sqlite:" + DBWorkspace.dbName;
            connection = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
