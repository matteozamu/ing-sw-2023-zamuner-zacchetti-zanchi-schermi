package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;

import java.io.Serial;
import java.util.List;

/**
 * Message class to tell the clients that the game is started
 */
public class GameStartMessage extends Message {
    @Serial
    private static final long serialVersionUID = -5671092105322763783L;

    private final String firstPlayer;
    private final List<CommonGoal> commonGoals;
    private final GameSerialized gameSerialized;

    /**
     * Constructs a game start message
     *
     * @param firstPlayer first player of the game
     * @param commonGoals common goals of the game
     * @param username    username of the player
     * @param filepath    filepath of the game
     */
    public GameStartMessage(String firstPlayer, List<CommonGoal> commonGoals, String username, String filepath) {
        super("ServerUser", null, MessageContent.READY);
        this.gameSerialized = new GameSerialized(username, filepath);
        this.firstPlayer = firstPlayer;
        this.commonGoals = commonGoals;
    }

    /**
     * Returns the first player of the game
     */
    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Returns the common goals of the game
     */
    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Returns the game serialized
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
        return "GameStartMessage{" +
                "senderUsername=" + getSenderUsername() + ", " +
                "firstPlayer='" + firstPlayer + '\'' +
                ", content=" + getContent() +
                '}';
    }
}