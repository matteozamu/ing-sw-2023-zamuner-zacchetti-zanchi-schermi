package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class ReconnectionMessage extends Message {
    private static final long serialVersionUID = -8073712912335222650L;
    private final String message;

    public ReconnectionMessage(String message) {
        super("ServerUser", null, MessageContent.RECONNECTION);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ReconnectionMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
