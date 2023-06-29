package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class to request to create a new game
 */
public class CreateGameRequest extends Message {
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;

    public CreateGameRequest(String username, String token) {
        super(username, token, MessageContent.CREATE_GAME);
    }

    @Override
    public String toString() {
        return "CreateGameRequest{" +
                '}';
    }
}
