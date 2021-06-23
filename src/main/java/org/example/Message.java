package org.example;


import lombok.Data;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Data
public class Message
{
        int cType;
        String message;
        public SecretKey secretKey;
        int messageLen;

        public Message(){}

        public Message(int cType, String message) throws Exception {
            this.cType = cType;
            this.message = message;
            keyMaking();
            messageLen = message.length();
            System.out.println("messageLen is " + messageLen);
        }
        public Message(byte[] messageEncoded)
        {
            try {
                ByteBuffer byteBuffer = ByteBuffer.wrap(messageEncoded);
                cType = byteBuffer.getInt();

                messageLen = byteBuffer.getInt();
                int keyEncodedLength = byteBuffer.getInt();
                byte[] keyEncoded = new byte[keyEncodedLength];
                byteBuffer.get(keyEncoded);
                secretKey = new SecretKeySpec(keyEncoded, 0, keyEncodedLength, "AES");

                byte[] messageText = new byte[messageLen];
                byteBuffer.get(messageText);
                message = new String(messageText);
                decode(secretKey);
            }
            catch(Exception e) {}

        }
    private void keyMaking() throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();

        short keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        secretKey = keyGenerator.generateKey();
    }

        public byte[] messagePackaging(){
            try {
                encode(secretKey);
                byte[] keyEncoded = secretKey.getEncoded();
                return ByteBuffer.allocate(messageLength() + keyEncoded.length)
                        .putInt(cType)
                        .putInt(messageLen)
                        .putInt(keyEncoded.length)
                        .put(keyEncoded)
                        .put(message.getBytes(StandardCharsets.UTF_8)).array();
            }
            catch(Exception e)
            {

            }
            return null;
        }

        public int messageLengthCoded(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.encode(message, secretKey);
            return message.length();
        }

        public int messageLength()
        {
            return Integer.BYTES * 3 + 50;
        }

        public void encode(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.encode(message, secretKey);
            messageLen = message.length();
        }

        public void decode(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.decode(message, secretKey);
        }










}
