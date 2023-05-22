package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class EndGameMessage extends Message {

    private final String winner;

    public EndGameMessage(String winner) {
        super("serverUser", null, MessageContent.GAME_ENDED);
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "EndGameMessage{" +
                "winner='" + winner + '\'' +
                '}';
    }
}
