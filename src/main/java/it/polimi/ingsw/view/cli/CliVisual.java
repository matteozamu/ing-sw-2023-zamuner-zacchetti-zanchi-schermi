package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.JsonReader;

import java.io.PrintStream;
import java.util.List;

public class CliVisual {

    /**
     * Prints the personal goal card in the console
     *
     * @param out            is the output PrintStream
     * @param gameSerialized is the object containing the board to be printed
     */
    public static void printPersonalGoalCards(PrintStream out, GameSerialized gameSerialized) {
        List<PersonalGoal> goals = gameSerialized.getPersonalGoalCard().getGoals();
        StringBuilder sb = new StringBuilder();

        sb.append("Your Personal Goal Card is:\n");

        for (int i = 5; i >= 0; i--) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                boolean found = false;
                for (PersonalGoal goal : goals) {
                    if (goal.getRow() == i && goal.getColumn() == j) {
                        line.append(getColoredText(goal.getType().getR(), goal.getType().getG(), goal.getType().getB())).append(" ");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    line.append("- ");
                }
            }

            String border = new String(new char[10]).replace("\0", "═");
            if (i == 5) {
                sb.append("╔").append(border).append("╗\n");
            }

            sb.append("║").append(line).append("║\n");

            if (i == 0) {
                sb.append("╚").append(border).append("╝\n");
            }
        }

