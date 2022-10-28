package org.example;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class StoreServerTCP {

    private static ServerSocket serverSocket;
    public static int amountOfThreads;

    public void start(int port) throws IOException, InterruptedException {

        serverSocket = new ServerSocket(port);

        while (true) {
            new EchoClientHandler(serverSocket.accept()).start();
            amountOfThreads++;
            if (amountOfThreads == 3)
                break;
        }

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread {

        private final Socket clientSocket;

        public EchoClientHandler(Socket socket) throws InterruptedException {
            this.clientSocket = socket;
            this.join();
        }

        @SneakyThrows
        public void run() {
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();

            byte[] maxPacketBuffer = new byte[Message.maxLength];

            while (!clientSocket.isClosed()) {

                byte[] check = maxPacketBuffer.clone();
                in.read(maxPacketBuffer);

                System.out.println("RECEIVED: " + Arrays.toString(maxPacketBuffer));

                if (!Arrays.toString(check).equals(Arrays.toString(maxPacketBuffer))) {


                    System.out.println("Length of the received package: " + maxPacketBuffer.length);
                    Message message = new Message(maxPacketBuffer);
                    System.out.println("Server received a packet: " + Arrays.toString(maxPacketBuffer));


                    String[] result = CommandAnalyzator.analyze(message);
                    if (result == null) out.write("1".getBytes(StandardCharsets.UTF_8));

                    else {
                        String output = "";

                        try {
                            for (int i = 0; i < result.length; i++) {
                                if (result[i] != null)
                                    output += result[i];
                            }
                            out.write(output.getBytes(StandardCharsets.UTF_8));
                        } catch (Exception e) {
                            e.printStackTrace();
                            out.write(output.getBytes(StandardCharsets.UTF_8));
                        }
                    }


                } else break;

            }

            out.flush();
            in.close();
            clientSocket.close();
        }
    }
}
