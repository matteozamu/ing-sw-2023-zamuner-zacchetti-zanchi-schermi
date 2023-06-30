package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class of connection request
 */
public class ConnectionRequest extends Message {
    @Serial
    private static final long serialVersionUID = 5444683484323330868L;

    /**
     * Constructs a connection request message
     *
     * @param username username of the player
     */
    public ConnectionRequest(String username) {
        super(username, null, MessageContent.CONNECTION);
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ConnectionRequest{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                "}";
    }
}