        out.print(sb);
    }

    public static String getColoredText(int r, int g, int b) {
        // Creazione del codice ANSI per impostare il colore di sfondo
        String colorCode = String.format("\033[48;2;%d;%d;%dm", r, g, b);
        // Creazione del codice ANSI per reimpostare il colore di sfondo a quello di default
        String resetCode = "\033[0m";
        // Combinazione del codice di sfondo, del testo vuoto e del codice di reset per creare la stringa formattata
        return String.format("%s%s%s", colorCode, " ", resetCode);
    }

    public static void printScore(PrintStream out, GameSerialized gameSerialized) {
        out.println("You have " + gameSerialized.getPoints() + " points");
    }

    /**
     * Prints the board in the console
     *
     * @param out            is the output PrintStream
     * @param gameSerialized is the object containing the board to be printed
     */
    public static void printBoard(PrintStream out, GameSerialized gameSerialized) {
        ObjectCard objectCard;
        StringBuilder boardView = new StringBuilder();
        Board board = gameSerialized.getBoard();
        List<Player> players = gameSerialized.getAllPlayers();

        JsonReader.readJsonConstant("GameConstant.json");

        int playerNumber = players.size();
        int[][] boardMatrix = JsonReader.getBoard(playerNumber);

        if(playerNumber == 2) {
            boardView.append(" ".repeat(17));
            boardView.append("-3        -2        -1         0         1         2         3\n");
        } else if (playerNumber == 3 || playerNumber == 4) {
            boardView.append(" ".repeat(7));
            boardView.append("-4        -3        -2        -1         0         1         2         3         4\n\n");
        }

        for (int i = 0; i < boardMatrix.length / 2; i++) {
            if(!(playerNumber == 2 && 4 - i == 4)) {
                boardView.append(String.format("%2s ", 4 - i));
            }
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                    if (objectCard != null) {
                        String cardText = objectCard.toString();
                        String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                        int visibleCardTextLength = visibleCardText.length();
                        if(visibleCardTextLength == 7){
                            boardView.append("║").append(" ").append(objectCard).append(" ");
                        } else if (visibleCardTextLength == 6) {
                            boardView.append("║").append(" ".repeat(1)).append(objectCard).append(" ".repeat(2));
                        } else if (visibleCardTextLength == 5) {
                            boardView.append("║").append(" ".repeat(2)).append(objectCard).append(" ".repeat(2));
                        }
                    } else {
                        boardView.append("║ ").append(" ".repeat(8));
                    }
                    if(playerNumber == 2) {
                        if((4 - i == 3 && j - 4 == 0) || (4 - i == 2 && j - 4 == 1) ||  (4 - i == 1 && j - 4 == 3)){
                            boardView.append("║");
                        }
                    }
                } else {
                    boardView.append(" ".repeat(10));
                }
            }
            boardView.append("\n");
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length; i++) {
            if(!(playerNumber == 2 && 4 - i == -4)) {
                boardView.append(String.format("%2s ", 4 - i));
            }
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                    if (objectCard != null) {
                        String cardText = objectCard.toString();
                        String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                        int visibleCardTextLength = visibleCardText.length();
                        if(visibleCardTextLength == 7){
                            boardView.append("║").append(" ").append(objectCard).append(" ");
                        } else if (visibleCardTextLength == 6) {
                            boardView.append("║").append(" ".repeat(1)).append(objectCard).append(" ".repeat(2));
                        } else if (visibleCardTextLength == 5) {
                            boardView.append("║").append(" ".repeat(2)).append(objectCard).append(" ".repeat(2));
                        }
                    } else {
                        boardView.append("║ ").append(" ".repeat(8));
                    }
                    if(playerNumber == 2) {
                        if((4 - i == -1 && j - 4 == 2) || (4 - i == -2 && j - 4 == 1) ||  (4 - i == -3 && j - 4 == 1) || (4 - i == 0 && j - 4 == 3)){
                            boardView.append("║");
                        }
                    }
                } else {
                    boardView.append(" ".repeat(10));
                }
            }
            boardView.append("\n");
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

        int maxLengthType = 9;
        StringBuilder shelfView = new StringBuilder();

        String horizontalBorder = "═".repeat(48);
        String topBorder = "╔" + horizontalBorder + "═╗\n";
        String bottomBorder = "╚" + horizontalBorder + "═╝\n";
        String middleBorder = "╠" + horizontalBorder + "═╣\n";

        shelfView.append(topBorder);

        for (int row = Shelf.ROWS - 1; row >= 0; row--) {
            shelfView.append("║");
            for (int col = 0; col < Shelf.COLUMNS; col++) {
                Coordinate coord = new Coordinate(row, col);
                ObjectCard card = s.getObjectCard(coord);
                if (card == null) {
                    shelfView.append("-".repeat(maxLengthType)); // Subtract 2 to account for cell borders
                } else {
                    String cardText = card.toString();
                    String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                    int visibleCardLength = visibleCardText.length();
                    if(visibleCardLength == 7){
                        shelfView.append(" ").append(cardText).append(" ");
                    } else if (visibleCardLength == 6) {
                        shelfView.append(" ".repeat(2)).append(cardText).append(" ".repeat(1));
                    } else if (visibleCardLength == 5) {
                        shelfView.append(" ".repeat(2)).append(cardText).append(" ".repeat(2));
                    }
                }
                shelfView.append("║");
            }
            shelfView.append("\n");
            if (row > 0) {
                shelfView.append(middleBorder);
            }
        }
        shelfView.append(bottomBorder);

        out.print(shelfView);
    }


    public static void printLimbo(PrintStream out, GameSerialized gameSerialized) {
        List<ObjectCard> limboCards = gameSerialized.getAllLimboCards();
        StringBuilder limbo = new StringBuilder();

        if (limboCards.size() == 1) {
            limbo.append("You have selected this card:\n");
        } else {
            limbo.append("You have selected these cards:\n");
        }

        if (!limboCards.isEmpty()) {
            int totalLength = 0;
            for (int i = 0; i < limboCards.size(); i++) {
                String visibleCardText = limboCards.get(i).toString().replaceAll("\u001B\\[[;\\d]*m", "");
                totalLength += visibleCardText.length() + 2;
                if (i != 0) {
                    totalLength += 1;
                }
            }

            String border = new String(new char[totalLength]).replace("\0", "═");
            limbo.append("╔").append(border).append("╗\n");

            for (ObjectCard card : limboCards) {
                limbo.append(String.format("║ %-7s ", card.toString()));
            }
            limbo.append("║\n");

            limbo.append("╚").append(border).append("╝\n");
        }

        limbo.append("\n");
        out.print(limbo);
    }
}
