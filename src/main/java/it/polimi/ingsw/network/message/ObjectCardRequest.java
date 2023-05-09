package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.ObjectCard;

/**
 * Message class for requesting the bot spawn
 */
public class ObjectCardRequest extends Message {
    private static final long serialVersionUID = 1272585248979532293L;

    private final ObjectCard objectCard;

    public ObjectCardRequest(String username, String token, ObjectCard objectCard) {
        super(username, token, MessageContent.PICK_OBJECT_CARD);

        this.objectCard = objectCard;
    }

    public ObjectCard getObjectCard() {
        return objectCard;
    }

    @Override
    public String toString() {
        return "ObjectCardRequest{" +
                "objectCard=" + objectCard +
                '}';
    }
}
