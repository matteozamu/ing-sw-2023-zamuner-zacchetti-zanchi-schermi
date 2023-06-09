package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class ReconnectionRequest extends Message {
    private static final long serialVersionUID = -8073712912335222650L;
    private final String message;

    public ReconnectionRequest(String message, String token) {
        super("ServerUser", token, MessageContent.RECONNECTION_REQUEST);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ReconnectionRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
