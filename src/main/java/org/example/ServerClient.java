package org.example;


public class ServerClient {

    public static void main(String[] args) throws Exception {
        StoreServerTCP serverTCP = new StoreServerTCP();

        serverTCP.start(2305);

        serverTCP.stop();

    }
}
