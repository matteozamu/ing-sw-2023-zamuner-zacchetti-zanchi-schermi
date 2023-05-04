package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;

import java.io.PrintStream;
import java.util.Arrays;

public class CliVisual {
    public static void printBoard(PrintStream out, GameSerialized gameSerialized) {
        ObjectCard objectCard;
        Board b = gameSerialized.getBoard();

        for (int row = 1; row <= 5; row++) {
            String s = "";
            for (int spaces = 5 - row; spaces > 0; spaces--) s += "\t\t";
            for (int col = 1; col < 2 * row; col++) {
                objectCard = b.getGrid().get(new Coordinate(5 - row, -5 + col));
                s += ("|" + objectCard);
            }
            out.println(s);
        }
        for (int row = 5 - 1; row >= 1; row--) {
            String s = "";
            for (int spaces = 5 - row; spaces > 0; spaces--) s += "\t\t";
            for (int col = 1; col < 2 * row; col++) {
                objectCard = b.getGrid().get(new Coordinate(-5 + row, -5 + col));
                s += ("|" + objectCard);
            }
            out.println(s);
        }
    }

    /**
     * Prints the layout of object cards on the Shelf, including both the type and ID of each card.
     * Cards are printed in reverse row order, starting from the last row and proceeding towards the first.
     * Each card is represented as "type-id", where "type" is the card type and "id" is its ID.
     * In case a cell is empty, a placeholder with dashes ("-") will be printed.
     */
    public static void printShelf(PrintStream out, GameSerialized gameSerialized) {
        Shelf s = gameSerialized.getShelf();

        int maxLength = Arrays.stream(ObjectCardType.values())
                .map(ObjectCardType::toString)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        maxLength += 2; // Adds space for ID and separator "-"

        for (int row = Shelf.ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < Shelf.COLUMNS; col++) {
                Coordinate coord = new Coordinate(row, col);
                ObjectCard card = s.getGrid().get(coord);
                if (card == null) {
                    System.out.print("-".repeat(maxLength));
                } else {
                    String cardText = card.toString();
                    int padding = maxLength - cardText.length();
                    System.out.print(cardText + " ".repeat(padding));
                }
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}
