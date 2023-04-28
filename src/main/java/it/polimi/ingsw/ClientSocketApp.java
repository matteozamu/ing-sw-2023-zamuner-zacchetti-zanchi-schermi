package it.polimi.ingsw;

import it.polimi.ingsw.control.ControllerClient;
import it.polimi.ingsw.view.cli.Cli;

public class ClientSocketApp {
    public static void main(String[] args) {
        Cli view = new Cli();
        ControllerClient clientController = new ControllerClient(view);
        view.addObserver(clientController);
        view.init();
    }
}
