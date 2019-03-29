import ClientSoft.ClientSenderReceiver;
import AlicePack.*;
import Messages.ClientMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Main {

    public final static int DEFAULT_PORT = 2007;
    public static void main(String[] arg) throws IOException {
        SocketAddress remote = new InetSocketAddress("localhost", DEFAULT_PORT);
        DatagramChannel channel = DatagramChannel.open( );
        //channel.connect(remote);
        //channel.socket().bind(remote);
//        String message = "Привет";
        ByteBuffer buffer;
//        System.out.println(channel.send(buffer, remote));
           ByteArrayOutputStream bytte = new ByteArrayOutputStream();
        try (ObjectOutputStream sendstream = new ObjectOutputStream(bytte)) {
            sendstream.writeObject(new ClientMessage<Alice>("Привет", new Alice()));
            sendstream.flush();
            buffer = ByteBuffer.wrap(bytte.toByteArray());
            System.out.println(channel.send(buffer, remote));
            buffer.clear();
            System.out.println("Отправлено");
        }
        SocketAddress adress = channel.receive(buffer);
        System.out.println(adress);
        try (ObjectInputStream receivedstream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()))) {
            ClientMessage<Alice> message = (ClientMessage<Alice>) receivedstream.readObject();
            System.out.println(message.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

