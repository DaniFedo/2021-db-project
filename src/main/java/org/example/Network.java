package org.example;

public interface Network {
    byte[] generate() throws Exception;
    void sendMessage(Packet packet);

    //for future
    //Packet receive();
}
