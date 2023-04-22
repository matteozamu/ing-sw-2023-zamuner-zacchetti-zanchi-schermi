package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Shelf;

/**
 * this message is sent by the server to the client to notify his updated shelf
 */
public class ShelfMessage extends Message {
    private static final long serialVersionUID = -269778592L;
    private final Shelf shelf;

    /**
     * default constructor
     * @param shelf is the updated shelf
     */
    public ShelfMessage(Shelf shelf) {
        super(Game.SERVER_NICKNAME, MessageType.SHELF);
        this.shelf = shelf;
    }

    /**
     * @return the shelf
     */
    public Shelf getShelf() {
        return shelf;
    }

    @Override
    public String toString() {
        return "ShelfMessage{" +
                "shelf=" + shelf +
                '}';
    }
}
