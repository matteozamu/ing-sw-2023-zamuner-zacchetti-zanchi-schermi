package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class used to join or leave the lobby
 */
public class LobbyMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8420070827032848314L;

    private final boolean disconnection;

    /**
     * Constructs a lobby message
     *
     * @param username      username of the player
     * @param token         token of the player
     * @param disconnection true if the player is leaving the lobby, false otherwise
     */
    public LobbyMessage(String username, String token, boolean disconnection) {
        super(username, token, MessageContent.ADD_PLAYER);
        this.disconnection = disconnection;
    }

    /**
     * Returns true if the player is leaving the lobby, false otherwise
     *
     * @return true if the player is leaving the lobby, false otherwise
     */
    public boolean isDisconnection() {
        return this.disconnection;
    }

    // A Lobby message is equal to another (in our case) if it is the same message or if it has the same user sender name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyMessage that = (LobbyMessage) o;
        return getSenderUsername().equals(that.getSenderUsername());
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "LobbyMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                ", disconnection=" + disconnection +
                '}';
    }
}
