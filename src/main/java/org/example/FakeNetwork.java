package org.example;

import java.security.SecureRandom;

public class FakeNetwork implements Network {


    //random generation almost everywhere where was possible
    @Override
    public byte[] generate() throws Exception {
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

    @Override
    public void sendMessage(Packet packet){
        //fake output
        System.out.println(packet);
    }
}
