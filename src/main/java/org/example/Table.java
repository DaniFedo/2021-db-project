package org.example;

import org.sqlite.SQLiteException;
import org.sqlite.core.DB;
import partFive.MyHttpServer;
import partFive.controllers.Controller;

import javax.management.Query;
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
                System.out.println(resultSet.getString("NameOfProduct") + "\t" + resultSet.getString("Description")
                        + "\t" + resultSet.getString("Manufacturer") + "\t" + resultSet.getInt("Amount")
                        + "\t" + resultSet.getString("Price") + "\t" + resultSet.getString("ProductGroup"));
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

    public static int maxID()
    {
        String query = "SELECT MAX(id) FROM " + DBWorkspace.tableName;

        try {
            Statement statement = Database.connection.createStatement();
            //showProducts(statement.executeQuery(query));
            //return statement.executeQuery(query);
            return statement.executeQuery(query).getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //create table
    public static void create(String tableName) {

        String query;
        if(tableName.equals("Product")) {
             query = "CREATE TABLE IF NOT EXISTS Product " +
                    " (NameOfProduct VARCHAR(45) PRIMARY KEY, Description VARCHAR(45), Manufacturer VARCHAR(45)," +
                     " Amount int, Price VARCHAR(32), ProductGroup VARCHAR(45), " +
                     " FOREIGN KEY (ProductGroup) REFERENCES GroupProduct (NameOfGroup) ON DELETE CASCADE" +
                     " ON UPDATE CASCADE);";
        }

        else {
             query = "CREATE TABLE IF NOT EXISTS " + tableName +
                    " (NameOfGroup VARCHAR(45) PRIMARY KEY, Description VARCHAR(45))";
        }
        try {
            Statement statement = Database.connection.createStatement();

            statement.execute(query);

            System.out.println("Table " + tableName + " created or already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static String lookForUniqToken(int id, String password){
        String query = "SELECT * FROM " + MyHttpServer.TableName +
                " WHERE id = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, password);
            ResultSet answer = preparedStatement.executeQuery();

            return answer.getString("token");

        } catch (SQLException e) {
        }
        return null;
    }

    public static boolean checkUniqToken(String token){
        String query = "SELECT * FROM " + MyHttpServer.TableName +
                " WHERE token = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, token);
            ResultSet answer = preparedStatement.executeQuery();

            if(answer.next() == true) return true;
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int getIdByToken(String token){
        String query = "SELECT id FROM " + MyHttpServer.TableName +
                " WHERE token = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, token);
            ResultSet answer = preparedStatement.executeQuery();

           
            return answer.getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public static boolean checkWhetherAlive(int id){
        String query = "SELECT * FROM " + DBWorkspace.tableName +
                " WHERE id = ?";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            ResultSet answer = preparedStatement.executeQuery();

            return answer.next();

        } catch (SQLException e) {
            return false;
        }

    }

    public static void addCustomer(String password, String token){
        String query = "INSERT INTO " + MyHttpServer.TableName + " (password, token) VALUES(?, ?)";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, token);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //create product; code - 5
    public static String addProduct(String title, String description, String manufacturer,
                                    String Price, String ProductGroup) {

        String query = "INSERT INTO " + DBWorkspace.tableName + " (NameOfProduct, Description, Manufacturer" +
                ", Price, ProductGroup, Amount) VALUES(?, ?, ?, ?, ?, 0)";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, manufacturer);
            preparedStatement.setString(4, Price);
            preparedStatement.setString(5, ProductGroup);

            preparedStatement.executeUpdate();
            return "Added new element: " + title;

        } catch (SQLException e) {
            System.out.println("This product already exists");
        }
        return null;
    }

    public static String addGroup(String title, String description) {

        String query = "INSERT INTO " + DBWorkspace.productTableName + " (NameOfGroup, Description) VALUES(?, ?)";

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);

            preparedStatement.executeUpdate();
            return "Added new group: " + title;

        } catch (SQLException e) {
            System.out.println("Group with name " + title + " already exists");
        }
        return null;
    }
    public static void deleteGroup(String title){
        String query = "DELETE FROM " + DBWorkspace.productTableName + " WHERE NameOfGroup = ?";

        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, title);

            preparedStatement.executeUpdate();

            deleteProductByGroup(title);

            System.out.println("Deleted group with title = \"" + title + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateGroup(String oldTitle, String newTitle, String newDescription){
        String query = "UPDATE " + DBWorkspace.productTableName + " SET ";
        query = fullfillingQuery(oldTitle, newTitle, newDescription, "", "", "", 0, query);
        System.out.println("query is " + query);

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);


            for(int i = 1; i < 4; i++) {
                String variable = "";
                if(i == 1 & !newTitle.isEmpty())  variable = newTitle;
                else if(i <= 2 & !newDescription.isEmpty())
                {
                    variable = newDescription;
                    newDescription = "";
                }
                else if(i <= 2 & !oldTitle.isEmpty())
                {
                    variable = oldTitle;
                    oldTitle = "";
                }

                if(!variable.isEmpty())
                    preparedStatement.setString(i, variable);
            }

            preparedStatement.executeUpdate();

            updateProductByGroup(oldTitle, newTitle);

        } catch (SQLException e) {
            Controller.response.setStatusCode(404);
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

    private static String fullfillingQuery(String oldNameOfProduct, String newNameOfProduct, String description, String manufacturer,
                                    String Price, String ProductGroup, int Amount, String query)
    {
        boolean first = true;
        if(!oldNameOfProduct.isEmpty()) {
            if (query.contains("WHERE")) {
                query += " NameOfProduct = ?";
                first = false;
            }
            if (!newNameOfProduct.isEmpty()) {
                if(query.contains(DBWorkspace.productTableName))
                    query+= " NameOfGroup = ?";
                else
                    query += " NameOfProduct = ?";
                first = false;
            }

            if (!description.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " Description = ?";
                first = false;
            }
            if (!manufacturer.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " Manufacturer = ?";
            }

            if (!Price.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " Price = ?";
            }

            if (!ProductGroup.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " ProductGroup = ?";
            }
            if (Amount != 0) {
                if (!first) query += " AND ";
                query += " Amount = ?";
            }

            if (first)
                System.out.println("No input");
            else
                System.out.println(query);
        }
        else
        {
            System.out.println("Enter old title of the product");
        }
        return query;
    }


    //read; code - 9
    public static ResultSet showProduct(String oldNameOfProduct, String description, String manufacturer,
                                     String Price, String ProductGroup, int Amount){

        //String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE id = ?";
        String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE ";


        query = fullfillingQuery(oldNameOfProduct, "", description, manufacturer,
                Price, ProductGroup, Amount, query);

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);
            for(int i = 1; i < 7; i++) {
                String variable = "";
                if(i == 1 & !oldNameOfProduct.isEmpty())  {
                    variable = oldNameOfProduct;
                    oldNameOfProduct = "";
                }
                else if(i <= 2 & !description.isEmpty())
                {
                    variable = description;
                    description = "";
                }
                else if(i <= 3 & !manufacturer.isEmpty())
                {
                    variable = manufacturer;
                    manufacturer = "";
                }
                else if(i <= 4 & !Price.isEmpty())  {
                    variable = Price;
                    Price = "";
                }
                else if(i <= 5 & !ProductGroup.isEmpty())  {
                    variable = ProductGroup;
                    ProductGroup = "";
                }
                else if(i <= 6 & !oldNameOfProduct.isEmpty())  {
                    variable = oldNameOfProduct;
                    oldNameOfProduct = "";
                }
                else if(i <= 7 & Amount != 0)  {
                    try {
                        preparedStatement.setInt(i, Amount);
                    }
                    catch (Exception e){
                        break;
                    }
                }
                try {
                    if(variable!="")
                        preparedStatement.setString(i, variable);
                }
                catch(Exception e)
                {
                    break;
                }
            }

            //System.out.println("query is " + preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            //System.out.println(resultSet.next());
            showProducts(resultSet);
            return null;



        } catch (SQLException e) {
            Controller.response.setStatusCode(404);
        }
        return null;
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
        String query = "DELETE FROM " + DBWorkspace.tableName + " WHERE NameOfProduct = ?";

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
    public static void deleteProductByGroup(String title){
        String query = "DELETE FROM " + DBWorkspace.tableName + " WHERE ProductGroup = ?";
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
    public static void updateProductByGroup(String oldTitle, String newTitle){
        String query = "UPDATE " + DBWorkspace.tableName + " SET ProductGroup = ? WHERE ProductGroup = ?";
        try
        {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, oldTitle);

            preparedStatement.executeUpdate();

            //System.out.println("Deleted product with title = \"" + title + "\"");
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
    public static void updateProduct(String oldNameOfProduct, String newNameOfProduct, String description, String manufacturer,
                                     String Price, String ProductGroup){
        boolean first = true;
       /* String query =
                "UPDATE Product SET Description = ?,  Manufacturer = ?,  ProductGroup = ? WHERE NameOfProduct = ?";
        */
        String query = "UPDATE " + DBWorkspace.tableName + " SET";

        query = fullfillingQuery(oldNameOfProduct, newNameOfProduct, description, manufacturer,
                Price, ProductGroup, 0, query);
        query += " WHERE NameOfProduct = ?";

        System.out.println(query);

       try {
           try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {

               for(int i = 1; i < 6; i++) {
                   String variable = "";
                   if(i == 1 & !newNameOfProduct.isEmpty())  variable = newNameOfProduct;
                   else if(i <= 2 & !description.isEmpty())
                   {
                       variable = description;
                       description = "";
                   }
                   else if(i <= 3 & !manufacturer.isEmpty())
                   {
                       variable = manufacturer;
                       manufacturer = "";
                   }
                   else if(i <= 4 & !Price.isEmpty())  {
                       variable = Price;
                       Price = "";
                   }
                   else if(i <= 5 & !ProductGroup.isEmpty())  {
                       variable = ProductGroup;
                       ProductGroup = "";
                   }
                   else if(i <= 6 & !oldNameOfProduct.isEmpty())  {
                       variable = oldNameOfProduct;
                       oldNameOfProduct = "";
                   }
                   if(!variable.isEmpty())
                        preparedStatement.setString(i, variable);
               }

               preparedStatement.executeUpdate();
           }

           //System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");*//*
        } catch (SQLException e) {
           if(e.getMessage().contains("PRIMARYKEY"))
                System.out.println("Wrong name, duplicates");
           else
                System.out.println("Wrong group input");

           //e.printStackTrace();
           Controller.response.setStatusCode(404);
        }

    }
    public static void updateProductAmount(String nameOfProduct, int change){

        String query = "UPDATE " + DBWorkspace.tableName + " SET Amount = Amount + " + change + " WHERE NameOfProduct = ?";


        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);
            preparedStatement.setString(1, nameOfProduct);


            preparedStatement.executeUpdate();


            //System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");*//*
        } catch (SQLException e) {


            System.out.println("Wrong group input");

            e.printStackTrace();
            Controller.response.setStatusCode(404);
        }

    }
}
