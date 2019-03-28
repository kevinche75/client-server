package ClientSoft;

import AlicePack.Alice;
import Messages.ClientMessage;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;

public class Receiver extends Thread {

    private DatagramChannel channel;
    private Boolean workable;
    private DatagramSocket socket;

    public Receiver(DatagramChannel channel, Boolean workable) {
        this.channel = channel;
        this.workable = workable;
        socket = channel.socket();
    }

    @Override
    public void run() {
        while (true) {
            byte[] buf = new byte[100000];
            String string;
            ObjectOutputStream sendedstream;
            ByteArrayOutputStream sendedstreambuffer;
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(packet);
                    new Executor(packet, workable).start();
                } catch (IOException e) {
                    System.out.println("===\nНепредвиденная ошибка получения пакета");
                }
            }
        }
    }
}
