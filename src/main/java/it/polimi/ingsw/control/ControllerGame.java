package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

public class ControllerGame {
    private UUID id;
    private List<Player> players;
    private Board board;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
    private int numberOfPlayers;
    private Game game;

    public ControllerGame() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.game = new Game();
        this.numberOfPlayers = 0;
        this.commonGoals = new ArrayList<>();
    }

    /**
     * Check if the username is available
     * @param username
     * @return true if available, false if not
     * @throws NullPointerException
     */
    public boolean isUsernameAvailable(String username) throws NullPointerException {
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
            PersonalGoalCard pg = new PersonalGoalCard(game.createPersonalGoals());
            Player p = new Player(username, shelf, pg);
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

    // check userName and if it is available, add the player to the game
    public void checkAddPlayer() {

    }

    // select card from the board and check it that coordinate/card is available
    public void selectCard() {

    }

    // select the column in which you want to add your cards checking that
    // the available spaces are enough for the amount of card the player has
    public void selectColumn() {

    }

    // load your shelf with the selected card
    public void loadShelf() {

    }
}
