package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message class to request to delete the selected cards
 */
public class DeleteLimboRequest extends Message{
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;

    /**
     * Constructs a delete limbo request message
     *
     * @param username username of the player
     * @param token    token of the player
     */
    public DeleteLimboRequest(String username, String token) {
        super(username, token, MessageContent.DELETE_LIMBO);
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "DeleteLimboRequest{}";
    }
}
