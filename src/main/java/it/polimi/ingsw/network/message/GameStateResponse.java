package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.model.GameSerialized;

/**
 * Message class to inform the clients about the model changes
 */
public class GameStateResponse extends Message {
    private static final long serialVersionUID = 2725986184174583892L;

    private final GameSerialized gameSerialized;
    private final String turnOwner;

    public GameStateResponse(String username, String turnOwner, String filepath) {
        super("serverUser", null, MessageContent.GAME_STATE);
        this.gameSerialized = new GameSerialized(username, filepath);
        this.turnOwner = turnOwner;
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    public String getTurnOwner() {
        return turnOwner;
    }

    @Override
    public String toString() {
        return "GameStateResponse{" +
                ", turnOwner='" + turnOwner + '\'' +
                '}';
    }
}
