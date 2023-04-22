package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ObjectCard;

import java.util.ArrayList;
import java.util.List;

/**
 * message used to the client to send the Object Cars to verify
 */
public class ObjCardRequestMessage extends Message{
    private static final long serialVersionUID = 234567L;
    private final List<ObjectCard> objectCards = new ArrayList<>();

    /**
     * default constructor
     * @param username rappresent who sent the message
     * @param objectCards rappresent the object cards choosen by the client, sent to the server to be verified
     */
    public ObjCardRequestMessage(String username, List<ObjectCard> objectCards) {
        super(username, MessageType.OBJ_CARD_REQUEST);  // metto come primo parametro colui che invia il messaggio, come secondo la tipologia di messaggio
        this.objectCards.addAll(objectCards);
    }

    /**
     * @return the list of object cards
     */
    public List<ObjectCard> getObjectCards() {
        return objectCards;
    }

    @Override
    public String toString() {
        return "ObjCardRequestMessage{" +
                "objectCards=" + objectCards +
                '}';
    }
}
