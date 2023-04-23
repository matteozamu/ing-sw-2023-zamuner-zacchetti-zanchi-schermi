package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * Message used to confirm or discard a login request of a client.
 */
public class LoginReply extends Message {

    private static final long serialVersionUID = 654321L;
    private final boolean nicknameAccepted;
    private final boolean connectionSuccessful;

    public LoginReply(boolean nicknameAccepted, boolean connectionSuccessful) {
        super(Game.SERVER_NICKNAME, MessageType.LOGIN_REPLY);
        this.nicknameAccepted = nicknameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }

    public boolean isUsernameAccepted() {
        return nicknameAccepted;
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    @Override
    public String toString() {
        return "LoginReply{" +
                "nickname=" + getUsername() +
                ", nicknameAccepted=" + nicknameAccepted +
                ", connectionSuccessful=" + connectionSuccessful +
                '}';
    }
}
