package it.polimi.ingsw.message;

import it.polimi.ingsw.model.Game;

/**
 * Message used to confirm or discard a login request of a client.
 */
public class LoginReply extends Message {

    private static final long serialVersionUID = 654321L;
    private final boolean usernameAccepted;
    private final boolean connectionSuccessful;

    public LoginReply(boolean usernameAccepted, boolean connectionSuccessful) {
        super(Game.SERVER_NICKNAME, MessageType.LOGIN_REPLY);
        this.usernameAccepted = usernameAccepted;
        this.connectionSuccessful = connectionSuccessful;
    }

    public boolean isUsernameAccepted() {
        return usernameAccepted;
    }

    public boolean isConnectionSuccessful() {
        return connectionSuccessful;
    }

    @Override
    public String toString() {
        return "LoginReply{" +
                "nickname=" + getUsername() +
                ", nicknameAccepted=" + usernameAccepted +
                ", connectionSuccessful=" + connectionSuccessful +
                '}';
    }
}
