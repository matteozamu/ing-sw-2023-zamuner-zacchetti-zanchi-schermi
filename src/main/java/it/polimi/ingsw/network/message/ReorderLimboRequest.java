package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;
import java.util.ArrayList;

/**
 * Message used to reorder the selected object cards
 */
public class ReorderLimboRequest extends Message{
    @Serial
    private static final long serialVersionUID = 127258524891232293L;
    private ArrayList<Integer> newLimbo;

    /**
     * Constructs a message
     *
     * @param username username of the sender
     * @param token    token of the sender
     * @param newLimbo new order of the selected object cards
     */
    public ReorderLimboRequest(String username, String token, ArrayList<Integer> newLimbo) {
        super(username, token, MessageContent.REORDER_LIMBO_REQUEST);
        this.newLimbo = newLimbo;
    }

    /**
     * Returns the new order of the selected object cards
     *
     * @return new order of the selected object cards
     */
    public ArrayList<Integer> getLimboOrder() {
        return newLimbo;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ReorderLimboRequest{" +
                "limboOrder=" + newLimbo +
                '}';
    }
}