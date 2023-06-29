package it.polimi.ingsw.utility;


import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.*;

import java.util.ArrayList;
import java.util.UUID;

public class MessageBuilder {
    private MessageBuilder() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a {@link LobbyMessage LobbyMessage} object from {@code username}
     *
     * @param token         is the user token
     * @param username      is the username under which the user log itself in
     * @param disconnection tell if the user wants to disconnect
     * @return the {@link LobbyMessage LobbyMessage} object to send to the server
     */
    public static LobbyMessage buildAddPlayerToGameMessage(String token, String username, boolean disconnection) {
        return new LobbyMessage(username, token, disconnection);
    }

    /**
     * Create a {@link NumberOfPlayersMessage NumberOfPlayersMessage}
     *
     * @param token           is the user token
     * @param username        is the username under which the user log itself in
     * @param numberOfPlayers is the maximum number of players of the game
     * @return the {@link NumberOfPlayersMessage NumberOfPlayersMessage} object to send to the server
     */
    public static NumberOfPlayersMessage buildNumberOfPlayerMessage(String token, String username, int numberOfPlayers, String gameName) {
        return new NumberOfPlayersMessage(username, token, numberOfPlayers, gameName);
    }

    /**
     * Create a {@link ObjectCardRequest ObjectCardRequest}
     *
     * @param p          is the player
     * @param token      is the user token
     * @param coordinate is the coordinate chosen by the user
     * @return the {@link ObjectCardRequest ObjectCardRequest} object to send to the server
     */
    public static ObjectCardRequest buildPickObjectCardRequest(Player p, String token, Coordinate coordinate) {
        if (p == null || coordinate == null)
            throw new NullPointerException("Player or Coordinate cannot be null");

        return new ObjectCardRequest(p.getName(), token, coordinate);
    }

    /**
     * Create a {@link ListGameRequest ListGameRequest}
     *
     * @param username is the username under which the user log itself in
     * @param token    is the user token
     * @return the {@link ListGameRequest ListGameRequest} object to send to the server
     */
    public static ListGameRequest buildListGameRequest(String username, String token) {
        return new ListGameRequest(username, token);
    }

    /**
     * Create a {@link CreateGameRequest CreateGameRequest}
     *
     * @param username is the username under which the user log itself in
     * @param token    is the user token
     * @return the {@link CreateGameRequest CreateGameRequest} object to send to the server
     */
    public static CreateGameRequest buildCreateGameRequest(String username, String token) {
        return new CreateGameRequest(username, token);
    }

    /**
     * Create a {@link JoinGameRequest JoinGameRequest}
     *
     * @param token    is the user token
     * @param username is the username under which the user log itself in
     * @param gameUUID is the game id
     * @return the {@link JoinGameRequest JoinGameRequest} object to send to the server
     */
    public static JoinGameRequest buildJoinGameRequest(String token, String username, UUID gameUUID) {
        return new JoinGameRequest(username, token, gameUUID);
    }

    /**
     * Create a {@link ReorderLimboRequest ReorderLimboRequest}
     *
     * @param username is the username under which the user log itself in
     * @param token    is the user token
     * @param order    the new order of the selected object card
     * @return the {@link ReorderLimboRequest ReorderLimboRequest} object to send to the server
     */
    public static ReorderLimboRequest buildReorderLimboRequest(String username, String token, ArrayList<Integer> order) {
        return new ReorderLimboRequest(username, token, order);
    }

    /**
     * Create a {@link LoadShelfRequest LoadShelfRequest}
     *
     * @param token    is the user token
     * @param username is the username under which the user log itself in
     * @param column   is the column chosen by user
     * @return the {@link LoadShelfRequest LoadShelfRequest} object to send to the server
     */
    public static LoadShelfRequest buildLoadShelfRequest(String token, String username, int column) {
        return new LoadShelfRequest(username, token, column);
    }

    /**
     * Create a {@link DeleteLimboRequest DeleteLimboRequest}
     *
     * @param username is the username under which the user log itself in
     * @param token    is the user token
     * @return the {@link DeleteLimboRequest DeleteLimboRequest} object to send to the server
     */
    public static Message buildDeleteLimboRequest(String username, String token) {
        return new DeleteLimboRequest(username, token);
    }
}