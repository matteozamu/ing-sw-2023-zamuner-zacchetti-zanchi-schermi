package it.polimi.ingsw.model;

public class Server {
    public static void main( String[] args ) {
        Game g = new Game();

        System.out.println(g.addPlayer("Franco"));
        System.out.println(g.addPlayer("Armando"));
        System.out.println(g.addPlayer("Renato"));
        System.out.println(g.addPlayer("Alfred"));
        System.out.println(g.addPlayer("Fritz"));
        System.out.println(g.addPlayer("Otto"));
        System.out.println(g);

    }
}
