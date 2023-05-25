package it.polimi.ingsw.model;

import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Represents the type of an object card in the game.
 */
public enum ObjectCardType {
    cat("Cat", 138, 165, 78),
    book("Book", 231, 219, 183),
    game("Game", 218, 165, 69),
    frame("Frame", 24, 106, 144),
    trophy("Trophy", 95, 182, 183),
    plant("Plant", 197, 83, 128);

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

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
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
