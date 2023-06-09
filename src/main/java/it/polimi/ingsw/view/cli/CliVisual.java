package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.JsonReader;

import java.io.PrintStream;
import java.util.List;

public class CliVisual {

    /**
     * Prints the personal goal card in the console
     *
     * @param out is the output PrintStream
     * @param gameSerialized is the object containing the personal goal to be printed
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

    /**
     * @param r red
     * @param g green
     * @param b blue
     * @return a coloured string
     */
    public static String getColoredText(int r, int g, int b) {
        // TODO se serve cambiare questa stringa per i colori
        String colorCode = String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
        String resetCode = "\u001B[0m";
        return String.format("%s%s%s", colorCode, " ", resetCode);
    }

    /**
     * print the score of the user
     * @param out is the output PrintStream
     * @param gameSerialized is the object containing the points to be printed
     */
    public static void printScore(PrintStream out, GameSerialized gameSerialized) {
        out.println("You have " + gameSerialized.getPoints() + " points");
    }

    /**
     * Prints the board in the console
     *
     * @param out is the output PrintStream
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

        if (playerNumber == 2) {
            boardView.append(" ".repeat(15));
            boardView.append("-3       -2        -1       0        1        2        3\n");
        } else if (playerNumber == 3 || playerNumber == 4) {
            boardView.append(" ".repeat(7));
            boardView.append("-4       -3       -2       -1       0        1        2        3        4\n\n");
        }

        for (int i = 0; i < boardMatrix.length / 2; i++) {
            if (!(playerNumber == 2 && 4 - i == 4)) {
                boardView.append(String.format("%2s ", 4 - i));
            }
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                    if (objectCard != null) {
                        String cardText = objectCard.toString();
                        String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                        int visibleCardLength = visibleCardText.length();
                        if (visibleCardLength % 2 == 0) {
                            boardView.append("║").append(" ".repeat((8 - visibleCardText.length())/2)).append(objectCard).append(" ".repeat((8 - visibleCardText.length())/2));
                        } else {
                            boardView.append("║").append(" ".repeat((8 - visibleCardText.length())/2)).append(objectCard).append(" ".repeat((int) Math.ceil((double)(8 - visibleCardText.length()) / 2)));
                        }
                    } else {
                        boardView.append("║").append(" ".repeat(8));
                    }
                    if (playerNumber == 2) {
                        if ((4 - i == 3 && j - 4 == 0) || (4 - i == 2 && j - 4 == 1) || (4 - i == 1 && j - 4 == 3)) {
                            boardView.append("║");
                        }
                    }
                } else {
                    boardView.append(" ".repeat(9));
                }
            }
            boardView.append("\n");
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length; i++) {
            if (!(playerNumber == 2 && 4 - i == -4)) {
                boardView.append(String.format("%2s ", 4 - i));
            }
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                    if (objectCard != null) {
                        String cardText = objectCard.toString();
                        String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                        int visibleCardLength = visibleCardText.length();
                        if (visibleCardLength % 2 == 0) {
                            boardView.append("║").append(" ".repeat((8 - visibleCardText.length()) / 2)).append(objectCard).append(" ".repeat((8 - visibleCardText.length()) / 2));
                        } else {
                            boardView.append("║").append(" ".repeat((8 - visibleCardText.length())/2)).append(objectCard).append(" ".repeat((int) Math.ceil((double)(8 - visibleCardText.length()) / 2)));
                        }
                    } else {
                        boardView.append("║").append(" ".repeat(8));
                    }
                    if (playerNumber == 2) {
                        if ((4 - i == -1 && j - 4 == 2) || (4 - i == -2 && j - 4 == 1) || (4 - i == -3 && j - 4 == 1) || (4 - i == 0 && j - 4 == 3)) {
                            boardView.append("║");
                        }
                    }
                } else {
                    boardView.append(" ".repeat(9));
                }
            }
            boardView.append("\n");
        }
        out.println(boardView);
    }

    /**
     * Prints the layout of object cards on the Shelf.
     * Cards are printed in reverse row order, starting from the last row and proceeding towards the first.
     * Each card is represented as "type", where "type" is the card type.
     */
    public static void printShelf(PrintStream out, GameSerialized gameSerialized) {
        Shelf s = gameSerialized.getShelf();

        int maxLengthType = 8;
        StringBuilder shelfView = new StringBuilder();

        String horizontalBorder = "═".repeat(43);
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
                    shelfView.append("-".repeat(maxLengthType));
                } else {
                    String cardText = card.toString();
                    String visibleCardText = cardText.replaceAll("\u001B\\[[;\\d]*m", "");
                    int visibleCardLength = visibleCardText.length();
                    if (visibleCardLength % 2 == 0) {
                        shelfView.append(" ".repeat((8 - visibleCardText.length())/2)).append(cardText).append(" ".repeat((8 - visibleCardText.length())/2));
                    } else {
                        shelfView.append(" ".repeat((8 - visibleCardText.length())/2)).append(cardText).append(" ".repeat((int) Math.ceil((double)(8 - visibleCardText.length()) / 2)));
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

    /**
     * print the logo
     * @param out is the output PrintStream
     */
    public static void printLogo(PrintStream out) {
        out.println("""
                                                                                :!?YJ~                                                                              \s
                                                                               ~P&&BG#@&~                                                                            \s
                            7GBBBB~      JBBB#B:                              7B@&^:J5B@@^       .                      .^::     ^YGGPP!                             \s
                            .GB@@&~      ?@@@@Y.                             :5&@7 !#@@@@7   J##&#Y                     .#@G.  ~B&5^..^B#^                           \s
                             .5&@#.      ^&&@G                               ^5&@7  !5BG7     ?#&G:                      G@Y  7&&~ ^GB!:@#                           \s
                              5&&@5      #@&@5                                5#@&~           ~B&G.                      P&Y :G@Y  B@&##@5                           \s
                              5#&&@7    5@&&@5      .^!J5GG^   .5##G7         .P&@@B~         ~B&G                       P&Y !G@7  .7YYJ~                            \s
                              Y#&7#&:  7@GY#@5      :YG&@@5    !&@#~            J&@@@#J.      ~B&B^~7?7~:                P&Y ^5&Y       ^JJ.                         \s
                              YB&^^#B :&#:J#@5         :P&@?  7&@B:              .?B&@@&5:    ~B&&&&&&&&BJ       .:.     5&J  !B&:     .P@@Y      ...                \s
                              YB&! !&PB&~ YB&5          .Y#@G5&@P.                  ^P&&&&?   ~G&&&Y^:!#&&~   ^YP5YYP5:  5&J .~B@B7J57  .!!.   .?P5YYPG!             \s
                              JB&?  ?&&?  5B&Y           .?G&&&J.              ..     :#&&&~  ~G&&!    J&&^  ?#B^   .P&: Y&J :Y?P&&7~~ !GB#?  !G#!.   ?&?            \s
                              JG&Y  Y&&?  PB&J    .^!!:   !5&#7             :5GBG5!    J&&B5  ~P&B.    Y#G. ~B&GP555Y5G~ Y#?    .B&7   .P##: :5#B5555YYGJ            \s
                              ?G&5 .7??7 .PB&J  :JB#&&&? .Y##~              B####BPJ   Y##G5  ~P#G.    5#?  ?B#^   .!7^  J#?     Y#B   .5#B: ~P#?    ~7~             \s
                              ?P#G       ^PB&J  ?5#BPGG?!P#G:               G###55P?.:Y##GY~  ^5#P    :P#7  !B#?   7###! ?B7     Y#B.   YBB: :P#P   :B#&5            \s
                            .75###5.    ^5B##B! :JG#GPGB##5.                .YGBBBBGB##BP?^  .YGBB!  .5BBB~  7BBP??PBB5:.Y#J    ~G#B^  :PBB^  ^PBGJ?5B#B!            \s
                            .~~!!!!:    ^!!!!!!. .~J5P5Y?~.                   .:~7?JJ?!~^.   .!!!!~  .!!!!~   .^7??7!:  .!!!.   ~!~!^  .~~~:    :!???!^.             \s
                """);
    }
}
