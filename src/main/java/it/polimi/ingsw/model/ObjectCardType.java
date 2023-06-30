package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.enumeration.Color.*;

/**
 * Represents the type of object card in the game.
 */
public enum ObjectCardType {
    /**
     * Represents a cat object card.
     */
    cat("Cat", ANSI_GREEN, ANSI_GREEN_BACKGROUND),

    /**
     * Represents a book object card.
     */
    book("Book", ANSI_WHITE, ANSI_WHITE_BACKGROUND),

    /**
     * Represents a game object card.
     */
    game("Game", ANSI_YELLOW, ANSI_YELLOW_BACKGROUND),

    /**
     * Represents a frame object card.
     */
    frame("Frame", ANSI_BLUE, ANSI_BLUE_BACKGROUND),

    /**
     * Represents a trophy object card.
     */
    trophy("Trophy", ANSI_CYAN, ANSI_CYAN_BACKGROUND),

    /**
     * Represents a plant object card.
     */
    plant("Plant", ANSI_MAGENTA, ANSI_MAGENTA_BACKGROUND);

    /**
     * The list of all ObjectCardType values.
     */
    public static final List<ObjectCardType> VALUES = List.of(values());

    /**
     * The size of the ObjectCardType enumeration.
     */
    public static final int SIZE = VALUES.size();

    /**
     * The random generator used to select a random ObjectCardType.
     */
    private static final Random RANDOM = new Random();
    private final String text;
    private final String color;
    private final String colorBG;

    /**
     * Constructs an ObjectCardType with the given text representation.
     *
     * @param text    The text representation of the ObjectCardType.
     * @param color   The color of the ObjectCardType.
     * @param colorBG The color of the background of the ObjectCardType.
     */
    ObjectCardType(final String text, final String color, final String colorBG) {
        this.text = text;
        this.color = color;
        this.colorBG = colorBG;
    }

    /**
     * Returns a random ObjectCardType.
     *
     * @return A randomly selected ObjectCardType.
     */
    public static ObjectCardType randomObjectCardType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * Return the color of the object card
     *
     * @return the color of the object card
     */
    public String getColor() {
        return color;
    }

    /**
     * Return the color of the background of the object card
     *
     * @return the color of the background of the object card
     */
    public String getColorBG() {
        return colorBG;
    }

    /**
     * Returns the type of the ObjectCardType.
     *
     * @return The type of the ObjectCardType.
     */
    public String getText() {
        return text;
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