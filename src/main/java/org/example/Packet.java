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
    public static byte bMagic = 0x13;
    byte bSrc;
    long bPktId;
    int wLen;
    short wCrc16_1;
    Message message;
    short wCrc16_2;
    public SecretKey secretKey;
    public static int maxLength = Byte.BYTES * 2 + Long.BYTES + Integer.BYTES * 3 + Short.BYTES * 2 + 100;

    public Packet(byte bSrc, long bPktId, Message message) throws Exception
    {
        this.bSrc = bSrc;
        this.message = message;
        this.bPktId = bPktId;
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

        if(bMagic != byteBuffer.get()) throw new Exception("Magic number is incorrect!");

        bSrc = byteBuffer.get();
        bPktId = byteBuffer.getLong();
        wLen = byteBuffer.getInt();

        wCrc16_1 = byteBuffer.getShort();
        int firstPartLength = Byte.BYTES * 2 + Long.BYTES + Integer.BYTES;
        byte[] firstPart = ByteBuffer.allocate(firstPartLength)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId)
                .putInt(wLen).array();

        if(wCrc16_1 != (short)CRC.main(firstPart))
            throw new Exception("First CRC is not right! Your packet was damaged.");

        message = new Message();
        message.setCType(byteBuffer.getInt());
        message.setBUserId(byteBuffer.getInt());

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

        int firstPartLength = Byte.BYTES * 2 + Long.BYTES + Integer.BYTES;
        byte[] firstPart = ByteBuffer.allocate(firstPartLength)
                            .put(bMagic)
                            .put(bSrc)
                            .putLong(bPktId)
                            .putInt(wLen).array();
        wCrc16_1 = (short) CRC.main(firstPart);

        int secondPartLength = message.messageLength();
        byte[] secondPart = ByteBuffer.allocate(secondPartLength)
                            .put(message.messagePackaging()).array();

        wCrc16_2 = (short) CRC.main(secondPart);

        byte[] keyEncoded = secretKey.getEncoded();

        int fullLength = firstPartLength + secondPartLength + Short.BYTES * 2 + keyEncoded.length + Integer.BYTES;

        return ByteBuffer.allocate(fullLength)
                        .put(firstPart)
                        .putShort(wCrc16_1)
                        .put(secondPart)
                        .putShort(wCrc16_2)
                        .putInt(keyEncoded.length)
                        .put(keyEncoded).array();

    }
}
