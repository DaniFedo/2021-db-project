package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {
    private static void showProductsForPrice(ResultSet resultSet) {

        //ResultSet resultSet = selectAllProducts();

        //System.out.println(resultSet.next());
        try {
            if(resultSet.next() == false){
                System.out.println("Query result is empty");
            }
            while (resultSet.next()) {
                System.out.println(/*resultSet.getString("NameOfProduct") + "\t" + resultSet.getString("Description")
                        + "\t" + resultSet.getString("Manufacturer") + "\t" + */

                        resultSet.getDouble("Amount")
                                + "\t" + resultSet.getDouble("Price")

                                /*
                                + "\t" + resultSet.getString("ProductGroup")*/);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showProducts(ResultSet resultSet) {
        boolean empty = true;
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("NameOfProduct") + "\t" + resultSet.getString("Description")
                        + "\t" + resultSet.getString("Manufacturer") + "\t" +

                        resultSet.getDouble("Amount")
                        + "\t" + resultSet.getDouble("Price")


                                + "\t" + resultSet.getString("ProductGroup"));

                empty = false;
            }
            if(empty) System.out.println("Query result is empty");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static double calculateSum(ResultSet resultSet) {
        double result = 0;
        try {
            while (resultSet.next()) {
                    result +=
                        resultSet.getDouble("Amount") * resultSet.getDouble("Price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //create table
    public static void create(String tableName) {

        String query;
        if(tableName.equals("Product")) {
             query = "CREATE TABLE IF NOT EXISTS Product " +
                    " (NameOfProduct VARCHAR(45) PRIMARY KEY, Description VARCHAR(45), Manufacturer VARCHAR(45)," +
                     " Amount double, Price double, ProductGroup VARCHAR(45), " +
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

    private static ResultSet getAllGroups(){
        String query = "SELECT NameOfGroup FROM " + DBWorkspace.productTableName;

        try {
            Statement statement = Database.connection.createStatement();
            //System.out.println("All groups:");
            //showProducts(statement.executeQuery(query));
            //return statement.executeQuery(query);
            return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //create product; code - 5
    public static String addProduct(String title, String description, String manufacturer,
                                    double Price, String ProductGroup) throws SQLException {

        String query = "INSERT INTO " + DBWorkspace.tableName + " (NameOfProduct, Description, Manufacturer" +
                ", Price, ProductGroup, Amount) VALUES(?, ?, ?, ?, ?, 0.0)";

        boolean check = false;
        ResultSet checkingSet = getAllGroups();
        while(checkingSet.next()) {
            if (checkingSet.getString("NameOfGroup").equals(ProductGroup)) check = true;
        }

        if(check && Price > 0) {
            try {
                PreparedStatement preparedStatement = Database.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, description);
                preparedStatement.setString(3, manufacturer);
                preparedStatement.setDouble(4, Price);
                preparedStatement.setString(5, ProductGroup);

                preparedStatement.executeUpdate();
                return "Added new element: " + title;

            } catch (SQLException e) {
                System.out.println("This product already exists");
            }
        }
        else
        {
            if(Price <= 0)
                System.out.println("Price must be higher than 0");
            else
                System.out.println("You've entered a product group which does not exist");
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
    public static void deleteGroup(String title) throws SQLException {
        String query = "DELETE FROM " + DBWorkspace.productTableName + " WHERE NameOfGroup = ?";

        boolean check = true;
        if(!title.equals("")) {
            check = false;
            ResultSet checkingSet = getAllGroups();
            while (checkingSet.next()) {
                if (checkingSet.getString("NameOfGroup").equals(title)) check = true;
            }
        }
        if(check) {
            try {
                PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

                preparedStatement.setString(1, title);

                preparedStatement.executeUpdate();

                deleteProductByGroup(title);

                System.out.println("Deleted group with title = \"" + title + "\"");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("There is no such group");
    }
    public static void updateGroup(String oldTitle, String newTitle, String newDescription){
        String query = "UPDATE " + DBWorkspace.productTableName + " SET ";
        query = fullfillingQuery(oldTitle, newTitle, newDescription, "", -1, "", -1, query);
        query += " WHERE NameOfGroup = ?";
        System.out.println("query is " + query);

        try {
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);

            String check = oldTitle;
            for(int i = 1; i < 4; i++) {
                String variable = "";
                if(i == 1 & !newTitle.isEmpty())  variable = newTitle;
                else if(i <= 2 & !newDescription.isEmpty())
                {
                    variable = newDescription;
                    newDescription = "";
                }
                else if(i <= 3 & !oldTitle.isEmpty())
                {
                    variable = oldTitle;
                    oldTitle = "";
                }

                if(!variable.isEmpty())
                    preparedStatement.setString(i, variable);
            }

            preparedStatement.executeUpdate();

            updateProductByGroup(check, newTitle);

        } catch (SQLException e) {
            System.out.println("Group with such name already exists");
        }
    }


    //list by criteria; code - 65


    private static String fullfillingQuery(String oldNameOfProduct, String newNameOfProduct, String description, String manufacturer,
                                    double Price, String ProductGroup, double Amount, String query)
    {
        boolean emptyOldName = oldNameOfProduct.isEmpty();
        boolean first = true;
        if(!emptyOldName || query.contains("SELECT")) {
            if (query.contains("WHERE") && !emptyOldName) {
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
                if(description.equals("null")) query+= " Description IS NULL";
                else  query += " Description = ?";
                first = false;
            }
            if (!manufacturer.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                if(manufacturer.equals("null")) query+= " Manufacturer IS NULL";
                else  query += " Manufacturer = ?";
                first = false;
            }

            if (Price > 0) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " Price = ?";
                first = false;
            }

            if (!ProductGroup.isEmpty()) {
                if (!first)
                {
                    if (query.contains("SET")) query += ", ";
                    else if (query.contains("WHERE")) query += " AND ";

                }
                query += " ProductGroup = ?";
                first = false;
            }
            if (Amount >= 0.0) {
                if (!first) query += " AND ";
                query += " Amount = ?";
                first = false;
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
    public static ResultSet showProduct(String nameOfProduct, String description, String manufacturer,
                                     double Price, String ProductGroup, double Amount){

        //String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE id = ?";
        String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE ";
        String checkQuery = query;


        query = fullfillingQuery(nameOfProduct, "", description, manufacturer,
                Price, ProductGroup, Amount, query);

        if(query.equals(checkQuery)) showAllProducts();

        else {
            try {
                PreparedStatement preparedStatement = Database.connection.prepareStatement(query);
                for (int i = 1; i < 7; i++) {
                    String variable = "";
                    if (i == 1 & !nameOfProduct.isEmpty()) {
                        variable = nameOfProduct;
                        nameOfProduct = "";
                    } else if (i <= 2 & !description.isEmpty() & !description.equals("null")) {
                        variable = description;
                        description = "";
                    } else if (i <= 3 & !manufacturer.isEmpty() & !manufacturer.equals("null")) {
                        variable = manufacturer;
                        manufacturer = "";
                    } else if (i <= 4 & Price > 0) {
                        try {
                            preparedStatement.setDouble(i, Price);
                            Price = 0;
                        } catch (Exception e) {
                            break;
                        }
                    } else if (i <= 5 & !ProductGroup.isEmpty()) {
                        variable = ProductGroup;
                        ProductGroup = "";
                    } else if (i <= 6 & !nameOfProduct.isEmpty()) {
                        variable = nameOfProduct;
                        nameOfProduct = "";
                    } else if (i <= 7 && Amount >= 0) {
                        try {
                            preparedStatement.setDouble(i, Amount);
                            Amount = -1;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    try {
                        if (variable != "")
                            preparedStatement.setString(i, variable);
                    } catch (Exception e) {
                        break;
                    }
                }

                //System.out.println("query is " + preparedStatement);
                ResultSet resultSet = preparedStatement.executeQuery();

                //System.out.println(resultSet.next());
                showProducts(resultSet);
                return null;


            } catch (SQLException e) {
                e.printStackTrace();
            }
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
    public static void showAllProducts(String groupName) throws SQLException {
        String query = "SELECT * FROM " + DBWorkspace.tableName + " WHERE ProductGroup = \"" + groupName + "\";";
        boolean check = true;
        if (!groupName.equals("")) {
            check = false;
            ResultSet checkingSet = getAllGroups();
            while (checkingSet.next()) {
                if (checkingSet.getString("NameOfGroup").equals(groupName)) check = true;
            }
        }
        if(check) {
            try {
                Statement statement = Database.connection.createStatement();
                System.out.println("All products in " + groupName + " group:");
                showProducts(statement.executeQuery(query));
                //return statement.executeQuery(query);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("You've entered wrong group name");


    }



    public static void fullPrice(){
        String query = "SELECT Amount, Price FROM " + DBWorkspace.tableName;

        try {
            Statement statement = Database.connection.createStatement();
            System.out.println("All amount + price:");
            showProductsForPrice(statement.executeQuery(query));
            double fullPriceAmount = calculateSum(statement.executeQuery(query));
            System.out.println("Full price is " + fullPriceAmount);
            //return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void fullPrice(String GroupName) throws SQLException {
        String query = "SELECT Amount, Price FROM " + DBWorkspace.tableName + " WHERE ProductGroup = \"" + GroupName + "\"";
        boolean check = true;
        if (!GroupName.equals("")) {
            check = false;
            ResultSet checkingSet = getAllGroups();
            while (checkingSet.next()) {
                if (checkingSet.getString("NameOfGroup").equals(GroupName)) check = true;
            }
        }
        if(check) {
            try {
                Statement statement = Database.connection.createStatement();
                System.out.println("All amount + price:");
                showProductsForPrice(statement.executeQuery(query));
                double fullPriceAmount = calculateSum(statement.executeQuery(query));
                System.out.println("Full price is " + fullPriceAmount);
                //return statement.executeQuery(query);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("You've entered wrong group name");
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

    //update; code - 17
    public static void updateProduct(String oldNameOfProduct, String newNameOfProduct, String description, String manufacturer,
                                     double Price, String ProductGroup) throws SQLException {
        boolean first = true;
       /* String query =
                "UPDATE Product SET Description = ?,  Manufacturer = ?,  ProductGroup = ? WHERE NameOfProduct = ?";
        */
        if(Price >= 0) {
            String query = "UPDATE " + DBWorkspace.tableName + " SET";

            query = fullfillingQuery(oldNameOfProduct, newNameOfProduct, description, manufacturer,
                    Price, ProductGroup, -1, query);
            query += " WHERE NameOfProduct = ?";

            System.out.println(query);

            boolean check = true;
            if (!ProductGroup.equals("")) {
                check = false;
                ResultSet checkingSet = getAllGroups();
                while (checkingSet.next()) {
                    if (checkingSet.getString("NameOfGroup").equals(ProductGroup)) check = true;
                }
            }
            if (check) {
                try {
                    try (PreparedStatement preparedStatement = Database.connection.prepareStatement(query)) {

                        for (int i = 1; i < 7; i++) {
                            String variable = "";
                            if (i == 1 & !newNameOfProduct.isEmpty()) variable = newNameOfProduct;
                            else if (i <= 2 & !description.isEmpty()) {
                                variable = description;
                                description = "";
                            } else if (i <= 3 & !manufacturer.isEmpty() & !manufacturer.equals("null")) {
                                variable = manufacturer;
                                manufacturer = "";
                            } else if (i <= 4 & Price > 0) {
                                try {
                                    preparedStatement.setDouble(i, Price);
                                    Price = 0;
                                } catch (Exception e) {
                                    break;
                                }
                            } else if (i <= 5 & !ProductGroup.isEmpty()) {
                                variable = ProductGroup;
                                ProductGroup = "";
                            } else if (i <= 6 & !oldNameOfProduct.isEmpty()) {
                                variable = oldNameOfProduct;
                                oldNameOfProduct = "";
                            }
                            if (!variable.isEmpty())
                                preparedStatement.setString(i, variable);
                        }

                        preparedStatement.executeUpdate();
                    }

                    //System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");*//*
                } catch (SQLException e) {
                    if (e.getMessage().contains("PRIMARYKEY"))
                        System.out.println("Wrong name, duplicates");
                    else
                        System.out.println("Wrong group input");


                }
            } else {
                System.out.println("You've entered the wrong productGroup.");
            }
        }
        else
            System.out.println("You should enter price higher than 0");

    }
    private static boolean checkProductAmount(String nameOfProduct, double change){
        String query = "SELECT Amount FROM " + DBWorkspace.tableName + " WHERE NameOfProduct = \"" + nameOfProduct + "\"";

        try {
            Statement statement = Database.connection.createStatement();
            if(statement.executeQuery(query).next()) {
                if (statement.executeQuery(query).getInt("Amount") + change < 0) {
                    return false;
                } else return true;
            }


            //System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");*//*
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }
    public static void updateProductAmount(String nameOfProduct, double change){

        String query = "UPDATE " + DBWorkspace.tableName + " SET Amount = Amount + " + change + " WHERE NameOfProduct = ?";


        if(checkProductAmount(nameOfProduct, change)) {
            try {
                PreparedStatement preparedStatement = Database.connection.prepareStatement(query);
                preparedStatement.setString(1, nameOfProduct);


                preparedStatement.executeUpdate();


                //System.out.println("Updated product with id = " + id + " on new title = \"" + title + "\"");*//*
            } catch (SQLException e) {


                System.out.println("Wrong group input");

                e.printStackTrace();
            }
        }
        else System.out.println("You can't proceed this operation. Amount can't be less than 0.");

    }
}
