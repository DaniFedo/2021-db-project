package org.example;

import java.security.SecureRandom;

public class FakeNetwork implements Network {



    @Override
    public Packet generate() throws Exception {
        byte[] cTypes = {5, 6, 9, 10, 17, 18, 33, 34};

        SecureRandom random = new SecureRandom();
        int randomCommandNumber = random.nextInt(8);
        char[] generatedText = new char[randomCommandNumber];

        for(int i = 0; i < randomCommandNumber; i++)
        {
            generatedText[i] = (char)(random.nextInt(58) + 65);
        }

        Message generatedMessage = new Message(cTypes[randomCommandNumber], randomCommandNumber, String.valueOf(generatedText));
        //System.out.println(generatedMessage.message);

        return new Packet((byte)(randomCommandNumber + 1), random.nextLong(), generatedMessage);
    }
}
