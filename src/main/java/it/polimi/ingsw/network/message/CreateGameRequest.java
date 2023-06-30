package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class to request to create a new game
 */
public class CreateGameRequest extends Message {
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;

    /**
     * Constructs a create game request message
     *
     * @param username username of the player
     * @param token    token of the player
     */
    public CreateGameRequest(String username, String token) {
        super(username, token, MessageContent.CREATE_GAME);
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "CreateGameRequest{" +
                '}';
    }
}