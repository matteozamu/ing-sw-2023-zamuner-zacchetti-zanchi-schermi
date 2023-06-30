package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Empty message used as a ping message
 */
public class PingMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8092508198825773159L;

    /**
     * Constructs a message
     */
    public PingMessage() {
        super("ServerUser", null, MessageContent.PING);
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "PingMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                "}";
    }
}
