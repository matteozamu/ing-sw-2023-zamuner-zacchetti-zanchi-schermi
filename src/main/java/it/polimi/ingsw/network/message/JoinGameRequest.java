package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;
import java.util.UUID;

/**
 * Message used to request to join an existing game
 */
public class JoinGameRequest extends Message {
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;
    private UUID gameUUID;

    /**
     * Constructs a join game request message
     *
     * @param username username of the player
     * @param token    token of the player
     * @param gameUUID UUID of the game
     */
    public JoinGameRequest(String username, String token, UUID gameUUID) {
        super(username, token, MessageContent.JOIN_GAME);
        this.gameUUID = gameUUID;
    }

    /**
     * Returns the UUID of the game
     *
     * @return UUID of the game
     */
    public UUID getGameUUID() {
        return gameUUID;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "JoinGameRequest{" +
                "gameUUID=" + gameUUID +
                '}';
    }
}
