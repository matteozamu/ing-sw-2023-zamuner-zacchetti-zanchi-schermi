package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message class to inform all the clients that a player disconnected
 */
public class DisconnectionMessage extends Message {
    private static final long serialVersionUID = -8073712986535222650L;

    private final String username;

    public DisconnectionMessage(String username) {
        super("ServerUser", null, MessageContent.DISCONNECTION);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "DisconnectionMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() + ", " +
                ", username='" + username + '\'' +
                '}';
    }
}
