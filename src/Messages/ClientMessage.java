package Messages;

import AlicePack.*;
import java.io.Serializable;

public class ClientMessage implements Serializable {
    private String message;
    private Alice argument;

    public ClientMessage(String message, Alice argument) {
        this.message = message;
        this.argument = argument;
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
