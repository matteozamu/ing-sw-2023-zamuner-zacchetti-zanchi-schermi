package it.polimi.ingsw.view;

import com.google.gson.*;
import it.polimi.ingsw.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CliVisual {

    /**
     * Prints the personal goal card in the console
     * @param out is the output PrintStream
     * @param gameSerialized is the object containing the board to be printed
     */
    public static void printPersonalGoalCards(PrintStream out, GameSerialized gameSerialized) {
        PersonalGoalCard personalGoalCard = gameSerialized.getPersonalGoalCard();
        out.println("Your Personal Goal Card is:");
        out.println(personalGoalCard.toString());
        printPGC(out, personalGoalCard);
    }

    public static void printPGC(PrintStream out, PersonalGoalCard personalGoalCard){
        List<PersonalGoal> goals = personalGoalCard.getGoals();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                boolean found = false;
                for (PersonalGoal goal : goals) {
                    if (goal.getRow() == i && goal.getColumn() == j) {
                        out.print(getColoredText(goal.getType().getR(), goal.getType().getG(), goal.getType().getB()) + " ");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    out.print("- ");
                }
            }
            out.println();
        }
    }

    public static String getColoredText(int r, int g, int b) {
        // Creazione del codice ANSI per impostare il colore di sfondo
        String colorCode = String.format("\033[48;2;%d;%d;%dm", r, g, b);
        // Creazione del codice ANSI per reimpostare il colore di sfondo a quello di default
        String resetCode = "\033[0m";
        // Combinazione del codice di sfondo, del testo vuoto e del codice di reset per creare la stringa formattata
        String coloredText = String.format("%s%s%s", colorCode, " ", resetCode);
        return coloredText;
    }



    /**
     * Prints the board in the console
     * @param out is the output PrintStream
     * @param gameSerialized is the object containing the board to be printed
     */
    public static void printBoard(PrintStream out, GameSerialized gameSerialized) {
        ObjectCard objectCard;
        StringBuilder boardView = new StringBuilder();
        Board board = gameSerialized.getBoard();

        for (int row = 1; row <= 5; row++) {
            boardView.append("\t".repeat(5 - row));
            for (int col = 1; col < 2 * row; col++) {
                int x = 5 - row;
                int y = -5 + col;
                objectCard = board.getGrid().get(new Coordinate(x, y));
                if(objectCard != null) {
                    boardView.append("| ").append(objectCard).append(" (").append(x).append(",").append(y).append(") ");
                } else {
                    boardView.append(" ".repeat(16));
                }
            }
            boardView.append("|\n");
        }
        for (int row = 5 - 1; row >= 1; row--) {
            boardView.append("\t".repeat(Math.max(0, 5 - row)));
            for (int col = 1; col < 2 * row; col++) {
                int x = -5 + row;
                int y = -5 + col;
                objectCard = board.getGrid().get(new Coordinate(x, y));
                if(objectCard != null) {
                    boardView.append("| ").append(objectCard).append(" (").append(x).append(",").append(y).append(") ");
                } else {
                    boardView.append(" ".repeat(16));
                }
            }
            boardView.append("|\n");
        }
        out.println(boardView);
    }

    /**
     * Prints the layout of object cards on the Shelf, including both the type and ID of each card.
     * Cards are printed in reverse row order, starting from the last row and proceeding towards the first.
     * Each card is represented as "type-id", where "type" is the card type and "id" is its ID.
     * In case a cell is empty, a placeholder with dashes ("-") will be printed.
     */
    public static void printShelf(PrintStream out, GameSerialized gameSerialized) {
        Shelf s = gameSerialized.getShelf();

        int maxLength = 9;

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
