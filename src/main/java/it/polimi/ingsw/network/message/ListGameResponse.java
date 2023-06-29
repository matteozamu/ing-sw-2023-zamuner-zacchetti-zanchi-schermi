package it.polimi.ingsw.network.message;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.MessageContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Message that contains the list of available games
 */
public class ListGameResponse extends Message {
    private static final long serialVersionUID = 6870316479006394730L;
    private ArrayList<ControllerGame> games;

    public ListGameResponse(List<ControllerGame> games) {
        super("serverUser", null, MessageContent.LIST_GAME);
        this.games = (ArrayList<ControllerGame>) games;
    }

    public ArrayList<ControllerGame> getGames() {
        return games;
    }
}
