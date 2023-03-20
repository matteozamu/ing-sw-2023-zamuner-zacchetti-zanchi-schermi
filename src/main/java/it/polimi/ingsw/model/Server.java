package it.polimi.ingsw.model;

public class Server {
    public static void main( String[] args ) {
        Game g = new Game();

        try{
            System.out.println(g.addPlayer("Jolie"));
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(g);
    }
}
