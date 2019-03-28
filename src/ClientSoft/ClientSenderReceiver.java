package ClientSoft;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import AlicePack.*;
import Messages.ClientMessage;
import Messages.ServerMessage;
import Messages.SpecialMessage;

public class ClientSenderReceiver <T> extends Thread{

    private DatagramChannel channel;
    private DatagramSocket socket;
    private InetSocketAddress adress;
    private int timeout = 60000;
    private static final String hostname = "localhost";
    private ObjectOutputStream sendedstream;
    private  ByteArrayOutputStream sendedstreambuffer;
    private InetAddress host;
    private InetSocketAddress address;
    private ObjectInputStream receivedsteam;
    private T argument;
    private String command;

    public ClientSenderReceiver(int port) throws IOException {
        channel = DatagramChannel.open();
        address = new InetSocketAddress(hostname, port);
        //channel.configureBlocking(false);
        socket = channel.socket();
        socket.setSoTimeout(timeout);
    }

    public ClientSenderReceiver (int port,String command, T argument) throws IOException {
        host = InetAddress.getLocalHost();
        address = new InetSocketAddress(hostname, port);
        channel = DatagramChannel.open();
       // channel.bind(null);
        socket = channel.socket();
       // channel.connect(adress);
        this.command = command;
        this.argument = argument;
    }

    public DatagramChannel getChannel(){
        return channel;
    }

    @Override
    public void run() {
        ClientMessage message = new ClientMessage(command, argument);
        sendedstreambuffer = new ByteArrayOutputStream();
        try {
            sendedstreambuffer = new ByteArrayOutputStream();
            sendedstream = new ObjectOutputStream(sendedstreambuffer);
            sendedstream.writeObject(message);
            sendedstream.flush();
            byte[] sendbuffer = sendedstreambuffer.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendbuffer, sendbuffer.length, address);
            socket.send(packet);
//            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
//            buffer.flip();
//            channel.send(buffer, address);
            System.out.println("===\nСообщение послано серверу");
        } catch (IOException e) {
            System.out.println("===\nНепредвиденная ошибка");
        }
    }

//    public <T> void doRequest(String command, T argument) {
//        //   try {
//        new Thread(() -> {
//        });
////            ClientMessage message = new ClientMessage(command, argument);
////            sendedstreambuffer = new ByteArrayOutputStream();
////            sendedstream = new ObjectOutputStream(sendedstreambuffer);
////            sendedstream.writeObject(message);
////            sendedstream.flush();
////            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
////            buffer.flip();
////            channel.send(buffer, address);
////            buffer.clear();
////            channel.receive(buffer);
////            receivedsteam = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
////            return (ServerMessage) receivedsteam.readObject();
////        }catch (PortUnreachableException e){
////            return new ServerMessage(SpecialMessage.TIMEOUT, "===\nСервер не доступен.");
//    //}
//    }

    public boolean checkConnection()throws IOException, ClassNotFoundException{
        try {
            ClientMessage message = new ClientMessage<>("test", null);
            sendedstreambuffer = new ByteArrayOutputStream();
            sendedstream = new ObjectOutputStream(sendedstreambuffer);
            sendedstream.writeObject(message);
            sendedstream.flush();
            byte[] sendbuffer = sendedstreambuffer.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendbuffer, sendbuffer.length, address);
            socket.send(packet);
            byte [] receivedbuffer = new byte[65507];
            DatagramPacket receivepacket = new DatagramPacket(receivedbuffer, receivedbuffer.length);
            socket.receive(packet);
            receivedsteam = new ObjectInputStream(new ByteArrayInputStream(receivepacket.getData()));
            System.out.println((String)receivedsteam.readObject());
            return true;
        }catch (SocketTimeoutException e){
            System.out.println("===\nСоединение не установлено");
            return false;
        }
    }

    public ServerMessage sendCollection (CopyOnWriteArrayList<Alice> collection)throws IOException, ClassNotFoundException{
        try {
            ClientMessage message = new ClientMessage(collection, "send");
            sendedstreambuffer = new ByteArrayOutputStream();
            sendedstream = new ObjectOutputStream(sendedstreambuffer);
            sendedstream.writeObject(message);
            sendedstream.flush();
            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
            buffer.flip();
            channel.send(buffer, address);
            buffer.clear();
            channel.receive(buffer);
            receivedsteam = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            return (ServerMessage) receivedsteam.readObject();
        }catch (PortUnreachableException e){
            return new ServerMessage(SpecialMessage.TIMEOUT, "===\nСервер не доступен.");
        }
    }
}
