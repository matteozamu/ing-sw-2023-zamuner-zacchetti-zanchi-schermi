package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message sent by the client to load the shelf of the player
 */
public class LoadShelfRequest extends Message {
    @Serial
    private static final long serialVersionUID = 127258524891232293L;
    private int column;

    /**
     * Constructs a load shelf request message
     *
     * @param username username of the player
     * @param token    token of the player
     * @param column   column of the shelf
     */
    public LoadShelfRequest(String username, String token, int column) {
        super(username, token, MessageContent.LOAD_SHELF_REQUEST);
        this.column = column;
    }

    /**
     * Method that returns the column of the shelf
     *
     * @return column of the shelf
     */
    public int getColumn() {
        return column;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "LoadShelfRequest{" +
                "username=" + getSenderUsername() +
                ", columns=" + column +
                '}';
    }


}
