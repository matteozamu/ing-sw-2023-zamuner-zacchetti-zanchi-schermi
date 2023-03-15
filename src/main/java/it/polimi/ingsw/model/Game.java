package it.polimi.ingsw.model;

import java.util.List;
import java.util.Random;

public class Game {
    public int MAXPLAYER;
    private String id;
    private List<Player> players;
    private Board board;
    private Player firstPlayer;
    private CommonGoal commonGoals[];
    private Player currentPlayer;
    private int numberOfPlayers;
    private List<ObjectCard> cardContainer;


    private void createPersonalGoals(){
        Random rand = new Random();

        for (int i = 0; i < 6; i++){

            PersonalGoal p = new PersonalGoal(rand.nextInt(6), rand.nextInt(5), );
            this.goals[i] = p;
        }

        return;
    }
    public void startGame() {
    }
    public void endGame() {
    }

    public Player nextPlayer() {
        return null;
    }

    public boolean isUsernameTaken(String username) {
        for (Player p : players){
            if (p.getName().equals(username)){
                return true;
            }
        }
        return false;
    }


    //function to add player in the list
    public String insertPlayer(String name){
        if (players.size() < 4){
            Shelf shelf = new Shelf();
            PersonalGoalCard pg = new PersonalGoalCard();
            Player p = new Player(name, 0, shelf, pg);
            players.add(p);
            return "giocatore inserito con successo";
        } else {
            return "numero massimo di giocari raggiunto";
        }
    }

    public int calculatePoints(Player p) {
        return 0;
    }

    private ObjectCard getRandomObjectCard(){
        return null;
    }

    public void fillBoard(){

        // for per scegliere le coordinate, escludere alcuni valori
//        Coordinate c = new Coordinate(x, y);
//        this.board.getGrid().put(c, getRandomObjectCard());
    }

}