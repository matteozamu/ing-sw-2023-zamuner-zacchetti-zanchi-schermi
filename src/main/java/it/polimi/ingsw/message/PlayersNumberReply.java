package it.polimi.ingsw.message;

/**
 * Message used to send to the server the number of players picked by the client.
 */
public class PlayersNumberReply extends Message {
    private static final long serialVersionUID = -293753L;
    private final int playerNumber;

    public PlayersNumberReply(String username, int playerNumber) {
        super(username, MessageType.PLAYERS_NUMBER_REPLY);
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public String toString() {
        return "PlayerNumberReply{" +
                "nickname=" + getUsername() +
                ", playerNumber=" + playerNumber +
                '}';
    }
}
