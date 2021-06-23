package org.example;

import javax.xml.crypto.Data;

public class CommandAnalyzator {


    public static void analyze(Message message) {

            takeACommand(message);

    }

    private static void takeACommand(Message message) {
        try {
            Database.connect();
            int command = message.cType;
            String messageString = message.message;
            String commandName = CommandTypeEncoder.getTypeCommand(command);
            if (commandName.equals("CREATE")) {
                if(command == 5) MessageDecryptor.decryptAddProduct(messageString);
                else if(command  == 6) MessageDecryptor.decryptAddGroup(messageString);
            }



            if (commandName.equals("READ")){
                if(command == 9) MessageDecryptor.decryptShowProduct(messageString);
                else if(command  == 10) MessageDecryptor.decryptShowAllProducts(messageString);
            }
            if (commandName.equals("UPDATE")){
                if(command == 17) MessageDecryptor.decryptUpdateProduct(messageString);
                if(command == 18) MessageDecryptor.decryptUpdateGroup(messageString);
            }
            if (commandName.equals("DELETE")) System.out.println("It's DELETE");
            if (commandName.equals("LIST_BY_CRITERIA")) System.out.println("It's LIST_BY_CRITERIA");
            Database.close();
        } catch (Exception e) {

        }


    }
}
