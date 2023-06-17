package it.polimi.ingsw.network.client;

import it.polimi.ingsw.control.ControllerGame;
import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.CommonGoal;
import it.polimi.ingsw.model.GameSerialized;
import it.polimi.ingsw.network.message.ConnectionResponse;
import it.polimi.ingsw.network.message.Response;

import java.util.List;

/**
 * This listener is used to let the game progress via user inputs.
 * Methods are called when is received a specific message from the server
 */
interface ClientGameManagerListener {
    /**
     * Handles the response to a connection
     */
    void connectionResponse(ConnectionResponse response);

    void addPlayerToGameRequest();

    /**
     * Handles the response to a connection for a loaded game
     */
    void loadResponse();

    void responseError(String error);

    /**
     * Handles the response to the lobby join
     *
     * @param response to the lobby join
     */
    void lobbyJoinResponse(Response response);

    void numberOfPlayersRequest(Response response);

    /**
     * Notifies an update of players in the lobby
     *
     * @param users list of users in the lobby
     */
    void playersWaitingUpdate(List<String> users);

    void chooseGameToJoin(List<ControllerGame> games);

    void noGameAvailable();

    /**
     * Tells the client that is not his turn
     */
    void notYourTurn(String turnOwner);

    /**
     * Communicates who is the first player
     *
     * @param username first player username
     */
    void firstPlayerCommunication(String username, List<CommonGoal> cg);

    /**
     * Handles a game state update
     */
    void gameStateUpdate();

    /**
     * Tells the client what are the possible actions
     *
     * @param possibleActions list of possible actions
     */
    void displayActions(List<PossibleAction> possibleActions);

    /**
     * allows the client to choose an object card from the board
     */
    void pickBoardCard();

    void joinGame();

    void createGame();

    /**
     * print the object card selected by the client
     */
    void printLimbo();

    /**
     * allows the user to reorder the selected object cards
     */
    void reorderLimbo();

    /**
     * allows the user to put back the selected object cards on the board
     */
    void deleteLimbo();

    /**
     * allows the user to choose the column where to put the selected object cards
     */
    void chooseColumn();

    void showPersonalGoal();

    void showShelf();

    /**
     * print the user the winner of the game
     *
     * @param gameSerialized is the game serialized
     */
    void printWinner(GameSerialized gameSerialized);

    /**
     * print the user the end of the game
     *
     * @param message is the message to print
     */
    void printEndGame(String message);


    void printScore();

    void gameStateRequest(String username, String token);

    /**
     * print the disconnection of the player
     *
     * @param username is the username that disconnected
     */
    void onPlayerDisconnection(String username);

    /**
     * print the reconnection of the player
     *
     * @param message is the message to show
     */
    void onPlayerReconnection(String message);
}
