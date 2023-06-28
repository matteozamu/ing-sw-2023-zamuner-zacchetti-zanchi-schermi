package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

import static it.polimi.ingsw.enumeration.Color.*;

/**
 * Represents the type of object card in the game.
 */
public enum ObjectCardType {
    cat("Cat", ANSI_GREEN, ANSI_GREEN_BACKGROUND),
    book("Book", ANSI_WHITE, ANSI_WHITE_BACKGROUND),
    game("Game", ANSI_YELLOW, ANSI_YELLOW_BACKGROUND),
    frame("Frame", ANSI_BLUE, ANSI_BLUE_BACKGROUND),
    trophy("Trophy", ANSI_CYAN, ANSI_CYAN_BACKGROUND),
    plant("Plant", ANSI_MAGENTA, ANSI_MAGENTA_BACKGROUND);

    public static final List<ObjectCardType> VALUES =
            List.of(values());
    public static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    private final String text;
    private final String color;
    private final String colorBG;

    /**
     * Constructs an ObjectCardType with the given text representation.
     *
     * @param text The text representation of the ObjectCardType.
     */
    ObjectCardType(final String text, final String color, final String colorBG) {
        this.text = text;
        this.color = color;
        this.colorBG = colorBG;
    }

    //    /**
//     * @return a coloured string
//     */
//    public String getColoredText() {
//        return Ansi.ansi().fgRgb(r, g, b).a(text).reset().toString();
//    }

    /**
     * Returns a random ObjectCardType.
     *
     * @return A randomly selected ObjectCardType.
     */
    public static ObjectCardType randomObjectCardType() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    /**
     * @return the color of the object card
     */
//    public String getColor() {
//        return color;
//    }

    /**
     * @return the color of the background of the object card
     */
    public String getColorBG() {
        return colorBG;
    }

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
