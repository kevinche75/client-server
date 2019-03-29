package ServerSoft;

import AlicePack.Alice;
import Messages.ClientMessage;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {



    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            DatagramSocket data = new DatagramSocket(2000);
            InetSocketAddress address = new InetSocketAddress("localhost", 2000);
            data.connect(address);
            byte[] buf = new byte[100000];
            byte[] sendbuf = new byte[100000];
            String string;
            ObjectOutputStream sendedstream;
            ByteArrayOutputStream sendedstreambuffer;
//            while (true) {
//                DatagramPacket packet = new DatagramPacket(buf, buf.length);
//                data.receive(packet);
//                try(ObjectInputStream receivedstream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()))){
//                    ClientMessage message = (ClientMessage) receivedstream.readObject();
//                    System.out.println((Alice)message.getArgument());
//                    System.out.println(message.getMessage());
//                }
                string = "Здарова бичара";
                sendbuf = string.getBytes();
                DatagramPacket packet = new DatagramPacket(sendbuf, sendbuf.length, address);
                data.send(packet);
            //}
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }

//        try {
//            CollectionPlace collectionPlace = new CollectionPlace(ServerReaderSaver.justReadFile());
//            ServeOneClient serveOneClient = new ServeOneClient(collectionPlace);
////            DatagramSocket data = new DatagramSocket(2000);
////            InetAddress address = InetAddress.getLocalHost();
//            System.out.println(collectionPlace.info());
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//        catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
    }
}
