package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

import java.io.Serial;

/**
 * Message class to inform the clients about the model changes
 */
public class GameStateResponse extends Message {
    @Serial
    private static final long serialVersionUID = 2725986184174583892L;

    private final GameSerialized gameSerialized;
    private final String turnOwner;

    /**
     * Constructs a game state response message
     *
     * @param username  username of the player
     * @param turnOwner username of the player that has the turn
     * @param filepath  filepath of the game
     */
    public GameStateResponse(String username, String turnOwner, String filepath) {
        super("serverUser", null, MessageContent.GAME_STATE);
        this.gameSerialized = new GameSerialized(username, filepath);
        this.turnOwner = turnOwner;
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
     * Returns the username of the player that has the turn
     *
     * @return username of the player that has the turn
     */
    public String getTurnOwner() {
        return turnOwner;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "GameStateResponse{" +
                ", turnOwner='" + turnOwner + '\'' +
                '}';
    }
}