package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * Message to notify generic information to the user.
 */
public class GenericMessage extends Message {
    private static final long serialVersionUID = 12345678L;

    private final String message;


    public GenericMessage(String message) {
        super(Game.SERVER_NICKNAME, MessageType.GENERIC_MESSAGE);
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "GenericMessage{" +
                "nickname=" + getNickname() +
                ", message=" + message +
                '}';
    }
}
