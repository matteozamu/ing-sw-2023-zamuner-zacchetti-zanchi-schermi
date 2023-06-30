package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * Message class that is sent when a change is done to the lobby.
 * It contains the player in lobby
 */
public class LobbyPlayersResponse extends Message {
    @Serial
    private static final long serialVersionUID = 6870316479006394730L;
    private ArrayList<String> users;

    /**
     * Constructs a list game response message
     *
     * @param users list of available games
     */
    public LobbyPlayersResponse(List<String> users) {
        super("serverUser", null, MessageContent.PLAYERS_IN_LOBBY);
        this.users = (ArrayList<String>) users;
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation of the object
     */
    public List<String> getUsers() {
        return users;
    }
}