package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;

import java.io.Serial;

/**
 * Message class of response after a connection request
 */
public class ConnectionResponse extends Message {
    @Serial
    private static final long serialVersionUID = 8971780730242420173L;

    private final String newToken;
    private final String message;
    private final MessageStatus status;

    /**
     * Constructs a connection response message
     *
     * @param message  message of the response
     * @param newToken new token of the player
     * @param status   status of the response
     */
    public ConnectionResponse(String message, String newToken, MessageStatus status) {
        super("ServerUser", null, MessageContent.CONNECTION_RESPONSE);
        this.message = message;
        this.newToken = newToken;
        this.status = status;
    }

    /**
     * Method to get the message of the response
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method to get the new token of the player
     */
    public String getNewToken() {
        return newToken;
    }

    /**
     * Method to get the status of the response
     */
    public MessageStatus getStatus() {
        return status;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ConnectionResponse{" +
                "content=" + getContent() +
                ", newToken='" + newToken + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}