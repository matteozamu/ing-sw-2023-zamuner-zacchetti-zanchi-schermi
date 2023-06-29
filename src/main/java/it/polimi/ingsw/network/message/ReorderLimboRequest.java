package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.util.ArrayList;

/**
 * Message used to reorder the selected object cards
 */
public class ReorderLimboRequest extends Message{
    private static final long serialVersionUID = 127258524891232293L;
    private ArrayList<Integer> newLimbo;

    public ReorderLimboRequest(String username, String token, ArrayList<Integer> newLimbo) {
        super(username, token, MessageContent.REORDER_LIMBO_REQUEST);
        this.newLimbo = newLimbo;
    }

    public ArrayList<Integer> getLimboOrder() {
        return newLimbo;
    }

    @Override
    public String toString() {
        return "ReorderLimboRequest{" +
                "limboOrder=" + newLimbo +
                '}';
    }
}
