package it.polimi.ingsw.view;

import java.util.Scanner;

public class TextualUI {

    public void createPlayer() {
        System.out.print("Write here your username: ");
        Scanner s = new Scanner(System.in);
        String input = s.next();
    }

}
