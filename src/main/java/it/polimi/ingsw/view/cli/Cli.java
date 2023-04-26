package it.polimi.ingsw.view.cli;


import it.polimi.ingsw.control.ControllerClient;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.observer.ViewObservable;
import it.polimi.ingsw.view.View;

import java.util.*;

import static java.lang.System.out;


/**
 * This class offers a User Interface to the user via terminal. It is an implementation of the {@link View}.
 */
public class Cli extends ViewObservable implements View {

    public void init() {
        out.println("███╗   ███╗██╗   ██╗    ███████╗██╗  ██╗███████╗██╗     ███████╗██╗███████╗");
        out.println("████╗ ████║╚██╗ ██╔╝    ██╔════╝██║  ██║██╔════╝██║     ██╔════╝██║██╔════╝");
        out.println("██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  ");
        out.println("██╔████╔██║ ╚████╔╝     ███████╗███████║█████╗  ██║     █████╗  ██║█████╗  ");
        out.println("██║╚██╔╝██║  ╚██╔╝      ╚════██║██╔══██║██╔══╝  ██║     ██╔══╝  ██║██╔══╝  ");
        out.println("██║ ╚═╝ ██║   ██║       ███████║██║  ██║███████╗███████╗██║     ██║███████╗");
        out.println("╚═╝     ╚═╝   ╚═╝       ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝╚═╝     ╚═╝╚══════╝");
        askServerInfo();
    }

    public void askServerInfo() { // si puo aggiungere Execution exception se lo stream di input è interrotto
        Map<String, String> serverInfo = new HashMap<>();
        String defaultAddress = "localhost";
        String defaultPort = "16847";
        boolean validInput;
        Scanner s = new Scanner(System.in);

        do {
            out.print("Enter the server address [" + defaultAddress + "]: ");

            String address = s.nextLine();

            if (address.equals("")) {
                serverInfo.put("address", defaultAddress);
                validInput = true;
            } else if (ControllerClient.isValidIpAddress(address)) {
                serverInfo.put("address", address);
                validInput = true;
            } else {
                out.println("Invalid address!");
                // clearCli();
                validInput = false;
            }
        } while (!validInput);

        do {
            out.print("Enter the server port [" + defaultPort + "]: ");
            String port = s.nextLine();

            if (port.equals("")) {
                serverInfo.put("port", defaultPort);
                validInput = true;
            } else {
                if (ControllerClient.isValidPort(port)) {
                    serverInfo.put("port", port);
                    validInput = true;
                } else {
                    out.println("Invalid port!");
                    validInput = false;
                }
            }
        } while (!validInput);

        notifyObserver(obs -> obs.onUpdateServerInfo(serverInfo));

    }

    /**
     * Shows the login result.
     * On login fail, the program is terminated immediatly.
     *
     * @param nicknameAccepted     indicates if the chosen nickname has been accepted.
     * @param connectionSuccessful indicates if the connection has been successful.
     * @param nickname             the nickname of the player to be greeted.
     */
    @Override
    public void showLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname) {
//        clearCli();

        if (nicknameAccepted && connectionSuccessful) {
            out.println("Hi, " + nickname + "! You connected to the server.");
        } else if (connectionSuccessful) {
            askUsername();
        } else if (nicknameAccepted) {
            out.println("Max players reached. Connection refused.");
            out.println("EXIT.");

            System.exit(1);
        } else {
            showErrorAndExit("Could not contact server.");
        }
    }

    /**
     * ask the username to the user
     */
    @Override
    public void askUsername() {
        Scanner s = new Scanner(System.in);
        String username = null;
        do {
            out.println("Enter your username: ");
            username = s.nextLine();
        } while (username == null);

        String finalUsername = username;
        notifyObserver(obs -> obs.onUpdateUsername(finalUsername));
    }

    /**
     * this method ask the user to choose 1, 2 or 3 Object Card
     * the usern will enter the coordinate of the Object Card
     */
    @Override
    public void askObjCard() {
        List<Coordinate> coordinates = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        int i = 0;

        out.println("Choose your object card, you can choose 1, 2 or 3 of them.");

        while (i < 3) {
            out.println("Enter the coordinates separated by a space:");
            int x = s.nextInt();
            int y = s.nextInt();

            Coordinate coordinate = new Coordinate(x, y);
            coordinates.add(coordinate);
            i++;

            out.print("Do you want to choose other coordinates? (y/n): ");
            String answer = s.next();
            if (!answer.equalsIgnoreCase("y")) {
                break;
            }
        }

        notifyObserver(obs -> obs.onUpdateObjCard(coordinates));
    }


    /**
     * Shows an error message and exit.
     *
     * @param error the error to be shown.
     */
    @Override
    public void showErrorAndExit(String error) {

        out.println("\nERROR: " + error);
        out.println("EXIT.");

        System.exit(1);
    }

}