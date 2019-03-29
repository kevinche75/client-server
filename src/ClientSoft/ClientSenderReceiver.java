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

import javax.xml.crypto.Data;

public class ClientSenderReceiver <T> extends Thread{

    private DatagramChannel channel;
    private  ByteArrayOutputStream sendedstreambuffer;
    private T argument;
    private String command;
    private SocketAddress address;

    public ClientSenderReceiver (DatagramChannel channel, SocketAddress adress,String command, T argument) {
        this.command = command;
        this.argument = argument;
        this.channel = channel;
        this.address = adress;
    }

    @Override
    public void run() {
        ClientMessage message = new ClientMessage(command, argument);
        sendedstreambuffer = new ByteArrayOutputStream();
        try (ObjectOutputStream sendedstream = new ObjectOutputStream(sendedstreambuffer)){
            sendedstream.writeObject(message);
            sendedstream.flush();
            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
            channel.send(buffer, address);
            buffer.clear();
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

//    public boolean checkConnection()throws IOException, ClassNotFoundException{
//        try {
//            ClientMessage message = new ClientMessage<>("test", null);
////            sendedstream = new ObjectOutputStream(sendedstreambuffer);
////            sendedstream.writeObject(message);
////            sendedstream.flush();
////            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
////            buffer.flip();
////            channel.send(buffer, address);
//            sendedstreambuffer = new ByteArrayOutputStream();
//            sendedstream = new ObjectOutputStream(sendedstreambuffer);
//            sendedstream.writeObject(message);
//            sendedstream.flush();
//            byte[] sendbuffer = sendedstreambuffer.toByteArray();
//            DatagramPacket packet = new DatagramPacket(sendbuffer, sendbuffer.length, address);
//            socket.send(packet);
//            byte [] receivedbuffer = new byte[65507];
//            DatagramPacket receivepacket = new DatagramPacket(receivedbuffer, receivedbuffer.length);
//            socket.receive(packet);
////           // receivedsteam = new ObjectInputStream(new ByteArrayInputStream(receivepacket.getData()));
////            buffer.clear();
////            channel.receive(buffer);
////            buffer.flip();
////            receivedsteam = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
//            System.out.println((String)receivedsteam.readObject());
//            return true;
//        }catch (SocketTimeoutException e){
//            System.out.println("===\nСоединение не установлено");
//            return false;
//        }
//    }
}
