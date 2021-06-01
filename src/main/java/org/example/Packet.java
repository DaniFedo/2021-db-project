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
    /*public static void main(String args[]) throws Exception
    {
        Message message = new Message(1,1, "");
        byte test1 = 1;
        long test2 = 2;
        Packet packet = new Packet(test1,test2, message);
        byte[] packagedPacket = packet.packetPackaging();

        Packet packet2 = new Packet(packagedPacket);
        Message message2 = new Message(6,5, "test2");
        byte test4 = 7;
        long test3 = 5;
        Packet packet3 = new Packet(test4,test3, message2);



        //packet2.message.decode(packet2.secretKey);
        System.out.println(packet2.message.message);


        *//*
        System.out.println("first: " + packet.message.bUserId + " "  + packet.message.cType + " " + packet.message);
        //System.out.println();
        byte[] packetPackaged = packet.packetPackaging();
        System.out.println(packetPackaged.toString());
        Packet packet1 = new Packet(packetPackaged);
        System.out.println(packet1.message.message);
        System.out.println("second: " + packet1.bMagic + " "  + packet1.bSrc + " " + packet1.bPktId + " " + packet1.wLen);
       *//*
        *//*CommandTypeEncoder c = new CommandTypeEncoder(9);
        System.out.println(c.getTypeCommand(9));*//*

        *//*FakeNetwork f = new FakeNetwork();
        Packet packetTest;
        packetTest = f.generate();
        System.out.println(packetTest.message.message + ' ' + packetTest.bSrc + ' ' + packetTest.bPktId);
        packetTest.message.decode(secretKey);
        System.out.println(packetTest.message.message);
        Decryptor decryptor = new Decryptor();*//*

    }*/

}
