package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ObjectCard;

import java.util.List;
import java.util.Map;

/**
 * this message contains the board and is used to send the updated board to the client
 */
public class BoardMessage extends Message {
    private static final long serialVersionUID = 123456L;
    private final Map<Coordinate, ObjectCard> board;

    /**
     * default constructor
     * @param board is the board to wrap in the message
     */
    public BoardMessage(Map<Coordinate, ObjectCard> board) {
        super(Game.SERVER_NICKNAME, MessageType.BOARD);
        this.board = board;
    }

    /**
     * @return a map containing the coordinates and the relative Object Card
     */
    public Map<Coordinate, ObjectCard> getBoard() {
        return board;
    }


    @Override
    public String toString() {
        return "BoardMessage{" +
                "board=" + board +
                '}';
    }
}
