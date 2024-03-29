package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.client.DisconnectionListener;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.utility.MessageBuilder;
import it.polimi.ingsw.utility.ServerAddressValidator;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class is used to manage the cli
 */
public class Cli extends ClientGameManager implements DisconnectionListener {
    private Scanner in;
    private PrintStream out;

    /**
     * Constructor of a cli
     */
    public Cli() {
        super();

        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out, true);
    }

    /**
     * Method used to read the input for the reorderLimbo method
     *
     * @return the list of the index read
     */
    private static ArrayList<Integer> readLimboInput() {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] numberStrings = input.split(" ");
        for (String numberString : numberStrings) {
            try {
                int number = Integer.parseInt(numberString);
                numbers.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + numberString);
            }
        }

        return numbers;
    }

    /**
     * Clear the console
     */
    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start a cli client
     */
    public void start() {
        CliVisual.printLogo(out);
        doConnection();
    }

    /**
     * Print that there are no games available
     */
    public void noGameAvailable() {
        out.println("No game available");
    }

    /**
     * Show an error message
     *
     * @param errorMessage the error to show
     */
    private void promptInputError(String errorMessage) {
        out.println(errorMessage);
    }

    /**
     * Show an error message and close the program
     *
     * @param errorMessage the error message to show
     * @param close        if true, the program will close
     */
    private void promptError(String errorMessage, boolean close) {
        out.println("ERROR: " + errorMessage);

        if (close) {
            out.println("\nPress ENTER to exit");
            in.nextLine();
            System.exit(1);
        }
    }

    /**
     * Ask the user to insert the name
     *
     * @return the username inserted
     */
    private String askUsername() {
        String username = null;

        out.println("Enter your username:");

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                final String currentUsername = in.nextLine();

                if (currentUsername.equals("")) {
                    promptInputError("Invalid username!");
                } else {
                    username = currentUsername;
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (username == null);

        clearConsole();

        return username;
    }

    /**
     * Try to connect to the server
     */
    private void doConnection() {

        String username = askUsername();

        out.printf("Hi %s!%n", username);
        int connection = askConnection();

        if (connection == 0) {
            out.println("You chose Socket connection\n");
        } else {
            out.println("You chose RMI connection\n");
        }

        String address = askAddress();
        out.println("Server Address: " + address);

        int port = askPort(connection);
        out.println("Server Port: " + port);

        try {
            createConnection(connection, username, address, port, this);
        } catch (Exception e) {
            promptError(e.getMessage(), true);
        }

        clearConsole();
    }

    /**
     * Ask the user to insert the connection type
     *
     * @return the connection type
     */
    private int askConnection() {
        int connection = -1;

        out.println("\nEnter the connection type (0 = Sockets or 1 = RMI) (default is \"Sockets\"):");
        in.reset();

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return 0;
                } else {
                    try {
                        connection = Integer.parseInt(line);
                        if (connection == 0 || connection == 1) {
                            return connection;
                        } else {
                            promptInputError("Invalid connection!");
                        }
                    } catch (Exception e) {
                        promptInputError("Invalid number!");
                    }
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (true);
    }

    /**
     * Asks the user for the server address
     *
     * @return the server address
     */
    private String askAddress() {
        String address;

        out.println("Enter the server address (default is \"localhost\"):");

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                address = in.nextLine();

                if (address.equals("")) {
                    return "localhost";
                } else if (ServerAddressValidator.isAddressValid(address)) {
                    clearConsole();
                    return address;
                } else {
                    promptInputError("Invalid address!");
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (true);

    }

    /**
     * Asks the user for the server port
     *
     * @param connection the connection type
     * @return the server port
     */
    private int askPort(int connection) {

        int defaultPort = (connection == 0 ? 6666 : 7777);
        out.println("\nEnter the server port (default " + defaultPort + "):");
        in.reset();

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return defaultPort;
                } else {
                    if (ServerAddressValidator.isPortValid(line)) {
                        return Integer.parseInt(line);
                    } else {
                        promptInputError("Invalid Port!");
                    }
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (true);
    }

    /**
     * Send a lobby join request to the server
     */
    public void addPlayerToGameRequest() {
        if (!sendRequest(MessageBuilder.buildAddPlayerToGameMessage(getClientToken(), getUsername(), false))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Ask the user how many players he wants to play with
     *
     * @return the number of players
     */
    private int askNumberOfPlayers() {
        int gamePlayers = 2;

        out.println("How many players in the game? (2 to 4) (default is 2)");
        in.reset();

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return gamePlayers;
                } else {
                    try {
                        gamePlayers = Integer.parseInt(line);

                        if (gamePlayers >= 2 && gamePlayers <= 4) {
                            return gamePlayers;
                        } else {
                            promptInputError("Invalid number!");
                        }

                    } catch (Exception e) {
                        promptInputError("Invalid number!");
                    }
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (true);
    }

    /**
     * If the user connects to the server, he join the lobby
     *
     * @param response the response from the server
     */
    @Override
    public void connectionResponse(ConnectionResponse response) {
        if (response.getStatus().equals(MessageStatus.ERROR)) {
            promptError(response.getMessage(), false);
            out.println();
            doConnection();
        } else {
            out.println("Connected to server with username " + getUsername());
        }
    }

    /**
     * If the user joins a loaded game, he waits for the game to start
     */
    @Override
    public void loadResponse() {
        out.println("You joined a loaded game.\nWaiting for other players!");
    }

    /**
     * If the user joins the lobby, he waits for the game to start
     *
     * @param response the response from the server
     */
    @Override
    public void lobbyJoinResponse(Response response) {
        if (response.getStatus() == MessageStatus.ERROR) {
            out.println(response.getMessage());
            out.println();
        } else {
            out.println("You joined the lobby!\nWait for the game to start...\n");
        }
    }

    /**
     * Send a message to the server with the number of players
     *
     * @param response the response from the server
     */
    @Override
    public void numberOfPlayersRequest(Response response) {
        int numberOfPlayers = askNumberOfPlayers();
        String gameName = askGameName();
        if (!sendRequest(MessageBuilder.buildNumberOfPlayerMessage(getClientToken(), getUsername(), numberOfPlayers, gameName))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Asks the user for a game name for the game they created
     *
     * @return the game name
     */
    private String askGameName() {
        String gameName = null;

        out.println("Enter a name for the game:");

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                final String name = in.nextLine();

                if (name.equals("")) {
                    promptInputError("Invalid name!");
                } else {
                    gameName = name;
                }
            } else {
                in.nextLine();
                promptInputError(INVALID_STRING);
            }
        } while (gameName == null);

        return gameName;
    }

    /**
     * Shows the players in the lobby
     *
     * @param users list of users in the lobby
     */
    @Override
    public void playersWaitingUpdate(List<String> users) {
        StringBuilder players = new StringBuilder();

        for (String user : users) {
            players.append(user);
            players.append(", ");
        }

        if (users.size() == 1)
            out.println("There is " + users.size() + " player waiting: " + players.substring(0, players.length() - 2));
        else
            out.println("There are " + users.size() + " players waiting: " + players.substring(0, players.length() - 2));

        out.println();
    }

    /**
     * Shows the game state
     */
    @Override
    public void gameStateUpdate() {
        List<CommonGoal> cg = getGameSerialized().getCommonGoals();
        out.println("The common goal cards are: \n" + cg.get(0).getDescription() + "\n" + cg.get(0).getCardView() + "\n\n" + cg.get(1).getDescription() + "\n" + cg.get(1).getCardView());

        CliVisual.printPersonalGoalCards(out, getGameSerialized());
        out.println();
        CliVisual.printBoard(out, getGameSerialized());
        out.println();
        CliVisual.printShelf(out, getGameSerialized());
        out.println();
        CliVisual.printScore(out, getGameSerialized());
    }

    /**
     * Tells the other players who are playing
     *
     * @param turnOwner the player who is playing
     */
    @Override
    public void notYourTurn(String turnOwner) {
        out.println(turnOwner + " is playing. Wait for your turn...");
    }

    /**
     * Handle an error message
     *
     * @param error the error message
     */
    @Override
    public void responseError(String error) {
        promptError(error + "\n", false);
    }

    /**
     * Method used fot the first turn
     *
     * @param username first player username
     * @param cg       list of common goals
     */
    @Override
    public void firstPlayerCommunication(String username, List<CommonGoal> cg) {
        out.println("Game has started!");

        if (username.equals(getUsername())) {
            out.println("You are the first player!\n");
        } else out.println(username + " is the first player!\n");

    }

    /**
     * Print a list of game in which the user can log in
     *
     * @param games the list of games
     */
    @Override
    public void chooseGameToJoin(List<ControllerGame> games) {
        Integer choose = -1;
        out.println();

        if (games.isEmpty()) {
            out.println("There are no games to join!");
            return;
        }

        out.println("Choose the game to join:");

        for (int i = 0; i < games.size(); i++) {
            out.print("\t" + (i) + " - " + games.get(i).getGame().getGameName() + " - Players: ");
            String playerNames = games.get(i).getGame().getPlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));
            out.println(playerNames);
        }


        do {
            choose = readInt(0, games.size() - 1);
            if (choose == null) {
                promptInputError("Not valid input!");
            }
        } while (choose == null);

        if (!sendRequest(MessageBuilder.buildJoinGameRequest(getClientToken(), getUsername(), games.get(choose).getId()))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Method used to show the possible actions
     *
     * @param possibleActions list of possible actions
     */
    @Override
    public void displayActions(List<PossibleAction> possibleActions) {
        Integer choose = -1;
        out.println();
        out.println("Choose the next move:");

        for (int i = 0; i < possibleActions.size(); i++) {
            out.println("\t" + (i) + " - " + possibleActions.get(i).getDescription());
        }

        do {
            choose = readInt(0, possibleActions.size() - 1);
            if (choose == null) {
                promptInputError("Not valid input!");
            }
        } while (choose == null);

        doAction(possibleActions.get(choose));
    }

    /**
     * Read an integer in the desired interval
     *
     * @param minVal minimum value
     * @param maxVal maximum value
     * @return the integer read
     */
    private Integer readInt(int minVal, int maxVal) {
        boolean accepted = false;
        Integer choose = Integer.MIN_VALUE;

        do {
            out.print(">>> ");
            String line = in.nextLine();

            if (line.equals("CANCEL")) {
                return null;
            }

            try {
                choose = Integer.valueOf(line);

                if (choose >= minVal && choose <= maxVal) {
                    accepted = true;
                } else {
                    promptInputError("Not valid input!");
                }
            } catch (NumberFormatException e) {
                promptInputError("Not valid number!");
            }
        } while (!accepted);

        return choose;
    }

    /**
     * Print a joining game message
     */
    @Override
    public void joinGame() {
        if (!sendRequest(MessageBuilder.buildListGameRequest(getUsername(), getClientToken()))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Print a creation game message
     */
    @Override
    public void createGame() {
        if (!sendRequest(MessageBuilder.buildCreateGameRequest(getUsername(), getClientToken()))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Pick an object card from the board
     */
    @Override
    public void pickBoardCard() {
        Board board = getGameSerialized().getBoard();
        ObjectCard objectCard;
        boolean validCard = false;
        Coordinate coordinate;

        out.println("Write the coordinate of the card (0,0 is the centre):");
        do {
            coordinate = readCoordinate();
            if (coordinate == null) {
                cancelAction(String.valueOf(PossibleAction.CANCEL));
                return;
            }
            objectCard = board.getGrid().get(coordinate);
            if (objectCard != null) validCard = true;
        } while (!validCard);

        if (!sendRequest(MessageBuilder.buildPickObjectCardRequest(getPlayer(), getClientToken(), coordinate))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Method used to cancel a previous action
     *
     * @param message the message to show
     */
    private void cancelAction(String message) {
        CliVisual.clearConsole(out);
        out.println(message);
        makeMove();
    }

    /**
     * Method to parse a Coordinate from the input
     *
     * @return the Coordinate read
     */
    private Coordinate readCoordinate() {
        boolean accepted = false;
        String stringCoordinate;
        Coordinate coordinate = null;

        do {
            out.print(">>> ");
            stringCoordinate = in.nextLine();

            if (stringCoordinate.equals("CANCEL")) return null;

            try {
                String[] parts = stringCoordinate.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                coordinate = new Coordinate(row, col);
                accepted = true;
            } catch (Exception e) {
                promptInputError("Invalid Coordinate");
            }
        } while (!accepted);

        return coordinate;
    }

    /**
     * Method used to reorder the cards in the limbo
     */
    @Override
    public void reorderLimbo() {
        out.println("Choose the order of the cards in the limbo:");
        ArrayList<Integer> limboOrder = readLimboInput();

        if (!sendRequest(MessageBuilder.buildReorderLimboRequest(getUsername(), getClientToken(), limboOrder))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Print the limbo
     */
    @Override
    public void printLimbo() {
        CliVisual.printLimbo(out, getGameSerialized());
    }

    @Override
    public void deleteLimbo() {
        if (!sendRequest(MessageBuilder.buildDeleteLimboRequest(getUsername(), getClientToken()))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Send a message to the server asking the available columns
     */
    @Override
    public void chooseColumn() {
        out.println("Choose the column you want to load:");
        Integer column;

        do {
            column = readInt(0, 4);
            if (column == null) {
                cancelAction(String.valueOf(PossibleAction.CANCEL));
                return;
            }
        } while (column == null);

        if (!sendRequest(MessageBuilder.buildLoadShelfRequest(getClientToken(), getUsername(), column))) {
            promptError(SEND_ERROR, true);
        }
    }

    /**
     * Shows a player their personal goal
     */
    @Override
    public void showPersonalGoal() {
        CliVisual.printPersonalGoalCards(out, getGameSerialized());
    }

    @Override
    public void cancelAction() {
        out.println("Action canceled");
    }

    /**
     * Shows a player their shelf
     */
    @Override
    public void showShelf() {
        CliVisual.printShelf(out, getGameSerialized());
        int nameLength = getUsername().length();

        out.print("█".repeat((nameLength % 2 == 0 ? 22 : 21) - nameLength / 2));
        out.print(" " + getUsername() + " ");
        out.println("█".repeat(22 - nameLength / 2));
    }

    /**
     * Method used to print the winner
     *
     * @param gameSerialized the game serialized
     */
    @Override
    public void printWinner(GameSerialized gameSerialized) {
        for (Player p : gameSerialized.getPlayers()) {
            if (getUsername().equals(p.getName()) && p.isWinner()) {
                out.println("You are the winner!");
            } else if (p.isWinner()) {
                out.println("You are not the winner, " + (p.isWinner() ? p.getName() + " is the winner" : ("")));
            }
        }

        out.println("Press any key to exit...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();

        System.exit(0);
    }

    /**
     * Method used to print the end game
     *
     * @param message message to print
     */
    @Override
    public void printEndGame(String message) {
        out.println(message);
    }

    /**
     * Method used to print that a player has disconnected
     *
     * @param player is the username that disconnected
     */
    @Override
    public void onPlayerDisconnection(String player) {
        out.println(player + " disconnected from the game.");
    }

    /**
     * Method used to print that a player has reconnected
     *
     * @param message is the message to show
     */
    @Override
    public void onPlayerReconnection(String message) {
        out.println(message);
    }

    /**
     * Show a disconnection message
     */
    @Override
    public void onDisconnection() {
        promptError("Disconnected from the server, connection expired", true);
    }
}