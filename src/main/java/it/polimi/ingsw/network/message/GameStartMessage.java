package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.CommonGoal;

import java.util.List;

/**
 * Message class to tell the clients that the game is started
 */
public class GameStartMessage extends Message {
    private static final long serialVersionUID = -5671092105322763783L;

    private final String firstPlayer;
    private final List<CommonGoal> cg;

    public GameStartMessage(String firstPlayer, List<CommonGoal> cg) {
        super("ServerUser", null, MessageContent.READY);
        this.firstPlayer = firstPlayer;
        this.cg = cg;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public List<CommonGoal> getCommonGoals() {
        return cg;
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
