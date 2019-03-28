package ClientSoft;

import Messages.ClientMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class Executor extends Thread {

    private DatagramPacket packet;
    private Boolean workable;

public Executor(DatagramPacket packet, Boolean workable){
    this.packet = packet;
    this.workable = workable;
    }

    @Override
    public void run() {
        try (ObjectInputStream receivedstream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()))) {
            String message = (String) receivedstream.readObject();
            if(message.equals("exit")){
                workable = false;
            }
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("===\nНепредвиденная ошибка чтения файла");
        } catch (ClassNotFoundException e) {
            System.out.println("===\nНеизвестное сообщение от сервера");
        }
    }
}
