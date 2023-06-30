package it.polimi.ingsw.enumeration;

/**
 * Enumeration of the colors used in the game.
 */
public class Color {
    /**
     * ANSI escape code to reset the color.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * ANSI escape code to clear the console.
     */
    public static final String CLEAR_CONSOLE = "\033[H\033[2J";

    /**
     * ANSI escape code for the color magenta (used for plant).
     */
    public static final String ANSI_MAGENTA = "\u001B[35m";

    /**
     * ANSI escape code for the color yellow (used for game).
     */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * ANSI escape code for the color blue (used for frame).
     */
    public static final String ANSI_BLUE = "\u001B[34m";

    /**
     * ANSI escape code for the color green (used for cat).
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * ANSI escape code for the color white (used for book).
     */
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * ANSI escape code for the color cyan (used for trophy).
     */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * ANSI escape code for the background color magenta (used for plant).
     */
    public static final String ANSI_MAGENTA_BACKGROUND = "\u001B[45m";

    /**
     * ANSI escape code for the background color yellow (used for game).
     */
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    /**
     * ANSI escape code for the background color blue (used for frame).
     */
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    /**
     * ANSI escape code for the background color green (used for cat).
     */
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

    /**
     * ANSI escape code for the background color white (used for book).
     */
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * ANSI escape code for the background color cyan (used for trophy).
     */
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
}
