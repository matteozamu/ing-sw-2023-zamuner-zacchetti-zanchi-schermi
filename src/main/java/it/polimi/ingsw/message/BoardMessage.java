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
    private final String activePlayers;
    private final List<String> players;

    /**
     * default constructor
     * @param board is the board to wrap in the message
     */
    public BoardMessage(String senderUsername, MessageType messageType, List<String> players, Map<Coordinate, ObjectCard> board, String activePlayers) {
        super(senderUsername, messageType);
        this.players = players;
        this.board = board;
        this.activePlayers = activePlayers;
    }

    /**
     * @return a map containing the coordinates and the relative Object Card
     */
    public Map<Coordinate, ObjectCard> getBoard() {
        return board;
    }

    /**
     *
     * @return the active player in that turn
     */
    public String getActivePlayers() {
        return activePlayers;
    }

    /**
     *
     * @return the list of player in the game
     */
    public List<String> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "BoardMessage{" +
                "board=" + board +
                ", activePlayers='" + activePlayers + '\'' +
                ", players=" + players +
                '}';
    }
}
