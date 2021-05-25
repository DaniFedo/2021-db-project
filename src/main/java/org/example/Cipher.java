package org.example;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Cipher
{
    private javax.crypto.Cipher cipher;
    private void initialization() throws Exception
    {
        cipher = javax.crypto.Cipher.getInstance("AES");

    }
    public String encode( String message, SecretKey secretKey) throws Exception
    {
        initialization();
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);

        //System.out.println("secretKey is " + secretKey);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(cipherText);

    }

    public String decode(String message, SecretKey secretKey) throws Exception
    {

        initialization();
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
       // System.out.println("secretKey is .2 " + secretKey);

        byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(cipherText);
    }
}
