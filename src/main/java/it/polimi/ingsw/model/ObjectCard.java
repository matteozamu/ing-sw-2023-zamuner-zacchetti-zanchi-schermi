package it.polimi.ingsw.model;

/**
 * Represents an object card in the game.
 */
public class ObjectCard {
    private ObjectCardType type;
    private int id;

    public ObjectCard(ObjectCardType type, int id) {
        this.type = type;
        if(id >= 0 && id <= 21){
            this.id = id;
        } else {
            throw new IllegalArgumentException("Value must be between 0 and 21");
        }

    }

    public ObjectCardType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return type + "-" + id;
    }
}