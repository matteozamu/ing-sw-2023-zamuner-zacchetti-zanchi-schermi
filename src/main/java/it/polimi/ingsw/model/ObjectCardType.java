package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

/**
 * Represents the type of an object card in the game.
 */
public enum ObjectCardType {
    gatto("Gatto"),
    libro("Libro"),
    gioco("Gioco"),
    cornice("Cornice"),
    trofeo("Trofeo"),
    pianta("Pianta");
//    empty("NoTile");

    private final String text;

    /**
     * Constructs an ObjectCardType with the given text representation.
     *
     * @param text The text representation of the ObjectCardType.
     */
    ObjectCardType(final String text) {
        this.text = text;
    }

    private static final List<ObjectCardType> VALUES =
            List.of(values());
    public static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    /**
     * Returns a random ObjectCardType.
     *
     * @return A randomly selected ObjectCardType.
     */
    public static ObjectCardType randomObjectCardType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Returns the text representation of the ObjectCardType.
     *
     * @return The text representation of the ObjectCardType.
     */
    @Override
    public String toString() {
        return text;
    }
}
