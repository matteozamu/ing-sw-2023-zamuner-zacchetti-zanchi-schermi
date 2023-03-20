package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {
    public static void main( String[] args ) {
        Game g = new Game();

        try{
            g.addPlayer("Jolie");
        }catch (Exception e){
            e.printStackTrace();
        }

//        for(int i=0; i<6*22; i++){
//            System.out.println(g.getCardContainer().get(i));
//        }
//        System.out.println(g.getRandomAvailableObjectCard());

        g.fillBoard();

        System.out.println(g.getBoard().toString());

    }
}
