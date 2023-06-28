package it.polimi.ingsw.model;

import java.io.Serializable;

import static it.polimi.ingsw.enumeration.Color.ANSI_RESET;

/**
 * Represents an object card in the game.
 */
public class ObjectCard implements Serializable {
    private final ObjectCardType type;
    private final String ID;

    /**
     * Constructs a new ObjectCard with the given type and ID.
     *
     * @param type The type of the object card.
     * @param ID   The ID of the object card.
     * @throws IllegalArgumentException If the ID is not between 0 and 2.
     */
    public ObjectCard(ObjectCardType type, String ID) throws IllegalArgumentException {
        if (ID == null || ID.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        this.type = type;
        this.ID = ID;
    }


    /**
     * Gets the type of the object card.
     *
     * @return The ObjectCardType of the object card.
     */
    public ObjectCardType getType() {
        return type;
    }

    /**
     * Gets the ID of the object card.
     *
     * @return The ID of the object card.
     */
    public String getId() {
        return ID;
    }



    /**
     * Returns a string representation of the object card.
     *
     * @return A string representation of the object card in the format "type-id".
     */
    @Override
    public String toString() {
        return type.getColor() + type.getText() + ANSI_RESET;
    }
}
