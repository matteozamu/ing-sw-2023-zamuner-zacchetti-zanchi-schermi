package it.polimi.ingsw.message;

import it.polimi.ingsw.model.ObjectCard;

import java.util.ArrayList;
import java.util.List;

/**
 * this message contains the list of the Object Card in the order the user want them to be add in the shelf
 */
public class OrderedObjCardMessage extends Message {
    private static final long serialVersionUID = -352521L;
    private final List<ObjectCard> objectCards = new ArrayList<>();

    /**
     * default constuctor
     * @param username is the client who sent the message
     * @param objectCards contains the list of Object Card in the order wanted by the user
     */
    public OrderedObjCardMessage(String username, List<ObjectCard> objectCards) {
        super(username, MessageType.ORDERED_OBJ_CARD);
        this.objectCards.addAll(objectCards);
    }

    /**
     * @return the list of the ordered Object Card
     */
    public List<ObjectCard> getObjectCards() {
        return objectCards;
    }


    @Override
    public String toString() {
        return "OrderedObjCardMessage{" +
                "objectCards=" + objectCards +
                '}';
    }
}
