package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * message sent by the client to notify if the selected Object card are valid or not
 */
public class ObjCardReplyMessage extends Message {
    private static final long serialVersionUID = 908765L;
    private final Boolean accepted;

    /**
     * message sent by the server to notify if the Object Card selected are acceptable or not
     * @param accepted is true if the Object Card are valid, false otherwise
     */
    public ObjCardReplyMessage(Boolean accepted) {
        super(Game.SERVER_NICKNAME, MessageType.OBJ_CARD_REPLY);
        this.accepted = accepted;
    }

    /**
     * @return either the choosen Object Card are valid or not
     */
    public Boolean getAccepted(){
        return this.accepted;
    }

    @Override
    public String toString() {
        return "ObjCardReplyMessage{" +
                "accepted=" + accepted +
                '}';
    }
}
