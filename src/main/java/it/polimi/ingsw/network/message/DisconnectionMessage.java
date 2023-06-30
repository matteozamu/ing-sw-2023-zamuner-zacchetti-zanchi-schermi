package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class to inform all the clients that a player disconnected
 */
public class DisconnectionMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8073712986535222650L;

    private final String username;

    /**
     * Constructs a disconnection message
     *
     * @param username username of the player
     */
    public DisconnectionMessage(String username) {
        super("ServerUser", null, MessageContent.DISCONNECTION);
        this.username = username;
    }

    /**
     * Returns the username of the player
     *
     * @return username of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "DisconnectionMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() + ", " +
                ", username='" + username + '\'' +
                '}';
    }
}
