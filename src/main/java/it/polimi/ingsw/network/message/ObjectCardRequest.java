package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.Coordinate;

import java.io.Serial;

/**
 * Message sent by the client containing the coordinate of the object card to pick in order to verify if the player can pick it
 */
public class ObjectCardRequest extends Message {
    @Serial
    private static final long serialVersionUID = 1272585248979532293L;

    private final Coordinate coordinate;

    /**
     * Constructs a message
     *
     * @param username   username of the sender
     * @param token      token of the sender
     * @param coordinate coordinate of the object card to pick
     */
    public ObjectCardRequest(String username, String token, Coordinate coordinate) {
        super(username, token, MessageContent.PICK_OBJECT_CARD);
        this.coordinate = coordinate;
    }

    /**
     * Returns the coordinate of the object card to pick
     *
     * @return coordinate of the object card to pick
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ObjectCardRequest{" +
                "token='" + super.getToken() + '\'' +
                "coordinate=" + coordinate +
                '}';
    }
}
