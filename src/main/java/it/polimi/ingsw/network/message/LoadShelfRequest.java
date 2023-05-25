package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class LoadShelfRequest extends Message {
    private static final long serialVersionUID = 127258524891232293L;
    private int column;

    public LoadShelfRequest(String username, String token, int column) {
        super(username, token, MessageContent.LOAD_SHELF_REQUEST);
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "LoadShelfRequest{" +
                "username=" + getSenderUsername() +
                ", columns=" + column +
                '}';
    }


}
