package it.polimi.ingsw.control;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static it.polimi.ingsw.model.Board.Direction.*;

public class ControllerGame {
    private UUID id;
    private List<Player> players;
    private Board board;
    private List<CommonGoal> commonGoals;
    private Player currentPlayer;
    private int numberOfPlayers;
    private Game game;
    private List<ObjectCard> limbo;

    public ControllerGame() {
        this.id = UUID.randomUUID();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.game = new Game();
        this.numberOfPlayers = 0;
        this.commonGoals = new ArrayList<>();
        this.limbo = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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
//      if (username == null) throw new NullPointerException("Username is null");
        if(!this.isUsernameAvailable(username)) throw new IllegalStateException("Username " + username + " already in use");

        if (this.players.size() < this.game.MAX_PLAYER) {
            Shelf shelf = new Shelf();
            PersonalGoalCard pg = game.getRandomAvailablePersonalGoalCard();

            Player p = new Player(username, shelf, pg);
            if(this.numberOfPlayers == 0) this.currentPlayer = p;
            this.players.add(p);
            this.numberOfPlayers++;

            return true;
        } else {
            throw new IllegalStateException("Max number of players reached");
        }
    }

    /**
     * Move to the next player
     * @return the next player
     */
    public Player nextPlayer() throws IllegalStateException{
        if(this.players.size() == 0) throw new IllegalStateException("No players");

        this.limbo.clear();
        if (this.players.indexOf(this.currentPlayer) == this.players.size() - 1) this.currentPlayer = this.players.get(0);
        else this.currentPlayer = players.get(players.indexOf(currentPlayer) + 1);

        return this.currentPlayer;
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

    // TODO: da spostare nella view
    /**
     * Select the column where the user want to add che ObjectCard, need to check if there is enough space
     * @param column is where the user want to add che ObjectCard
     */
    public void selectColumn(int column) {
        System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        while (currentPlayer.getShelf().getAvailableRows(column) < limbo.size()){
            System.out.println("La colonna selezionata non ha abbastanza spazi");
            System.out.println("Seleziona una colonna: [0, 1, 2, 3, 4]");
        }
    }

    /**
     * Load the shelf with the ObjectCard, the order has already been established
     * @param column is the number of the column where the ObjectCard is added
     * @param objectCard is the ObjectCard to add in the current player's shelf
     */
    public void loadShelf(int column, List<ObjectCard> objectCard) {
        currentPlayer.getShelf().addObjectCards(column, objectCard);
    }

    //si puo fare una modifica che non rimuova la coordinata della cella ma setti il contenuto a null
    /**
     * pick the ObjectCard from the board (if available)
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return the ObjectCard with that Coordinate
     */
    public ObjectCard pickObjectCard(Coordinate coordinate) {
        if(isObjectCardAvailable(coordinate)) return board.removeObjectCard(coordinate);
        else return null;
    }

    /**
     * check if the ObjectCard clicked by user is available, so if it has at least one side free
     * @param coordinate is the coordinate of the ObjectCard clicked by the user
     * @return true if this ObjectCard is available
     */
    private boolean isObjectCardAvailable(Coordinate coordinate) {
        return board.isEmptyAtDirection(coordinate, UP) || board.isEmptyAtDirection(coordinate, DOWN) || board.isEmptyAtDirection(coordinate, RIGHT) || board.isEmptyAtDirection(coordinate, LEFT);
    }

    /**
     * Add an object card to the limbo area
     * @param limbo is the zone where the objectCards selected from the board are put before being added in the shelf
     * @param objectCard is the objectCard selected
     * @throws NullPointerException if the objectCard is null (this case shouldn't happen)
     */
    public void addObjectCardToLimbo(List<ObjectCard> limbo, ObjectCard objectCard) throws NullPointerException{
        if (objectCard == null) throw new NullPointerException("ObjectCard is null");

        if (limbo.size() < 3) limbo.add(objectCard);
        else System.out.println("Limbo is full");
    }
}

