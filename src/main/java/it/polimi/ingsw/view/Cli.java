package it.polimi.ingsw.view;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.client.DisconnectionListener;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.utility.MessageBuilder;
import it.polimi.ingsw.utility.ServerAddressValidator;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Cli extends ClientGameManager implements DisconnectionListener {
    private Scanner in;
    private PrintStream out;

    public Cli() {
        super();

        this.in = new Scanner(System.in);
        this.out = new PrintStream(System.out, true);
    }

    public void start() {
        printLogo();
        doConnection();
    }

    private void printLogo() {
        out.println("███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗");
        out.println("████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝");
        out.println("██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  ");
        out.println("██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  ");
        out.println("██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝  ");
        out.println("██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗");
        out.println("╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝");
    }

    private boolean promptInputError(boolean firstError, String errorMessage) {
//        out.print(AnsiCode.CLEAR_LINE);
//        if (!firstError) {
//            out.print(AnsiCode.CLEAR_LINE);
//        }

        out.println(errorMessage);
        return false;
    }

    private void promptError(String errorMessage, boolean close) {
        out.println("ERROR: " + errorMessage);

        if (close) {
            out.println("\nPress ENTER to exit");
            in.nextLine();
            System.exit(1);
        }
    }

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

        return username;
    }

    private void doConnection() {
        boolean validConnection = false;
        boolean firstError = true;

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
    }

    private int askConnection() {
        boolean firstError = true;
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
                            firstError = promptInputError(firstError, "Invalid connection!");
                        }
                    } catch (Exception e) {
                        firstError = promptInputError(firstError, "Invalid number!");
                    }
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, INVALID_STRING);
            }
        } while (true);
    }

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
    private void addPlayerToGameRequest() {
        if (!sendRequest(MessageBuilder.buildAddPlayerToGameMessage(getClientToken(), getUsername(), false))) {
            promptError(SEND_ERROR, true);
        }
    }

    private int askNumberOfPlayers() {
        int gamePlayers = 2;
        boolean firstError = true;

        out.println("How many players in the game [2] (2 - 4)?");
        in.reset();

        do {
            out.print(">>> ");

            if (in.hasNextLine()) {
                String line = in.nextLine();

                if (line.equals("")) {
                    return gamePlayers;
                } else {
                    gamePlayers = Integer.parseInt(line);
                    if (gamePlayers >= 2 && gamePlayers <= 4) {
                        return gamePlayers;
                    } else {
                        firstError = promptInputError(firstError, "Invalid number!");
                    }
                }
            } else {
                in.nextLine();
                firstError = promptInputError(firstError, INVALID_STRING);
            }
        } while (true);
    }

    @Override
    public void connectionResponse(ConnectionResponse response) {
        if (response.getStatus().equals(MessageStatus.ERROR)) {
            promptError(response.getMessage(), false);
            out.println();
            doConnection();
        } else {
            out.println("Connected to server with username " + getUsername());
            addPlayerToGameRequest();
        }
    }

    @Override
    public void loadResponse() {
        out.println("You joined a loaded game.\nWaiting for other players!");
    }

    @Override
    public void lobbyJoinResponse(Response response) {
        if (response.getStatus() == MessageStatus.ERROR) {
            out.println(response.getMessage());
            out.println();
        } else {
            out.println("You joined the lobby!\nWait for the game to start...\n");
        }
    }

    @Override
    public void numberOfPlayersResponse(Response response) {
        int numberOfPlayers = askNumberOfPlayers();
        if (!sendRequest(MessageBuilder.buildNumberOfPlayerMessage(getClientToken(), getUsername(), numberOfPlayers))) {
            promptError(SEND_ERROR, true);
        }
    }

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

    @Override
    public void notYourTurn(String turnOwner) {
        out.println(turnOwner + " is playing. Wait for your turn...");
    }

    @Override
    public void responseError(String error) {
        promptError(error + "\n", false);
    }

    @Override
    public void firstPlayerCommunication(String username) {
        out.println("Game has started!");
        if (username.equals(getUsername())) {
            out.println("You are the first player!\n");
        } else out.println(username + " is the first player!\n");

    }

    @Override
    public void onDisconnection() {
        promptError("Disconnected from the server, connection expired", true);
    }
}