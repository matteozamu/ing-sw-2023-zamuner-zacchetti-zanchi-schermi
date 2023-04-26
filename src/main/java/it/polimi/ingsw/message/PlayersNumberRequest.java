package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * Message used to ask the client the maximum number of players of the game.
 */
public class PlayersNumberRequest extends Message {
    private static final long serialVersionUID = -1395235L;

    public PlayersNumberRequest() {
        super(Game.SERVER_NICKNAME, MessageType.PLAYERS_NUMBER_REQUEST);
    }

    @Override
    public String toString() {
        return "PlayerNumberRequest{" +
                "nickname=" + getUsername() +
                '}';
    }
}
