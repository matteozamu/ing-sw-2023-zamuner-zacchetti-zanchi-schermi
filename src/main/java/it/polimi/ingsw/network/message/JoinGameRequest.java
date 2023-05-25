package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.util.UUID;

/**
 * Message class for requesting the bot spawn
 */
public class JoinGameRequest extends Message {
    private static final long serialVersionUID = 1272585248979532293L;
    private UUID gameUUID;

    public JoinGameRequest(String username, String token, UUID gameUUID) {
        super(username, token, MessageContent.JOIN_GAME);
        this.gameUUID = gameUUID;
    }

    public UUID getGameUUID() {
        return gameUUID;
    }

    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "gameUUID=" + gameUUID +
                '}';
    }
}
