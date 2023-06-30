package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;
import javafx.application.Application;

/**
 * Main class of the client.
 */
public class ClientMain {

    /**
     * Starts the client.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("cli")) {
            new Cli().start();
        } else {
            Application.launch(Gui.class);
        }
    }
}