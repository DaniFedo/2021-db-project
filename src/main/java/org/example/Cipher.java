package org.example;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cipher {
    private javax.crypto.Cipher cipher;

    private synchronized void initialization() throws Exception {
        cipher = javax.crypto.Cipher.getInstance("AES");

    }

    public synchronized String encode(String message, SecretKey secretKey) throws Exception {
        initialization();
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);

        byte[] cipherText = cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);

    }

    public synchronized String decode(String message, SecretKey secretKey) throws Exception {

        initialization();
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);

        byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(message));
        return new String(cipherText);
    }
}
