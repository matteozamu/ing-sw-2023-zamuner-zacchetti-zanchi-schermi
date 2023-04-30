package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.Cli;

public class ClientMain {
    public static void main(String[] args) {

//        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
        new Cli().start();
//        } else {
//            Application.launch(Gui.class);
//        }
    }
}