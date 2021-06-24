package org.example;

public class CommandAnalyzator {


    public static String[] analyze(Message message) {

        String[] result = takeACommand(message);
        System.out.println("RES IS " + result[0]);
        try {
            System.out.println("RES IS " + result[0]);
            System.out.println(result[2] == "");

            return result;
        }
        catch(Exception e){
            return null;
        }

    }

    private static String[] takeACommand(Message message) {
        String messageString = message.message;
        try {
            Database.connect();
            int command = message.cType;
            messageString = message.message;
            String commandName = CommandTypeEncoder.getTypeCommand(command);
            if (commandName.equals("CREATE")) {
                if(command == 5) MessageDecryptor.decryptAddProduct(messageString);
                else if(command  == 6) MessageDecryptor.decryptAddGroup(messageString);
            }



            if (commandName.equals("READ")){
                if(command == 9) return MessageDecryptor.decryptShowProduct(messageString);
                else if(command  == 10) return MessageDecryptor.decryptShowAllProducts(messageString);
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
                if(command == 65) return MessageDecryptor.decryptFullPrice(messageString);
                else if(command == 66) MessageDecryptor.decryptUpdatingAmoung(messageString);
            };
            Database.close();
        } catch (Exception e) {

        }


        return null;
    }
}
