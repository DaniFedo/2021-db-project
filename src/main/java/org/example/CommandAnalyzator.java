package org.example;

public class CommandAnalyzator{

    public static void analyze(int commandType, String messageString) throws Exception {
        String commandName = CommandTypeEncoder.getTypeCommand(commandType);
        Database.connect();
        //if("CREATE" == commandName) Table.addProduct(messageString);
        /*else if("DELETE" == commandName) Table.deleteProduct(messageString);
        else if("LIST_BY_CRITERIA" == commandName) Table.showProductByTitle(messageString);
        else if ("READ" == commandName) Table.showAllProducts();*/
        Database.close();
    }

    public static void analyze(int commandType, int messageInt) throws Exception {
        String commandName = CommandTypeEncoder.getTypeCommand(commandType);
        Database.connect();
        /*if("READ" == commandName) Table.showProduct(messageInt);
        else if("DELETE" == commandName) Table.deleteProduct(messageInt);
        else if("LIST_BY_CRITERIA" == commandName) Table.showProductsInRange(messageInt);
       */ Database.close();
    }

    public static void analyze(int commandType, int messageInt, String messageString) throws Exception{
        String commandName = CommandTypeEncoder.getTypeCommand(commandType);
        Database.connect();
        //if("UPDATE" == commandName) Table.updateProduct(messageInt, messageString);
        /*else if("DELETE" == commandName) Table.deleteProduct(messageInt, messageString);
        else if("LIST_BY_CRITERIA" == commandName) Table.showProductByTitle(messageString, messageInt);*/
        Database.close();
    }

    public static void analyze(int commandType, int messageInt1, int messageInt2) throws Exception{
        String commandName = CommandTypeEncoder.getTypeCommand(commandType);
        Database.connect();
        if("LIST_BY_CRITERIA" == commandName) Table.showProductsInRange(messageInt1, messageInt2);
        Database.close();
    }



    }
