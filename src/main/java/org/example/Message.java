package org.example;


import lombok.Data;

import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


@Data
public class Message
{
        int cType;
        int bUserId;
        String message;

        public Message(){}

        public Message(int cType, int bUserId, String message)
        {
            this.cType = cType;
            this.bUserId = bUserId;
            this.message = message;
        }

        public byte[] messagePackaging()
        {
            return ByteBuffer.allocate(messageLength())
                            .putInt(cType)
                            .putInt(bUserId)
                            .put(message.getBytes(StandardCharsets.UTF_8)).array();
        }
        /*public static void main(String args[]) throws Exception {
            Cipher cipher = new Cipher();
            Message message1 = new Message(1,2,"onetwoThree");

            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

            SecureRandom secureRandom = new SecureRandom();

            short keyBitSize = 256;
            keyGenerator.init(keyBitSize, secureRandom);

            SecretKey secretKey = keyGenerator.generateKey();

            String text;
            message1.encode(secretKey);
            text = message1.message;
            System.out.println(text);
            message1.decode(secretKey);
            text=message1.message;
            System.out.println(text);

            System.out.println(message1.messageLengthCoded(secretKey));

        }*/
        public int messageLengthCoded(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.encode(message, secretKey);
            return message.length();
        }

        public int messageLength()
        {
            return Integer.BYTES * 2 + message.length();
        }

        //unused for now in main code, because implemented in messagePackaging
        public void encode(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.encode(message, secretKey);
        }

        public void decode(SecretKey secretKey) throws Exception
        {
            Cipher cipher = new Cipher();
            message = cipher.decode(message, secretKey);
        }










}
