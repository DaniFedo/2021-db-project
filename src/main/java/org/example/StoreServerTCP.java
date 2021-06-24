package org.example;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

   }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler extends Thread{

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
                    System.out.println(maxPacketBuffer.length + " EGASNDad");

                    byte[] check = maxPacketBuffer.clone();
                    in.read(maxPacketBuffer);

                    if (!Arrays.toString(check).equals(Arrays.toString(maxPacketBuffer))) {


                        System.out.println("Length of the received package: " + maxPacketBuffer.length);
                        Message message = new Message(maxPacketBuffer);
                        System.out.println("Server received a packet: " + Arrays.toString(maxPacketBuffer));
                        //-------------------------------------------
                        //CommandAnalyzator.analyze(message);
                        //-------------------------------------------
                        /*String[] stringArray = new String[5];
                        byte[] messageInput = message.message.getBytes(StandardCharsets.UTF_8);
                        int i = 0;
                        int number = 0;
                        String checkString = "";
                        while(i!=messageInput.length)
                        {

                            if((char)messageInput[i]!=',' && (char)messageInput[i] != '"') checkString += (char)messageInput[i];
                            else if ((char)messageInput[i] != '"'){
                                stringArray[number] = checkString;
                                checkString = "";
                                number++;
                            }
                            if(i == messageInput.length - 1) stringArray[number] = checkString;
                            i++;
                        }
                        for(int n = 0; n < 5; n++)
                        {
                            System.out.println(stringArray[n]);
                        }*/

                        /*Database.connect();
                        Table.addProduct(stringArray[0], stringArray[1], stringArray[2],
                                Integer.parseInt(stringArray[3]), stringArray[4]);
                        Database.close();*/
                        
                        //-------------------------------------------
                        //System.out.println(message);

                        String kaskda = " sda";
                        String[] result = CommandAnalyzator.analyze(message);
                            String output = "";
                            try {
                                for (int i = 0; i < result.length; i++) {
                                    if (result[i] != null)
                                        output += result[i];
                                }
                                out.write(output.getBytes(StandardCharsets.UTF_8));
                            }
                            catch(Exception e)
                            {
                                out.write(null);
                            }

                        //out.write(message.messagePackaging());

                    } else break;
                }

            out.flush();
            in.close();
            clientSocket.close();
        }
    }
}
