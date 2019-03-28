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

public class ClientSenderReceiver {

    private int port;
    private DatagramChannel channel;
    private DatagramSocket socket;
    private InetSocketAddress adress;
    private int timeout = 10000;
    private static final String hostname = "localhost";
    private ObjectOutputStream sendedstream;
    private  ByteArrayOutputStream sendedstreambuffer;
    private InetSocketAddress address;
    private ObjectInputStream receivedsteam;
    private UUID id;

    public ClientSenderReceiver(int port) throws IOException {
        this.port = port;
        channel = DatagramChannel.open();
        address = new InetSocketAddress(hostname, port);
        //channel.configureBlocking(false);
        socket = channel.socket();
        socket.setSoTimeout(timeout);
    }

    public ServerMessage doRequest(String command, Alice argument)throws IOException, ClassNotFoundException{
        try {
            ClientMessage message = new ClientMessage(command, argument);
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

    public boolean checkConnection()throws IOException, ClassNotFoundException{
        try {
            ClientMessage<Alice> message = new ClientMessage<>("test", null);
            sendedstreambuffer = new ByteArrayOutputStream();
            sendedstream = new ObjectOutputStream(sendedstreambuffer);
            sendedstream.writeObject(message);
            sendedstream.flush();
            ByteBuffer buffer = ByteBuffer.wrap(sendedstreambuffer.toByteArray());
            buffer.flip();
            channel.send(buffer,address);
            buffer.clear();
            channel.receive(buffer);
            buffer.flip();
            receivedsteam = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
            ServerMessage serverMessage = (ServerMessage) receivedsteam.readObject();
            if(serverMessage.getSpecialmessage().equals(SpecialMessage.CONNECTION)) return true;
                else return false;
        }catch (SocketTimeoutException e){
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
