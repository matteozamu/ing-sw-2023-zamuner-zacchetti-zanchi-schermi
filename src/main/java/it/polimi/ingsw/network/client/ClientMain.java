package it.polimi.ingsw.network.client;

import view.cli.Cli;

public class ClientMain {
    public static void main(String[] args) {
        new Cli().start();

//        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
//            new Cli().start();
//        } else {
//            Application.launch(Gui.class);
//        }
    }
}