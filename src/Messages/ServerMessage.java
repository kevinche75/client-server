package Messages;

import java.io.Serializable;

public class ServerMessage implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public ServerMessage(String message){
        this.message = message;
    }
}
