package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;
import java.io.Serializable;

/**
 * This abstract class need to differentiate that the server can send to the client or vice versa
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -5411382756213360684L;

    private final String senderUsername;
    private final String token;
    private final MessageContent content;

    /**
     * Constructs a message
     *
     * @param senderUsername username of the sender
     * @param token          token of the sender
     * @param content        content of the message
     */
    Message(String senderUsername, String token, MessageContent content) {
        this.senderUsername = senderUsername;
        this.token = token;
        this.content = content;
    }

    /**
     * Returns the username of the sender
     * @return username of the sender
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Returns the content of the message
     * @return content of the message
     */
    public MessageContent getContent() {
        return content;
    }

    /**
     * Returns the token of the sender
     * @return token of the sender
     */
    public String getToken() {
        return token;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Message{" +
                "senderUsername='" + senderUsername + '\'' +
                ", content=" + content +
                '}';
    }
}