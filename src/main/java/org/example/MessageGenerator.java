package org.example;

import java.security.SecureRandom;

public class MessageGenerator{
    final byte[] cTypes = {5, 6, 9, 10, 17, 18, 33, 34};
    final SecureRandom random = new SecureRandom();


    public byte[] generateForClient(StoreClientTCP clientTCP) throws Exception {

        int randomCommandNumber = random.nextInt(8);
        char[] generatedText = new char[randomCommandNumber];

        for(int i = 0; i < randomCommandNumber; i++)
        {
            int test1 = random.nextInt(58) + 65;
            char test = (char)test1;
            generatedText[i] = test;
        }
        Message generatedMessage = new Message(cTypes[randomCommandNumber], clientTCP.clientSocket.getLocalPort(), String.valueOf(generatedText));

        return new Packet((byte)(randomCommandNumber + 1), random.nextLong(), generatedMessage).packetPackaging();
    }

    public byte[] generateForClient(StoreClientUDP clientUDP) throws Exception {

        int randomCommandNumber = random.nextInt(8);
        char[] generatedText = new char[randomCommandNumber];

        for(int i = 0; i < randomCommandNumber; i++)
        {
            int test1 = random.nextInt(58) + 65;
            char test = (char)test1;
            generatedText[i] = test;
        }
        Message generatedMessage = new Message(cTypes[randomCommandNumber], clientUDP.socket.getLocalPort(), String.valueOf(generatedText));

        return new Packet((byte)(randomCommandNumber + 1), random.nextLong(), generatedMessage).packetPackaging();
    }


    //random generation almost everywhere where was possible
    public byte[] generateClassic() throws Exception {

        byte[] cTypes = {5, 6, 9, 10, 17, 18, 33, 34};

        SecureRandom random = new SecureRandom();
        int randomCommandNumber = random.nextInt(8);
        char[] generatedText = new char[randomCommandNumber];

        for(int i = 0; i < randomCommandNumber; i++)
        {
            generatedText[i] = (char)(random.nextInt(58) + 65);
        }
        Message generatedMessage = new Message(cTypes[randomCommandNumber], randomCommandNumber, String.valueOf(generatedText));

        return new Packet((byte)(randomCommandNumber + 1), random.nextLong(), generatedMessage).packetPackaging();
    }
}
