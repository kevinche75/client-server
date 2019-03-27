package ServerSoft;

import AlicePack.Alice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) {
//        try {
//            Scanner scanner = new Scanner(System.in);
//            DatagramSocket data = new DatagramSocket(2000);
//            InetAddress address = InetAddress.getLocalHost();
//            byte[] buf = new byte[100000];
//            byte[] sendbuf = new byte[100000];
//            String string;
//            while (true) {
//                DatagramPacket packet = new DatagramPacket(buf, buf.length);
//                data.receive(packet);
//                String result = new String(packet.getData());
//                System.out.println("\nClient: "+result);
//                System.out.println("\nServer :");
//                string = scanner.nextLine();
//                sendbuf = string.getBytes();
//                DatagramPacket sendpacket = new DatagramPacket(sendbuf, sendbuf.length, packet.getAddress(), packet.getPort());
//                data.send(sendpacket);
//                if (string.equalsIgnoreCase("exit")){
//                    System.out.println("End connection");
//                    break;
//                }
//            }
//            data.close();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
try{
    ServerReader.justReadFile();
} catch (FileNotFoundException e) {
    System.out.println(e.getMessage());
    System.out.println("===\nДальнейшая работа невозможна");
}
    }
}
