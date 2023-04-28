package it.polimi.ingsw.view.cli;

class CliPrinter {
    private static final String ROOM_NULL = "                ";
    private static final String ROOM_EMPTY = "             ";
    private static final String ROOM_DOOR = "     ";
    private static final String EMPTY_WEAPON_ROW = "                                          ";
    private static final String WEAPON_ROW = "║                                   ║     ";
    private static final String EMPTY_CARD_ROW = "                                 ";
    private static final String CARD_SPACING = " ║     ";
    private static final String BOX_ROW = "═══════════════";

    private static final String POINTS_TEXT = "Points: ";

    private CliPrinter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Prints the PlayerBoards to System.out
     *
     * @param out            PrintStream where to print
     * @param gameSerialized status of the game
     */

    /**
     * Clears the console
     */
    static void clearConsole(AdrenalinePrintStream out) {
        out.print(AnsiCode.CLEAR_CONSOLE);
        out.flush();
    }
}
