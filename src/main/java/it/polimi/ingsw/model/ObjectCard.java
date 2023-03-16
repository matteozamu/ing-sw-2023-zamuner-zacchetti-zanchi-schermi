package it.polimi.ingsw.model;

public class ObjectCard {
    private ObjectCardType type;
    private int id; // da 0 a 21

    public ObjectCard(ObjectCardType type, int id) {
        this.type = type;
        this.id = id;
    }

    public ObjectCardType getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}