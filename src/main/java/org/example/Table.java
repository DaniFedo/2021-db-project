package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {

    private static void showProducts(ResultSet resultSet) {
        //ResultSet resultSet = selectAllProducts();

        //System.out.println(resultSet.next());
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showProducts(ResultSet resultSet, int limit) {
        int counter = 0;
        try {
            while (resultSet.next() && counter < limit ) {
                System.out.println(resultSet.getInt("id") + "\t" + resultSet.getString("title"));
                counter++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet selectProductsByTitle(String title) {
        String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE title = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, title);

            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //create table
    public static void create() {
        String query = "CREATE TABLE IF NOT EXISTS " + DBWorkspace.tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT)";

        try {
            Statement statement = Database.connection.createStatement();

            statement.execute(query);

            System.out.println("Table " + DBWorkspace.tableName + " created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //create product; code - 5
    public static void addProduct(String title) {
        String query = "INSERT INTO " + DBWorkspace.tableName + " (title) VALUES(?)";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, title);

            preparedStatement.executeUpdate();
            System.out.println("Added new element: " + title);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //list by criteria; code - 65

    public static void showProductsInRange(int amount) {
        String query = "SELECT * FROM " + DBWorkspace.tableName + " LIMIT ?, ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, amount);

            System.out.println("Your first " + amount + " chosen elements:");
            showProducts(preparedStatement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showProductsInRange(int amount, int offset) {
        String query = "SELECT * FROM " + DBWorkspace.tableName + " LIMIT ?, ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, amount);

            System.out.println("Your first " + amount + " chosen elements with offset " + offset + ":");
            showProducts(preparedStatement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showProductByTitle(String title){

            System.out.println("Products with title = \"" + title + "\":");
            showProducts(selectProductsByTitle(title));
    }

    public static void showProductByTitle(String title, int limit){
        System.out.println("First " + limit + " products with title = \"" + title + "\":");
        showProducts(selectProductsByTitle(title), limit);

    }


    //read; code - 9
    public static void showProductById(int id){
        String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE id = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            System.out.println("Here is your product:");
            showProducts(preparedStatement.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllProducts() {
        String query = "SELECT * FROM " + DBWorkspace.tableName;

        try {
            Statement statement = Database.connection.createStatement();
            System.out.println("All products:");
            showProducts(statement.executeQuery(query));
            //return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    //delete; code - 33
    public static void deleteProduct(String title){
        String query = "DELETE FROM " + DBWorkspace.tableName + " WHERE title = ?";

        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, title);

            preparedStatement.executeUpdate();

            System.out.println("Deleted product with title = \"" + title + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id){
        String query = "DELETE FROM " + DBWorkspace.tableName + " WHERE id = ?";

        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

            System.out.println("Deleted product with id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id, String title){
        String query = "DELETE FROM " + DBWorkspace.tableName + " WHERE id = ? AND title = ?";

        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();

            System.out.println("Deleted product with title = \"" + title + "\" and id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //update; code - 17
    public static void updateProduct(int id, String title){
        String query = "UPDATE " + DBWorkspace.tableName + " SET title = ? WHERE id = ?";

        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();

            System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
