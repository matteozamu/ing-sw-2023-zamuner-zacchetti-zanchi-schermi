package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControllerGame {
    private UUID id;
    private List<Player> players;
    private Board board;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
    private int numberOfPlayers;
    private Game game;
    private ObjectCard[] limbo;

    public ControllerGame() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.game = new Game();
        this.numberOfPlayers = 0;
        this.commonGoals = new ArrayList<>();
        this.limbo = new ObjectCard[3];
    }

    /**
     * Check if the username is available
     * @param username
     * @return true if available, false if not
     * @throws NullPointerException
     */
    public boolean isUsernameAvailable(String username) throws NullPointerException { //faccio controllo da view
        if (username == null) throw new NullPointerException("Username is null");
        for (Player p : players){
            if (p.getName().equals(username)) return false;
        }
        return true;
    }

    /**
     * Add a new player to the game
     * @param username
     * @return true if successful, false otherwise
     * @throws NullPointerException
     * @throws IllegalStateException
     */
    public boolean addPlayer(String username) throws NullPointerException, IllegalStateException {
        // if (username == null) throw new NullPointerException("Username is null");
        if(!this.isUsernameAvailable(username)) throw new IllegalStateException("Username " + username + " already in use");
        if (players.size() < game.MAX_PLAYER) {
            Shelf shelf = new Shelf();
            //TODO assegno una carta personale pescata dal mazzo, quindi non ne creo una nuova
            //PersonalGoalCard pg = new PersonalGoalCard(game.createPersonalGoals());
            Player p = new Player(username, shelf, null /*pg*/);
            players.add(p);
            this.numberOfPlayers++;
            return true;
        } else {
            throw new IllegalStateException("Max number of players reached");
        }
    }

    public Player nextPlayer() {
        return null;
    }

    /**
     * fill the board with objectCards based on the number of player
     */
    // TODO parametrizzare sul numero di giocatori
    public void fillBoard(){
        Coordinate c;
        try {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(5 - row, -5 + col);
                    this.board.createCell(c, game.getRandomAvailableObjectCard());
                }
            }
            for (int row = 5 - 1; row >= 1; row--) {
                for (int col = 1; col < 2 * row; col++) {
                    c = new Coordinate(-5 + row, -5 + col);
                    this.board.createCell(c, game.getRandomAvailableObjectCard());
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }



    //update function, funzione update vista dal prof
    //ipotizzo che dalla view arrivi una stringa Usarname non nulla
    //ricopio la funzione con observer e observable //TODO da sostituire
//    public String update(Observable o, String username) {   // si può ritornare una stringa alla view???
//        if (game.isUsernameAvailable(username)) {
//            game.addPlayer(username);
//            return "Utente inserito";
//        } else {
//            return "Nome non disponibile";
//        }
//    }

    // select the column in which you want to add your cards checking that
    // the available spaces are enough for the amount of card the player has
    public void selectColumn() {

    }

    // load your shelf with the selected card
    public void loadShelf() {

    }


    // method used during the game
    //si puo fare una modifica che non rimuova la coordinata della cella ma setti il contenuto a null
    /**
     * pick the ObjectCard from the board (if available)
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return the ObjectCard with that Coordinate
     */
    public ObjectCard pickObjectCard(Coordinate coordinate) {
        if(isAvailable(coordinate)) {
            return board.removeObjectCard(coordinate);
        } else {
            return null;
        }
    }

    /**
     * check if the ObjectCard clicked by user is available, so if it has at least one side free
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return true if this ObjectCard is available
     */
    private boolean isAvailable(Coordinate coordinate) {
        return board.isEmptyUp(coordinate) || board.isEmptyDown(coordinate) || board.isEmptyRight(coordinate) || board.isEmptyLeft(coordinate);
    }

    /**
     *
     * @param limbo is the limbo zone where the objectCard selected from the board are put before beiing putted in the shelf
     * @param objectCard is the objectCard selected
     * @throws NullPointerException if the objectCard is null (this case shouldn't happen)
     */
    public void addObjectCardToLimbo(ObjectCard[] limbo, ObjectCard objectCard) throws NullPointerException{
        if (objectCard == null){
            throw new NullPointerException("selezionare una tessera oggetto");
        }
        int i = 0;
        while (limbo[i] != null){
            i++;
        }
        if (i==4){
            System.out.println("limbo pieno");
            return;
        }
        limbo[i] = objectCard;
    }
}

