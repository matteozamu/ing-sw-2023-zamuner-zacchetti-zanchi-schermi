package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public int MAX_PLAYER;
    private String id;
    private List<Player> players;
    private Board board;
    private Player firstPlayer;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
    private int numberOfPlayers;
    private List<ObjectCard> cardContainer;

    public Game() {
        this.MAX_PLAYER = 4;
        this.players = new ArrayList<>();
    }

    public ArrayList<PersonalGoal> createPersonalGoals(){
        Random rand = new Random();
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        ObjectCardType[] objTypes = ObjectCardType.values();

        for (int i = 0; i < 6; i++){
            PersonalGoal pg = new PersonalGoal(rand.nextInt(6), rand.nextInt(5), objTypes[i]);
            goals.add(pg);
        }

        return goals;
    }

    public void startGame() {
        //TODO verificare come creare un'istanza di una classe partendo da una stringa

    }
    public void endGame() {
    }

    public Player nextPlayer() {
        return null;
    }

    public boolean isUsernameTaken(String username) throws NullPointerException{
        if (username == null){
            throw new NullPointerException("Username is null");
        }
        for (Player p : players){
            if (p.getName().equals(username)){
                return true;
            }
        }
        return false;
    }

    // add player in the list
    //so che name != null
    public boolean addPlayer(String name) {
        if (players.size() < MAX_PLAYER){
            Shelf shelf = new Shelf();
            PersonalGoalCard pg = new PersonalGoalCard(this.createPersonalGoals());
            Player p = new Player(name, 0, shelf, pg);
            players.add(p);
            return true;
        } else {
            return false;
        }
    }

    public int calculatePoints(Player p) {
        return 0;
    }

    private ObjectCard getRandomObjectCard() {
        return null;
    }

    public void fillBoard(){
        // for per scegliere le coordinate, escludere alcuni valori
//        Coordinate c = new Coordinate(x, y);
//        this.board.getGrid().put(c, getRandomObjectCard());
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                '}';
    }
}