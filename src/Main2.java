import AlicePack.Alice;
import Messages.ClientMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

public class Main2 {

    public final static int port = 2007;

    public static void main(String[] arg) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[8192];
        DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
        socket.receive(incoming);
        System.out.println("Принято");
        try (ObjectInputStream receivedstream = new ObjectInputStream(new ByteArrayInputStream(buffer))) {
            ClientMessage message = (ClientMessage) receivedstream.readObject();
            System.out.println(message.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bytte = new ByteArrayOutputStream();
        try (ObjectOutputStream sendstream = new ObjectOutputStream(bytte)) {
            sendstream.writeObject(new ClientMessage<Alice>("Пока", new Alice()));
            sendstream.flush();
            DatagramPacket packet = new DatagramPacket(bytte.toByteArray(), bytte.toByteArray().length, incoming.getAddress(), incoming.getPort());
            socket.send(packet);
            System.out.println("Отправлено");
        }
    }
}
