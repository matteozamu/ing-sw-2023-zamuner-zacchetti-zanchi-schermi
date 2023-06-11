package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;

public class NumberOfPlayersMessage extends Message {
    private static final long serialVersionUID = -8420070827032848314L;

    private final int numberOfPlayers;
    private final String gameName;

    public NumberOfPlayersMessage(String username, String token, int numberOfPlayers, String gameName) {
        super(username, token, MessageContent.NUMBER_OF_PLAYERS);
        this.numberOfPlayers = numberOfPlayers;
        this.gameName = gameName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getGameName() {
        return gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfPlayersMessage that = (NumberOfPlayersMessage) o;
        return getSenderUsername().equals(that.getSenderUsername());
    }

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
