package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * this method notify the client that the game is ended, namely a shelf is full
 */
public class EndGameMessage extends Message {
    private static final long serialVersionUID = -13253592L;

    public EndGameMessage() {
        super(Game.SERVER_NICKNAME, MessageType.END_GAME);
    }

    @Override
    public String toString() {
        return "EndGameMessage{username= " + getNickname() +"}";
    }
}
