package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Message class that is sent when a change is done to the lobby.
 * It contains the player in lobby
 */
public class LobbyPlayersResponse extends Message {
    private static final long serialVersionUID = 6870316479006394730L;
    private ArrayList<String> users;

    public LobbyPlayersResponse(List<String> users) {
        super("serverUser", null, MessageContent.PLAYERS_IN_LOBBY);
        this.users = (ArrayList<String>) users;
    }

    public List<String> getUsers() {
        return users;
    }
}
