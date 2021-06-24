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
            //System.out.println("NEW TEST HERE " + test[0]);
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
        number = 0;
        i = 0;

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
        /*for(int n = 0; n < amountOfElements; n++)
        {
            System.out.println("output here: " + stringArray[n]);
        }*/
        return stringArray;
    }

    public static String[] decryptingForClient(String messageString, int amountOfElements){
        i = 0;
        number = 0;

        checkString = "";
        stringArray = new String[amountOfElements];
        messageInput = messageString.getBytes(StandardCharsets.UTF_8);
        while(number != amountOfElements)
        {
            if(messageInput[StoreClientTCP.amount] == 0) break;
            if((char)messageInput[StoreClientTCP.amount] == '.')
                if(number == 0){
                    StoreClientTCP.amount++;
                    continue;
                }

                else if(number == amountOfElements - 1 || number == amountOfElements - 4) {

                    stringArray[number] = checkString;
                    checkString = "";
                    number++;
                    if(number == amountOfElements - 4)
                        number = 5;
                }
            if((char)messageInput[StoreClientTCP.amount]!=',') {
                if((char)messageInput[StoreClientTCP.amount] != '"')
                    if(checkString=="" && (messageInput[StoreClientTCP.amount] >= 48 &&
                            messageInput[StoreClientTCP.amount] <=57) && number == 0) {
                    }
                    else {
                        checkString += (char) messageInput[StoreClientTCP.amount];
                    }
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
    }
