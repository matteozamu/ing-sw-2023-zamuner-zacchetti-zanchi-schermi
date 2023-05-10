package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class GameStateRequest extends Message {
    private static final long serialVersionUID = -8420070827032848314L;

    public GameStateRequest(String username, String token) {
        super(username, token, MessageContent.GAME_STATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateRequest that = (GameStateRequest) o;
        return getSenderUsername().equals(that.getSenderUsername());
    }

    @Override
    public String toString() {
        return "GameStateRequest{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                '}';
    }
}
