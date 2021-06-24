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
               // System.out.println("Length of the sent package: " + packet.length);
                System.out.println("Client with ID " + clientSocket.getLocalPort() + " send " + Arrays.toString(packet));

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        public void receive(){

            try {
                byte[] maxPacketBuffer = new byte[Message.maxLength];
                in.read(maxPacketBuffer);

                System.out.println("Client with ID " + clientSocket.getLocalPort() + " received a " + Arrays.toString(maxPacketBuffer));
                if (maxPacketBuffer[0] != 49) {
                    String result = "";
                    boolean check = false;

                    if ((char) maxPacketBuffer[0] == '.') check = true;

                    if (check) {
                        for (int i = 1; i < maxPacketBuffer.length; i++) {
                            result += (char) (maxPacketBuffer[i]);
                        }
                    } else {
                        for (int i = 0; i < maxPacketBuffer.length; i++) {
                            result += (char) (maxPacketBuffer[i]);
                        }
                    }

                    //System.out.println(result + " " + check);
                    while (result.getBytes(StandardCharsets.UTF_8)[amount] != 0) {
                        if (!check) {
                            String[] test = MessageDecryptor.decryptingForClient(result, 6);

                                //System.out.print(test[i] + " ");
                            Model model = new Model(test[0], test[1], test[2], Double.parseDouble(test[4]),
                                    test[5], Double.parseDouble(test[3]));
                            InterfaceController.outputData.add(model);
                        } else {
                            String[] test = MessageDecryptor.decryptingForClient(result, 3);
                            //System.out.println(test[0] == "");


                            try {
                                Model model = new Model(test[0], Double.parseDouble(test[1]), Double.parseDouble(test[2]));
                                InterfaceController.outputData.add(model);
                            }
                            catch(Exception e){

                            }
                        }
                    }
                }


                    System.out.println("-------------------");
                }
            catch(Exception e){
                    e.printStackTrace();
                }
                amount = 0;

        }

        public void stopConnection() throws IOException {
            in.close();
            out.close();
            clientSocket.close();
        }

}
