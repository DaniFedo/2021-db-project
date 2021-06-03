package org.example;



import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class StoreServerTCP{

    private static ServerSocket serverSocket;
    private int amountOfThreads;

    public void start(int port) throws IOException, InterruptedException {
        //System.out.println("Server works");
        serverSocket = new ServerSocket(port);
        System.out.println("active thread before: " + Thread.activeCount());
        while(true) {
            new EchoClientHandler(serverSocket.accept()).start();
            System.out.println(++amountOfThreads);
            if(amountOfThreads == 2)
                break;
        }
        //while(Thread.activeCount() != 2)
            System.out.println("active threads: " + Thread.activeCount());

    }

    public void stop() throws IOException {
        serverSocket.close();
        System.out.println("server is closed? " + serverSocket.isClosed());

        System.out.println("stop threads are: " + amountOfThreads);
    }

    private static class EchoClientHandler extends Thread{

        private Socket clientSocket;
        private OutputStream out;
        byte[] poisonPill =
                {19, 4, 6, 24, 124, -86, -47, -121, -48, 74, 0, 0, 0, 24, -128, -97, 0, 0, 0, 10, 0, 0, 0, 3, 83, 73, 112, 68, 49, 88, 75, 48, 51, 98, 115, 106, 85, 113, 112, 97, 121, 73, 103, 110, 111, 65, 61, 61, -84, 68, 0, 0, 0, 32, -15, -121, -93, 88, 104, -46, 30, 54, 28, -32, 122, -18, 50, -2, 55, -19, 78, 80, 17, -18, 114, 59, 66, 115, 5, -98, 124, 105, 48, -34, 125, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                ;
        private InputStream in;

        public EchoClientHandler(Socket socket) throws IOException, InterruptedException {
            //System.out.println("EchoClientHandler created ");
            this.clientSocket = socket;
            this.join();
        }

        @SneakyThrows
        public void run() {
            //System.out.println("It works here");
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();


            byte[] maxPacketBuffer = new byte[Packet.maxLength];


            while (!clientSocket.isClosed()) {


                    //System.out.println(clientSocket.isClosed());
                    byte[] check = maxPacketBuffer.clone();

                    in.read(maxPacketBuffer);

                    if(!Arrays.toString(check).equals(Arrays.toString(maxPacketBuffer))){
                    System.out.println(Thread.currentThread().getName() + "Received(at server): " + Arrays.toString(maxPacketBuffer));

                    out.write(maxPacketBuffer);}
                    else break;

                /*} catch (SocketException e) {
                    System.out.println(Thread.currentThread().getName() + " is getting interrupted");
                    Thread.currentThread().interrupt();
                    out.flush();
                    in.close();
                    clientSocket.close();
                }*/
            }
            System.out.println(Thread.currentThread().getName() + " is getting interrupted");

            Thread.currentThread().interrupt();
            out.flush();
            in.close();
            clientSocket.close();





        }
    }


}
