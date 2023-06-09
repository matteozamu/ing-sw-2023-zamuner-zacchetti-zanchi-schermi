package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.Coordinate;

/**
 * Message class for requesting the bot spawn
 */
public class ObjectCardRequest extends Message {
    private static final long serialVersionUID = 1272585248979532293L;

    private final Coordinate coordinate;

    public ObjectCardRequest(String username, String token, Coordinate coordinate) {
        super(username, token, MessageContent.PICK_OBJECT_CARD);
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return "ObjectCardRequest{" +
                "token='" + super.getToken() + '\'' +
                "coordinate=" + coordinate +
                '}';
    }
}
