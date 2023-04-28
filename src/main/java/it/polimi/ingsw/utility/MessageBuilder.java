package it.polimi.ingsw.utility;


import it.polimi.ingsw.network.message.ConnectionRequest;
import it.polimi.ingsw.network.message.LobbyMessage;

public class MessageBuilder {


    private MessageBuilder() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a {@link ConnectionRequest ConnectionRequest} object from {@code username}
     *
     * @param username username chosen by the user to request from the server if available
     * @return the {@link ConnectionRequest ConnectionRequest} object to send to the server
     */
    public static ConnectionRequest buildConnectionRequest(String username) {
        return new ConnectionRequest(username);
    }

    public static LobbyMessage buildGetInLobbyMessage(String token, String username, boolean disconnection) {
        return new LobbyMessage(username, token, disconnection);
    }
}