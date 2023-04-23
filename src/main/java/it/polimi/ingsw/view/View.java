package it.polimi.ingsw.view;

import java.util.List;

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
     * Asks the user to choose a Nickname.
     */
    void askUsername();

    void askObjCard();


    /**
     * Shows an error message.
     *
     * @param error the error message to be shown.
     */
    void showErrorAndExit(String error);
}
