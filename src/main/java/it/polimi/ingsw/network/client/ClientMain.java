package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.cli.Cli;
import it.polimi.ingsw.view.gui.Gui;
import javafx.application.Application;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
//        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
//            new Cli().start();
//        } else {
//            Application.launch(Gui.class);
//        }
        Integer value = readInt(0, 1);
        if (value.equals(0)) {
            new Cli().start();
        } else {
            Application.launch(Gui.class);
        }
    }

    private static Integer readInt(int minVal, int maxVal) {
        Scanner myObj = new Scanner(System.in);
        Integer choose = null;
        boolean accepted = false;

        do {
            System.out.print("Choose:\n\t- 0 for the CLI \n\t- 1 for the GUI\n");
            System.out.print(">>> ");
            String line = myObj.nextLine();

            try {
                choose = Integer.valueOf(line);

                if (choose >= minVal && choose <= maxVal) {
                    accepted = true;
                } else System.out.println("Not valid number!");
            } catch (NumberFormatException e) {
                System.out.println("Not valid input!");
            }
        } while (!accepted || choose == null);

        return choose;
    }
}