package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    String expectedResult = "testText";
    Message testMessage = new Message(1,1,expectedResult);

    private SecretKey keyForTest() throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();

        short keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        return keyGenerator.generateKey();
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void messageEncodingAndDecodingTest() throws Exception
    {
        //

        SecretKey testKey = keyForTest();

        //
        testMessage.encode(testKey);
        testMessage.decode(testKey);
        String actualResult = testMessage.message;

        //
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testPackagingAndDepackaging() throws Exception
    {
        //
        Packet testPackage = new Packet((byte)1, 1, testMessage);

        //
        byte [] testPacketPackaging = testPackage.packetPackaging();
        Packet testPackage2 = new Packet(testPacketPackaging);

        //
        Assert.assertEquals(testPackage.bPktId, testPackage2.bPktId);

    }
}
