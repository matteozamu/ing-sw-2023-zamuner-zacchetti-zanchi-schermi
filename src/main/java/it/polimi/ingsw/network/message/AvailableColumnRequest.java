package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class AvailableColumnRequest extends Message {
    private static final long serialVersionUID = 127258524891232293L;

    public AvailableColumnRequest(String username, String token) {
        super(username, token, MessageContent.AVAILABLE_COLUMN_REQUEST);
    }

    @Override
    public String toString() {
        return "AvailableColumnRequest{" +
                "username=" + getSenderUsername() +
                '}';
    }


}
