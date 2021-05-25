package org.example;

import lombok.Data;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
    static SecretKey secretKey;

    public Packet(byte bSrc, long bPktId, Message message) throws Exception
    {
        this.bSrc = bSrc;
        this.message = message;
        this.bPktId = bPktId;
        keyMaking();
        wLen = message.messageLengthCoded(secretKey);
    }

    private SecretKey keyMaking() throws Exception
    {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom();

        short keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);

        secretKey = keyGenerator.generateKey();
        return secretKey;
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
        message.decode(secretKey);

        wCrc16_2 = byteBuffer.getShort();
        if(wCrc16_2 != checkVariable)
            throw new Exception("Second CRC is not right! Your message was damaged.");

    }
    public byte[] packetPackaging()
    {

        int firstPartLength = Byte.BYTES * 2 + Long.BYTES + Integer.BYTES;
        byte[] firstPart = ByteBuffer.allocate(firstPartLength)
                            .put(bMagic)
                            .put(bSrc)
                            .putLong(bPktId)
                            .putInt(wLen).array();
        //System.out.println(firstPart.toString());
        wCrc16_1 = (short) CRC.main(firstPart);
        //System.out.println(wCrc16_1);

        int secondPartLength = message.messageLength();
        byte[] secondPart = ByteBuffer.allocate(secondPartLength)
                            .put(message.messagePackaging()).array();

        wCrc16_2 = (short) CRC.main(secondPart);

        int fullLength = firstPartLength + secondPartLength + Short.BYTES * 2;

        return ByteBuffer.allocate(fullLength)
                        .put(firstPart)
                        .putShort(wCrc16_1)
                        .put(secondPart)
                        .putShort(wCrc16_2).array();

    }
    /*public static void main(String args[]) throws Exception
    {
        Message message = new Message(1,1, "test");
        byte test1 = 1;
        long test2 = 2;
        Packet packet = new Packet(test1,test2, message);
        System.out.println("first: " + packet.message.bUserId + " "  + packet.message.cType + " " + packet.message);
        //System.out.println();
        byte[] packetPackaged = packet.packetPackaging();
        System.out.println(packetPackaged.toString());
        Packet packet1 = new Packet(packetPackaged);
        System.out.println(packet1.message.message);
        System.out.println("second: " + packet1.bMagic + " "  + packet1.bSrc + " " + packet1.bPktId + " " + packet1.wLen);


    }*/

}
