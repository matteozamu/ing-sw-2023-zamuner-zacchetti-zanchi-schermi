package it.polimi.ingsw.model;

import java.util.*;

public class Game {
    public int MAX_PLAYER;
    private UUID id;
    private List<Player> players;
    private Board board;
    private Player firstPlayer;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
    private int numberOfPlayers;
    private List<ObjectCard> cardContainer;
    private List<ObjectCard> temporaryCard;

    public Game() {
        this.id = UUID.randomUUID();
        this.MAX_PLAYER = 4;
        this.players = new ArrayList<>();
        this.temporaryCard = new ArrayList<>();
        this.board = new Board();

        this.cardContainer = new ArrayList<>();
        for(int i=0; i<6; i++){
            List<ObjectCardType> types = List.of(ObjectCardType.values());
            for(int j=0; j<22; j++){
                this.cardContainer.add(new ObjectCard(types.get(i), j));
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<ObjectCard> getCardContainer() {
        return cardContainer;
    }

    public List<ObjectCard> getTemporaryCard() {
        return temporaryCard;
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
        this.temporaryCard.clear();
        return null;
    }

    // check if the username is already used by another player
    public boolean isUsernameAvailable(String username) throws NullPointerException {
        if (username == null) throw new NullPointerException("Username is null"); // inutile
        for (Player p : players){
            if (p.getName().equals(username)) return false;
        }
        return true;
    }

    // add a new player to the game

    /**
     *
     * @param username
     * @return
     * @throws NullPointerException
     * @throws IllegalStateException
     */
    public boolean addPlayer(String username) throws NullPointerException, IllegalStateException {
        // if (username == null) throw new NullPointerException("Username is null");
        if(!this.isUsernameAvailable(username)) throw new IllegalStateException("Username " + username + " already in use");
        if (players.size() < MAX_PLAYER) {
            Shelf shelf = new Shelf();
            PersonalGoalCard pg = new PersonalGoalCard(this.createPersonalGoals());
            Player p = new Player(username, shelf, pg);
            players.add(p);
            return true;
        } else {
            throw new IllegalStateException("Max number of players reached");
        }
    }

    public int calculatePoints(Player p) {
        return 0;
    }

    public ObjectCard getRandomAvailableObjectCard() {
        Random r = new Random();
        return this.cardContainer.get(r.nextInt(this.cardContainer.size()-1));
    }

    public void fillBoard(){
        Coordinate c;
        try {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(5 - row, -5 + col);
                    this.board.createCell(c, getRandomAvailableObjectCard());
                }
            }
            for (int row = 5 - 1; row >= 1; row--) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(-5 + row, -5 + col);
                    this.board.createCell(c, getRandomAvailableObjectCard());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "players=" + players;
    }
}