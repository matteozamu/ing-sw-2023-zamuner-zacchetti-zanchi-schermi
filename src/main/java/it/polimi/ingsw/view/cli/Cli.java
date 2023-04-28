package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.client.DisconnectionListener;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.utility.MessageBuilder;
import it.polimi.ingsw.utility.ServerAddressValidator;

import java.util.List;
import java.util.Scanner;

public class Cli extends ClientGameManager implements DisconnectionListener {
    private Scanner in;
    private AdrenalinePrintStream out;

    public Cli() {
        super();

        this.in = new Scanner(System.in);
        this.out = new AdrenalinePrintStream();
    }

    /**
     * Starts the view.cli
     */
    public void start() {
        printLogo();
        doConnection();
    }

    /**
     * Prints Adrenaline Logo
     */
    private void printLogo() {
        String adrenalineLogo = "             _____   _____   _______ _   _            _       _  _   _  _______\n" +
                "      /\\    |  __ \\ |  __ \\ |  ____/| \\ | |    /\\    | |     | || \\ | ||  ____/\n" +
                "     /  \\   | |  | || |__) || |__   |  \\| |   /  \\   | |     | ||  \\| || |__   \n" +
                "    / /\\ \\  | |  | ||  _  / |  __|  | . ` |  / /\\ \\  | |     | || . ` ||  __|  \n" +
                "   / /__\\ \\ | |__| || | \\ \\ | |_____| |\\  | / /__\\ \\ | |____ | || |\\  || |_____\n" +
                "  /_/|_____\\|_____/ |_|  \\_\\|______/|_| \\_|/_/|_____\\|______\\|_||_| \\_||______/\n\n" +
                "Welcome to Adrenaline Board Game made by Giorgio Piazza, Francesco Piro and Lorenzo Tosetti.\n" +
                "Before starting playing you need to setup some things:\n";

        out.println(adrenalineLogo);
    }

    private boolean promptInputError(boolean firstError, String errorMessage) {
        out.print(AnsiCode.CLEAR_LINE);
        if (!firstError) {
            out.print(AnsiCode.CLEAR_LINE);
        }

        out.println(errorMessage);
        return false;
    }

    /**
     * Prompts a generic error
     *
     * @param errorMessage the error message
     * @param close        {@code true} if you want to close the shell
     */
    private void promptError(String errorMessage, boolean close) {
        CliPrinter.clearConsole(out);

        out.println("ERROR: " + errorMessage);

        if (close) {
            out.println("\nPress ENTER to exit");
            in.nextLine();
            System.exit(1);
        }
    }

    /**
     * Asks the username
     */
    private String askUsername() {
        boolean firstError = true;
        String username = null;

        out.println("Enter your username:");

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                final String currentUsername = in.nextLine();

                if (currentUsername.equals("")) {
                    firstError = promptInputError(firstError, "Invalid username!");
                } else {
                    username = currentUsername;
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, INVALID_STRING);
            }
        } while (username == null);

        CliPrinter.clearConsole(out);
        return username;
    }

    /**
     * Asks the connection type
     */
    private void doConnection() {
        boolean validConnection = false;
        boolean firstError = true;
        int connection = -1;

        String username = askUsername();

        out.printf("Hi %s!%n", username);
        out.println("\nEnter the connection type (0 = Sockets or 1 = RMI):");

        do {
            out.print(">>> ");

            if (in.hasNextInt()) {
                connection = in.nextInt();
                in.nextLine();

                if (connection >= 0 && connection <= 1) {
                    validConnection = true;
                } else {
                    firstError = promptInputError(firstError, "Invalid selection!");
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, "Invalid integer!");
            }
        } while (!validConnection);

        CliPrinter.clearConsole(out);

        if (connection == 0) {
            out.println("You chose Socket connection\n");
        } else {
            out.println("You chose RMI connection\n");
        }

        String address = askAddress();
        out.println("\nServer Address: " + address);

        int port = askPort(connection);
        out.println("\nServer Port: " + port);

        try {
            createConnection(connection, username, address, port, this);
        } catch (Exception e) {
            promptError(e.getMessage(), true);
        }
    }

    /**
     * Asks and verify the address
     *
     * @return a verified address
     */
    private String askAddress() {
        String address;
        boolean firstError = true;

        out.println("Enter the server address (default is \"localhost\"):");

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                address = in.nextLine();

                if (address.equals("")) {
                    return "localhost";
                } else if (ServerAddressValidator.isAddressValid(address)) {
                    return address;
                } else {
                    firstError = promptInputError(firstError, "Invalid address!");
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, INVALID_STRING);
            }
        } while (true);
    }

    /**
     * Asks and verify the port
     *
     * @param connection type to set the default port
     * @return a verified port
     */
    private int askPort(int connection) {
        boolean firstError = true;

        int defaultPort = (connection == 0 ? 2727 : 7272);
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
                        firstError = promptInputError(firstError, "Invalid Port!");
                    }
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, INVALID_STRING);
            }
        } while (true);
    }

    /**
     * Send a lobby join request to the server
     */
    private void sendLobbyJoinRequest() {
        if (!sendRequest(MessageBuilder.buildGetInLobbyMessage(getClientToken(), getUsername(), false))) {
            promptError(SEND_ERROR, true);
        }
    }

    @Override
    public void connectionResponse(ConnectionResponse response) {
        CliPrinter.clearConsole(out);

        if (response.getStatus().equals(MessageStatus.ERROR)) {
            promptError(response.getMessage(), false);
            out.println();
            doConnection();
        } else {
            out.println("Connected to server with username " + getUsername());
//            sendUnusedColorsRequest();
        }
    }

    @Override
    public void loadResponse() {
        CliPrinter.clearConsole(out);

        out.println("You joined a loaded game.\nWaiting for other players!");
    }

    @Override
    public void lobbyJoinResponse(Response response) {
        CliPrinter.clearConsole(out);

        if (response.getStatus() == MessageStatus.ERROR) {
            out.println(response.getMessage());
            out.println();
//            sendUnusedColorsRequest();
        } else {
            out.println("You joined the lobby!\n\nWait for the game to start...\n");
//            askVoteMap();
        }
    }

    @Override
    public void playersLobbyUpdate(List<String> users) {
        StringBuilder players = new StringBuilder();

        for (String user : users) {
            players.append(user);
            players.append(", ");
        }

        out.println("Players in lobby: " + players.substring(0, players.length() - 2));
    }

    @Override
    public void notYourTurn(String turnOwner) {
        out.println(turnOwner + " is playing. Wait for your turn...");
    }

    @Override
    public void onDisconnection() {
        promptError("Disconnected from the server, connection expired", true);
    }
}