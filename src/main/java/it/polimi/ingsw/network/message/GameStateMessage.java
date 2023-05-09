package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

/**
 * Message class to inform the clients about a game state update
 */
public class GameStateMessage extends Message {
    private static final long serialVersionUID = 2725986184174583892L;

    private final GameSerialized gameSerialized;
    private final String turnOwner;

    public GameStateMessage(String username, String turnOwner) {
        super("serverUser", null, MessageContent.GAME_STATE);
        this.gameSerialized = new GameSerialized(username);
        this.turnOwner = turnOwner;
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    public String getTurnOwner() {
        return turnOwner;
    }
}
