package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message used to ask for a reconnection to the server
 */
public class ReconnectionRequest extends Message {
    @Serial
    private static final long serialVersionUID = -8073712912335222650L;
    private final String message;

    /**
     * Constructs a message
     *
     * @param message message to send to the client
     * @param token   token of the sender
     */
    public ReconnectionRequest(String message, String token) {
        super("ServerUser", token, MessageContent.RECONNECTION_REQUEST);
        this.message = message;
    }

    /**
     * Returns the message to send to the client
     *
     * @return message to send to the client
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ReconnectionRequest{" +
                "message='" + message + '\'' +
                '}';
    }
}
