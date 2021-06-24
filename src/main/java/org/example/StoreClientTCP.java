package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

//ServerClient -> TCPTest

public class StoreClientTCP {
        public Socket clientSocket;
        private OutputStream out;
        private InputStream in;
        public static int amount = 0;

        public void startConnection(String ip, int port) throws IOException {
            clientSocket = new Socket(ip, port);
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();

        }

        public void sendPackage(byte[] packet){
            try {

                out.write(packet);
                System.out.println("Length of the sent package: " + packet.length);
                System.out.println("Client with ID " + clientSocket.getLocalPort() + " send " + Arrays.toString(packet));

            }
            catch(Exception e){}
        }
        public void receive(){
            try {
                byte[] maxPacketBuffer = new byte[Message.maxLength];
                in.read(maxPacketBuffer);
                //Message message = new Message(maxPacketBuffer);
                System.out.println("Client with ID " + clientSocket.getLocalPort() + " received a " + Arrays.toString(maxPacketBuffer));
                String result = "";

                for(int i = 0; i < maxPacketBuffer.length; i++)
                {
                    result+= (char)(maxPacketBuffer[i]);
                }
                System.out.println(result);
                while(result.getBytes(StandardCharsets.UTF_8)[amount]!=0){
                    String[] test = MessageDecryptor.decryptingForClient(result, 6);
                    for(int i = 0; i < 6; i++)
                        System.out.print(test[i] + " ");
                    Model model = new Model(test[0], test[1], test[2], Double.parseDouble(test[4]),
                            test[5], Double.parseDouble(test[3]));
                    InterfaceController.outputData.add(model);
                }

                amount = 0;
                System.out.println("-------------------");
            }
            catch(Exception e){

            }
        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }

}
