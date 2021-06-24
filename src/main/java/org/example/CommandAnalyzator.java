package org.example;

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
            else if (commandName.equals("UPDATE")){
                if(command == 17) MessageDecryptor.decryptUpdateProduct(messageString);
                else if(command == 18) MessageDecryptor.decryptUpdateGroup(messageString);
            }
            else if (commandName.equals("DELETE")){
                if(command == 33) MessageDecryptor.decryptDelete(messageString, true);
                else if(command == 34) MessageDecryptor.decryptDelete(messageString, false);
            }
            else if (commandName.equals("LIST_BY_CRITERIA")){
                if(command == 65) MessageDecryptor.decryptFullPrice(messageString);
                else if(command == 66) MessageDecryptor.decryptUpdatingAmoung(messageString);
            };
            Database.close();
        } catch (Exception e) {

        }


    }
}
