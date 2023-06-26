package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

/**
 * Message class to inform the clients about a game state update
 */
// TODO non usata, pensare se toglierla o no
public class ObjectCardResponse extends Message {
    private static final long serialVersionUID = 2725986184174583892L;

    private final GameSerialized gameSerialized;

    public ObjectCardResponse(String username, String filepath) {
        super("serverUser", null, MessageContent.OBJECT_CARD_VALID);
        this.gameSerialized = new GameSerialized(username, filepath);
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }
}
