package org.example;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class MessageDecryptor {

    private static boolean checkForComma(byte[] message){
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
    }
    }
