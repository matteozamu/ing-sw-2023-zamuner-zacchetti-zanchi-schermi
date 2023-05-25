package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message class for requesting the bot spawn
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
