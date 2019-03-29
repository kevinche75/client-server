package ClientSoft;

import AlicePack.Alice;
import Messages.ClientMessage;
import Messages.ServerMessage;
import ServerSoft.Server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Receiver extends Thread {

    private DatagramChannel channel;
    private Boolean workable;
    private ByteBuffer buffer;

    public Receiver(DatagramChannel channel, Boolean workable) {
        this.channel = channel;
        this.workable = workable;
        buffer.allocate(8192);
    }

    @Override
    public void run() {
        while (true) {
            try {
                channel.receive(buffer);
            try (ObjectInputStream receivedstream = new ObjectInputStream(new ByteArrayInputStream(buffer.array()))) {
                ServerMessage message = (ServerMessage) receivedstream.readObject();
                if(message.getMessage().equals("DISCONNECTION")){
                    workable = false;
                }
                if(message.getMessage().equals("CONNECTION")){
                    workable = true;
                }
                System.out.println(message.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("===\nНеизвестное сообщение от сервера");
            }
            } catch (IOException e) {
                System.out.println("===\nНепредвиденная ошибка приёма пакетов");
            }
        }
    }
}
