package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

import java.io.Serial;

/**
 * Message that contains the number of players in a game
 */
public class NumberOfPlayersMessage extends Message {
    @Serial
    private static final long serialVersionUID = -8420070827032848314L;

    private final int numberOfPlayers;
    private final String gameName;

    /**
     * Constructs a message
     *
     * @param username       username of the sender
     * @param token          token of the sender
     * @param numberOfPlayers number of players in the game
     * @param gameName       name of the game
     */
    public NumberOfPlayersMessage(String username, String token, int numberOfPlayers, String gameName) {
        super(username, token, MessageContent.NUMBER_OF_PLAYERS);
        this.numberOfPlayers = numberOfPlayers;
        this.gameName = gameName;
    }

    /**
     * Returns the number of players in the game
     *
     * @return number of players in the game
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Returns the name of the game
     *
     * @return name of the game
     */
    public String getGameName() {
        return gameName;
    }

    // A number of players message is equal to another (in our case) if it is the same message or if it has the same user sender name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfPlayersMessage that = (NumberOfPlayersMessage) o;
        return getSenderUsername().equals(that.getSenderUsername());
    }

    /**
     * Method that returns a string representation of the object
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "NumberOfPplayersMessage{" +
                "senderUsername=" + getSenderUsername() +
                ", content=" + getContent() +
                ", gameName=" + gameName +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}