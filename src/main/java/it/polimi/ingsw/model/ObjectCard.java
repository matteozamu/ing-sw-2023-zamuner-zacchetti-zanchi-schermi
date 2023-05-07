package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Represents an object card in the game.
 */

//TODO : se si pensa che la classe rimarrà cosi in futuro la si può sostituire con un record
public class ObjectCard implements Serializable {
    private final ObjectCardType type;
    private final int id;

    /**
     * Constructs a new ObjectCard with the given type and ID.
     *
     * @param type The type of the object card.
     * @param id   The ID of the object card.
     * @throws IllegalArgumentException If the ID is not between 0 and 2.
     */
    public ObjectCard(ObjectCardType type, int id) throws IllegalArgumentException {
        this.type = type;
        if (id >= 0 && id <= 2) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Value must be between 0 and 2");
        }
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
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the object card.
     *
     * @return A string representation of the object card in the format "type-id".
     */
    @Override
    public String toString() {
        return type.getColoredText() + "-" + id;
    }
}
