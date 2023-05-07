package it.polimi.ingsw.model;

import org.fusesource.jansi.Ansi;

import java.util.List;
import java.util.Random;

/**
 * Represents the type of an object card in the game.
 */
public enum ObjectCardType {
    gatto("Gatto", 138, 165, 78),
    libro("Libro", 231, 219, 183),
    gioco("Gioco", 218, 165, 69),
    cornice("Cornice", 24, 106, 144),
    trofeo("Trofeo", 95, 182, 183),
    pianta("Pianta", 197, 83, 128);

    private final String text;
    private final int r;
    private final int g;
    private final int b;

    /**
     * Constructs an ObjectCardType with the given text representation.
     *
     * @param text The text representation of the ObjectCardType.
     */
    ObjectCardType(final String text, final int r, final int g, final int b) {
        this.text = text;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getColoredText() {
        return Ansi.ansi().fgRgb(r, g, b).a(text).reset().toString();
    }

    public static final List<ObjectCardType> VALUES =
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
