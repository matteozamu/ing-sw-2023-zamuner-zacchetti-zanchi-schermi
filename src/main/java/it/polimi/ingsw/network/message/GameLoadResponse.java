package it.polimi.ingsw.network.message;


import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.UserPlayerState;

import java.io.Serial;

/**
 * Message class that is sent when a load game is completed successfully
 */
public class GameLoadResponse extends Message {
    @Serial
    private static final long serialVersionUID = 4880522547664967982L;

    private final String newToken;
    private final String message;
    private final UserPlayerState userPlayerState;

    /**
     * Constructs a game load response message
     *
     * @param message         message of the response
     * @param newToken        new token of the player
     * @param userPlayerState state of the player
     */
    public GameLoadResponse(String message, String newToken, UserPlayerState userPlayerState) {
        super("ServerUser", null, MessageContent.GAME_LOAD);
        this.message = message;
        this.newToken = newToken;
        this.userPlayerState = userPlayerState;
    }

    /**
     * Method to get the new token of the player
     */
    public String getMessage() {
        return message;
    }
}
