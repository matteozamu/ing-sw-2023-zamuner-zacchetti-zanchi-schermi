package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

/**
 * Message class to inform the clients that the game is ended
 */
public class EndGameMessage extends Message {
    private static final long serialVersionUID = 2725986185374583892L;


    private final GameSerialized gameSerialized;

    public EndGameMessage(String username, String filepath) {
        super("serverUser", null, MessageContent.GAME_ENDED);
        this.gameSerialized = new GameSerialized(username, filepath);
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    @Override
    public String toString() {
        return "EndGameMessage{" +
                "gameSerialized=" + gameSerialized +
                '}';
    }
}
