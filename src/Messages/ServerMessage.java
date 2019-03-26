package Messages;

import java.io.Serializable;

public class ServerMessage implements Serializable {
    private SpecialMessage specialmessage;
    private String message;

    public SpecialMessage getSpecialmessage() {
        return specialmessage;
    }

    public String getMessage() {
        return message;
    }

    public ServerMessage(SpecialMessage specialmessage, String message){
        this.specialmessage = specialmessage;
        this.message = message;
    }
}
