package Messages;

import AlicePack.*;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientMessage implements Serializable {
    private String message;
    private Alice argument;
    private CopyOnWriteArrayList<Alice> collection;

    public ClientMessage(String message, Alice argument) {
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

    private void setMessage(String message) {
        this.message = message;
    }

    private Alice getArgument() {
        return argument;
    }

    public void setArgument(Alice argument) {
        this.argument = argument;
    }
}
