package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.Board;

/**
 * Message class to tell the clients that the game is started
 */
public class BoardMessage extends Message {
    private static final long serialVersionUID = -5671092105322763783L;

    private final Board board;

    public BoardMessage(Board board) {
        super("ServerUser", null, MessageContent.BOARD);
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "GameStartMessage{" +
                "senderUsername=" + getSenderUsername() + ", " +
                "board='" + board + '\'' +
                ", content=" + getContent() +
                '}';
    }
}
