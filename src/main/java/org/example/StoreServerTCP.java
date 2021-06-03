package org.example;

import lombok.SneakyThrows;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Arrays;

public class StoreServerTCP{

    private static ServerSocket serverSocket;
    public static int amountOfThreads;

   /* public void start(int port) throws IOException, InterruptedException {
        //System.out.println("Server works");
        serverSocket = new ServerSocket(port);
        LocalTime time = LocalTime.now();
        System.out.println(time);
        time = time.plusMinutes(1);
        System.out.println(time);

        while(true){
            new EchoClientHandler(serverSocket.accept()).start();
            amountOfThreads++;
            if(amountOfThreads == 3)
                if(reconnect()) continue;
                break;
        }
        System.out.println("time is over");
    }*/
   public void start(int port) throws IOException, InterruptedException {

       serverSocket = new ServerSocket(port);

       while(true) {
           new EchoClientHandler(serverSocket.accept()).start();
           amountOfThreads++;
           if(amountOfThreads == 3)
               break;
       }
       System.out.println("active threads: " + Thread.activeCount());

   }
    public boolean reconnect()
    {
        LocalTime time = LocalTime.now();
        //System.out.println(time);
        time = time.plusMinutes(1);
        //System.out.println(time);
        while(LocalTime.now().getMinute() < time.getMinute())
            if(amountOfThreads < 3)
                return true;

        return false;
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread{

        private final Socket clientSocket;
        private OutputStream out;
        private InputStream in;


        public EchoClientHandler(Socket socket) throws IOException, InterruptedException {
            this.clientSocket = socket;
            this.join();
        }

        @SneakyThrows
        public void run() {
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

            byte[] maxPacketBuffer = new byte[Packet.maxLength];

                while (!clientSocket.isClosed()) {
                    Process process = new Process();

                    byte[] check = maxPacketBuffer.clone();
                    in.read(maxPacketBuffer);

                    if (!Arrays.toString(check).equals(Arrays.toString(maxPacketBuffer))) {
                        System.out.println("Received(at server): " + Arrays.toString(maxPacketBuffer));

                        Packet packet1 = new Packet(maxPacketBuffer);
                        System.out.println("Message received: " + packet1.message.message);

                        byte[] sending = process.process(maxPacketBuffer);
                        System.out.println("Sent from server: " + Arrays.toString(sending));
                        System.out.println("---------------------------");

                        out.write(sending);

                    } else break;
                }

            out.flush();
            in.close();
            clientSocket.close();
        }
    }
}
