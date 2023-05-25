package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.util.UUID;

/**
 * Message class for requesting the bot spawn
 */
public class CreateGameRequest extends Message {
    private static final long serialVersionUID = 1272585248979532293L;
    private UUID gameUUID;

    public CreateGameRequest(String username, String token) {
        super(username, token, MessageContent.CREATE_GAME);
    }

    @Override
    public String toString() {
        return "CreateGameRequest{" +
                '}';
    }
}
