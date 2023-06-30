package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

import java.io.Serial;

/**
 * Message class to inform the clients that the game is ended
 */
public class EndGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = 2725986185374583892L;

    private final GameSerialized gameSerialized;

    /**
     * Constructs an end game message
     *
     * @param username username of the player
     * @param filepath filepath of the game
     */
    public EndGameMessage(String username, String filepath) {
        super("serverUser", null, MessageContent.GAME_ENDED);
        this.gameSerialized = new GameSerialized(username, filepath);
    }

    /**
     * Returns the game serialized
     *
     * @return game serialized
     */
    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "EndGameMessage{" +
                "gameSerialized=" + gameSerialized +
                '}';
    }
}
