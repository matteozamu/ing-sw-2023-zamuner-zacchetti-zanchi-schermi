package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Coordinate;
import it.polimi.ingsw.model.ObjectCard;

import java.util.List;
import java.util.Map;

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI in JavaFX, ...).
 */
public interface View {
    /**
     * Shows to the user if the Login succeeded.
     *
     * @param usernameAccepted     indicates if the chosen nickname has been accepted.
     * @param connectionSuccessful indicates if the connection has been successful.
     * @param username             the nickname of the player to be greeted.
     */
    void showLoginResult(boolean usernameAccepted, boolean connectionSuccessful, String username);

    /**
     * Shows a generic message.
     *
     * @param genericMessage the generic message to be shown.
     */
    void showGenericMessage(String genericMessage);

    /**
     * Shows the lobby with connected players.
     *
     * @param nicknameList list of players.
     * @param numPlayers   number of players.
     */
    void showLobby(List<String> nicknameList, int numPlayers);

    /**
     * Asks the user to choose a Nickname.
     */
    void askUsername();

    /**
     * Asks the user the number of players he wants to play with.
     */
    void askPlayersNumber();

//    void showMatchInfo(List<String> players, Map<Coordinate, ObjectCard> board, String activePlayer);

    void showBoard(Map<Coordinate, ObjectCard> board);

    void askObjCard();

    /**
     * Shows an error message.
     *
     * @param error the error message to be shown.
     */
    void showErrorAndExit(String error);
}
