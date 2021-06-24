package org.example;

import java.nio.charset.StandardCharsets;

public class MessageDecryptor {
    private static String[] stringArray;
    private static byte[] messageInput;
    private static int i = 0;
    private static int number = 0;
    private static String checkString = "";

    public static void decryptAddProduct(String messageString) {
        i = 0;
        number = 0;
        checkString = "";

        decryptingString(messageString, 5);
        Table.addProduct(stringArray[0], stringArray[1], stringArray[2],
                Double.parseDouble(stringArray[3]), stringArray[4]);

    }
    public static void decryptAddGroup(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString, 2);
        Table.addGroup(stringArray[0], stringArray[1]);
    }

    public static String[] decryptShowProduct(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,6);

        return Table.showProduct(stringArray[0], stringArray[1], stringArray[2],
                Double.parseDouble(stringArray[3]), stringArray[4], Double.parseDouble(stringArray[5]));
    }
    public static String[] decryptShowAllProducts(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,1);
        if(stringArray[0] == null) {
            String[] test = Table.showAllProducts();
            System.out.println("NEW TEST HERE " + test[0]);
            return test;
        }
        else {
            return Table.showAllProducts(stringArray[0]);
        }
    }

    public static void decryptUpdateProduct(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,6);

        Table.updateProduct(stringArray[0],stringArray[1],stringArray[2], stringArray[3],
                Double.parseDouble(stringArray[4]), stringArray[5]);

    }
    public static void decryptUpdateGroup(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,3);

        Table.updateGroup(stringArray[0],stringArray[1],stringArray[2]);
    }

    public static void decryptDelete(String messageString, boolean Product){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,1);
        if(Product) Table.deleteProduct(stringArray[0]);
        else Table.deleteGroup(stringArray[0]);
    }

    public static String[] decryptFullPrice(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,1);
        if(stringArray[0] == null)
            return Table.fullPrice();
        else {

            return Table.fullPrice(stringArray[0]);
        }
    }
    public static void decryptUpdatingAmoung(String messageString){
        i = 0;
        number = 0;
        checkString = "";
        decryptingString(messageString,2);

        Table.updateProductAmount(stringArray[0],Double.parseDouble(stringArray[1]));
    }



    public static String[] decryptingString(String messageString, int amountOfElements){
        stringArray = new String[amountOfElements];
        messageInput = messageString.getBytes(StandardCharsets.UTF_8);
        while(i!=messageInput.length)
        {

            if((char)messageInput[i]!=',') {
                if((char)messageInput[i] != '"')
                    checkString += (char) messageInput[i];
            }
            else if ((char)messageInput[i] != '"'){
                stringArray[number] = checkString;
                checkString = "";
                number++;
            }
            if(i == messageInput.length - 1) stringArray[number] = checkString;
            i++;
        }
        for(int n = 0; n < amountOfElements; n++)
        {
            System.out.println("output here: " + stringArray[n]);
        }
        return stringArray;
    }

    public static String[] decryptingForClient(String messageString, int amountOfElements){
        number = 0;

        checkString = "";
        stringArray = new String[amountOfElements];
        messageInput = messageString.getBytes(StandardCharsets.UTF_8);
        while(number != amountOfElements)
        {
            if((char)messageInput[StoreClientTCP.amount] == '.')
                if(number == amountOfElements - 1 || number == amountOfElements - 4) {
                    stringArray[number] = checkString;
                    checkString = "";
                    number++;
                    if(number == amountOfElements - 4)
                        number = 5;
                }
            if((char)messageInput[StoreClientTCP.amount]!=',') {
                if((char)messageInput[StoreClientTCP.amount] != '"')
                    checkString += (char) messageInput[StoreClientTCP.amount];
            }
            else if ((char)messageInput[StoreClientTCP.amount] != '"'){
                stringArray[number] = checkString;
                checkString = "";
                number++;
            }
            if(StoreClientTCP.amount == messageInput.length - 1) stringArray[number] = checkString;
            StoreClientTCP.amount++;
        }
        return stringArray;
    }





















    /*private static boolean checkForComma(byte[] message){
        for(int i = 0; i < message.length; i++)
            if((char)message[i] == ',') return true;
        return false;
    }

    private static boolean checkForString(byte[] message){
        for(int i = 0; i < message.length; i++)
            if((char)message[i] >= 65 && (char)message[i] <= 122) return true;
        return false;
    }

    public static void decryptMessage(Message messageInput) throws Exception {
        byte[] message = messageInput.message.getBytes(StandardCharsets.UTF_8);
        if(checkForComma(message)) decryptMessageWithIntAndString(messageInput);
        else if (checkForString(message)) processMessageWithString(messageInput);
        else processMessageWithInt(messageInput);
    }
    private static void decryptMessageWithIntAndString(Message messageInput) throws Exception {
        byte[] message = messageInput.message.getBytes(StandardCharsets.UTF_8);
        int commandType = messageInput.cType;
        int counter = 0;
        int counter2 = 0;
        int positionOfComma = 0;

        for(int i = 0; i < message.length; i++)
            if((char) message[i] == ',') positionOfComma = i;


        char[] messageFirstPart =  new char[positionOfComma];
        char[] messageSecondPart =  new char[message.length - positionOfComma - 1];
        while(message[counter]!= ','){
            messageFirstPart[counter] = (char)message[counter];
            counter++;
        }

        counter++;
        while(counter < message.length){
            messageSecondPart[counter2] = (char)message[counter];
            counter++;
            counter2++;
        }

        int messageInt = Integer.parseInt(String.valueOf(messageFirstPart));
        boolean doubleInt = false;
        for(int i = 0; i < 10; i++)
        {
            int check = messageSecondPart[0];
            if(check == i + 48) doubleInt = true;
        }

        if(doubleInt) CommandAnalyzator.analyze(commandType, messageInt, Integer.parseInt(String.valueOf(messageSecondPart)));
        else {
            String messageString = String.valueOf(messageSecondPart);
            CommandAnalyzator.analyze(commandType, messageInt, messageString);
        }

    }

    private static void processMessageWithInt(Message messageInput) throws Exception {
        char[] messageChar = decryptionForStringOrInt(messageInput);
        int commandType = messageInput.cType;
        int messageInt = Integer.parseInt(String.valueOf(messageChar));
        CommandAnalyzator.analyze(commandType, messageInt);
    }

    private static void processMessageWithString(Message messageInput) throws Exception {
        char[] messageChar = decryptionForStringOrInt(messageInput);
        int commandType = messageInput.cType;
        String messageString = String.valueOf(messageChar);

        CommandAnalyzator.analyze(commandType, messageString);

    }

    private static char[] decryptionForStringOrInt(Message messageInput){
        byte[] message = messageInput.message.getBytes(StandardCharsets.UTF_8);
        char[] messageChar = new char[message.length];
        for(int i = 0; i < message.length; i++)
            messageChar[i] = (char) message[i];

        return messageChar;
    }*/
    }
