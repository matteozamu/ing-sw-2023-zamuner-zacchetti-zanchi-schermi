package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;

import java.util.List;

/**
 * Message class to tell the clients that the game is started
 */
public class GameStartMessage extends Message {
    private static final long serialVersionUID = -5671092105322763783L;

    private final String firstPlayer;
    private final List<CommonGoal> cg;
    private final GameSerialized gameSerialized;

    public GameStartMessage(String firstPlayer, List<CommonGoal> cg, String username) {
        super("ServerUser", null, MessageContent.READY);
        this.gameSerialized = new GameSerialized(username);
        this.firstPlayer = firstPlayer;
        this.cg = cg;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public List<CommonGoal> getCommonGoals() {
        return cg;
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    @Override
    public String toString() {
        return "GameStartMessage{" +
                "senderUsername=" + getSenderUsername() + ", " +
                "firstPlayer='" + firstPlayer + '\'' +
                "cg=" + cg + '\'' +
                ", content=" + getContent() +
                '}';
    }
}
