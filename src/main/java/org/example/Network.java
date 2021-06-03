package org.example;

public interface Network {
    byte[] generate() throws Exception;
    void sendMessage(byte[] packet);

    //for future
    //Packet receive();
}
