package Messages;

import AlicePack.*;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientMessage <T> implements Serializable {
    private String message;
    private T argument;
    private CopyOnWriteArrayList<Alice> collection;

    public ClientMessage (String message, T argument) {
        this.message = message;
        this.argument = argument;
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


    public T getArgument() {
        return argument;
    }

}
