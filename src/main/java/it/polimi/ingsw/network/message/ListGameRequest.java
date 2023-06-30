package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message used to request the list of available games
 */
public class ListGameRequest extends Message {
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;

    /**
     * Constructs a list game request message
     *
     * @param username username of the player
     * @param token    token of the player
     */
    public ListGameRequest(String username, String token) {
        super(username, token, MessageContent.LIST_GAME);
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ListGameRequest{" +
                '}';
    }
}
