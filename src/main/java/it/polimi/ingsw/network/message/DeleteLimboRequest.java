package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

/**
 * Message class to request to delete the selected cards
 */
public class DeleteLimboRequest extends Message{
    private static final long serialVersionUID = 1272585248979532293L;

    public DeleteLimboRequest(String username, String token) {
        super(username, token, MessageContent.DELETE_LIMBO);
    }

    @Override
    public String toString() {
        return "DeleteLimboRequest{}";
    }
}
