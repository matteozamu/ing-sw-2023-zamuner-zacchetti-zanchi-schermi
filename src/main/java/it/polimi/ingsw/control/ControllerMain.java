package it.polimi.ingsw.control;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.ObjectCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Shelf;

import java.util.Observable;

// se lo chiamiamo GameController ???
public class ControllerMain {
    //private static Game game = new Game();

    private Game game;
    private Player player;
    private ObjectCard objCard;
    private Shelf shelf;

    /*
    public ControllerMain(Game game) {
        this.game = game;
    }
     */

    //update function, funzione update vista dal prof
    //ipotizzo che dalla view arrivi una stringa Usarname
    //ricopio la funzione con observer e observable //TODO da sostituire
    public String update(Observable o, String username) {   // si può ritornare una stringa alla view???
        if (username instanceof String) {
            if (game.isUsernameAvailable(username)) {   // controllando che la stringa non sia null posso togliere il controllo nel model
                game.addPlayer(username);
                return "Utente inserito";
            } else {
                return "Nome non disponibile";
            }

        } else {
            /* Unknown event */
            return "Inserire un nome";
        }
    }

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
