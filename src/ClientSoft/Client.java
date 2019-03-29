package ClientSoft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;
import AlicePack.*;

public class Client {

    public static void main(String[] args) {
//        String string;
//        Scanner scanner = new Scanner(System.in);
//        InetAddress address = null;
//        DatagramSocket socket = null;
//        byte [] buffer = new byte [10000];
//        byte [] receivebuffer = new byte [100000];
//        int port = 2000;
//        try {
//            socket = new DatagramSocket();
//            address = InetAddress.getByName("localhost");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        try {
//        while (true){
//            System.out.println("\nClient: ");
//            string = scanner.nextLine();
//            buffer = string.getBytes();
 //               DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
//                socket.send(packet);
//            if (string.equalsIgnoreCase("exit")){
//                System.out.println("End connection");
    //                break;
//            }
//                DatagramPacket receivepacket = new DatagramPacket(receivebuffer, receivebuffer.length);
//                socket.receive(receivepacket);
//                String receivestring = new String(receivepacket.getData());
//                System.out.println("\nServer: " + receivestring);
//
//            }
//        }catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            ConsoleReader consoleReader = new ConsoleReader(2000);
      //      consoleReader.work();
            consoleReader.testwork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
