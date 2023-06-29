package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message used to request the list of available games
 */
public class ListGameRequest extends Message {
    private static final long serialVersionUID = 1272585248979532293L;

    public ListGameRequest(String username, String token) {
        super(username, token, MessageContent.LIST_GAME);
    }

    @Override
    public String toString() {
        return "ListGameRequest{" +
                '}';
    }
}
