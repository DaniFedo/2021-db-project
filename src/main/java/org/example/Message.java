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
