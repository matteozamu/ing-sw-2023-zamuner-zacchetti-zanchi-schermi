package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.ObjectCard;
import it.polimi.ingsw.enumeration.MessageContent;

import java.util.List;

public class ReorderLimboRequest extends Message{
    private static final long serialVersionUID = 127258524891232293L;
    private List<ObjectCard> limboOrder;

    public ReorderLimboRequest(String username, String token, List<ObjectCard> limboOrder) {
        super(username, token, MessageContent.REORDER_LIMBO_REQUEST);
        this.limboOrder = limboOrder;
    }

    public List<ObjectCard> getLimboOrder() {
        return limboOrder;
    }

    @Override
    public String toString() {
        return "ReorderLimboRequest{" +
                "limboOrder=" + limboOrder +
                '}';
    }
}
