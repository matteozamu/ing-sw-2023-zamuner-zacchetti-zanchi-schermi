package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message class used to join or leave the lobby
 */
public class LobbyMessage extends Message {
    private static final long serialVersionUID = -8420070827032848314L;

    private final boolean disconnection;

    public LobbyMessage(String username, String token, boolean disconnection) {
        super(username, token, MessageContent.ADD_PLAYER);
        this.disconnection = disconnection;
    }

    public boolean isDisconnection() {
        return this.disconnection;
    }

    // a Lobby message is equal to an other (in our case) if its the same message or if it has the same user sender name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyMessage that = (LobbyMessage) o;
        return getSenderUsername().equals(that.getSenderUsername());
    }

    @Override
    public String toString() {
        return "LobbyMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                ", disconnection=" + disconnection +
                '}';
    }
}
