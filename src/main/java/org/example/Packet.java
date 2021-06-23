package org.example;

import lombok.Data;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

@Data
public class Packet
{

    int wLen;

    Message message;
    short wCrc16_2;
    public SecretKey secretKey;
    public static int maxLength = Integer.BYTES * 3 + Short.BYTES  + 100;

    public Packet(Message message) throws Exception
    {

        this.message = message;
        keyMaking();
        wLen = message.messageLengthCoded(secretKey);
    }

    private void keyMaking() throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();

        short keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        secretKey = keyGenerator.generateKey();
    }


    public Packet(byte[] encodedPacket) throws Exception
    {
        ByteBuffer byteBuffer = ByteBuffer.wrap(encodedPacket);


        message = new Message();
        message.setCType(byteBuffer.getInt());

        byte[] messageText = new byte[wLen];
        byteBuffer.get(messageText);
        message.setMessage(new String(messageText));
        int secondPartLength = message.messageLength();
        byte[] secondPart = ByteBuffer.allocate(secondPartLength)
                .put(message.messagePackaging()).array();
        short checkVariable = (short) CRC.main(secondPart);


        wCrc16_2 = byteBuffer.getShort();
        if(wCrc16_2 != checkVariable)
            throw new Exception("Second CRC is not right! Your message was damaged.");

        int keyEncodedLength = byteBuffer.getInt();
        byte[] keyEncoded = new byte[keyEncodedLength];
        byteBuffer.get(keyEncoded);
        secretKey = new SecretKeySpec(keyEncoded,0, keyEncodedLength, "AES");
        message.decode(secretKey);
    }
    public byte[] packetPackaging()
    {

        int secondPartLength = message.messageLength();
        byte[] secondPart = ByteBuffer.allocate(secondPartLength)
                            .put(message.messagePackaging()).array();

        wCrc16_2 = (short) CRC.main(secondPart);

        byte[] keyEncoded = secretKey.getEncoded();

        int fullLength =  secondPartLength + Short.BYTES + keyEncoded.length + Integer.BYTES;

        return ByteBuffer.allocate(fullLength)
                        .put(secondPart)
                        .putShort(wCrc16_2)
                        .putInt(keyEncoded.length)
                        .put(keyEncoded).array();

    }
}
