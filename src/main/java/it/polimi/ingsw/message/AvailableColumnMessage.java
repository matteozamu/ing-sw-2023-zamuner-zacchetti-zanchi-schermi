package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * message sent by the server to notify which columns are available
 */
public class AvailableColumnMessage extends Message {
    private static final long serialVersionUID = 456132L;
    private final List<Integer> columns = new ArrayList<>();

    /**
     * default constructor
     * @param columns is the list of available columns
     */
    public AvailableColumnMessage(List<Integer> columns) {
        super(Game.SERVER_NICKNAME, MessageType.AVAILABLE_COLUMNS);
        this.columns.addAll(columns);
    }

    /**
     * @return the list of available columns
     */
    public List<Integer> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "AvailableColumnMessage{" +
                "columns=" + columns +
                '}';
    }
}
