package Messages;

import AlicePack.*;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientMessage <T> implements Serializable {
    private String message;
    private T argument;
    private UUID id;
    private CopyOnWriteArrayList<Alice> collection;

    public ClientMessage (String message, T argument) {
        this.message = message;
        this.argument = argument;
        id = UUID.randomUUID();
    }

    public ClientMessage(String message, T argument, UUID id){
        this.message = message;
        this.argument = argument;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public ClientMessage(CopyOnWriteArrayList<Alice> collection, String message){
        this.message = message;
        this.collection = collection;
    }

    public CopyOnWriteArrayList<Alice> getCollection() {
        return collection;
    }

    public String getMessage() {
        return message;
    }


    private T getArgument() {
        return argument;
    }

}
