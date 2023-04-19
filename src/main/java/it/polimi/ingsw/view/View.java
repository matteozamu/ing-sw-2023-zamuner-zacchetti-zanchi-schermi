package it.polimi.ingsw.view;

import java.util.List;

/**
 * Defines a generic view to be implemented by each view type (e.g. CLI, GUI in JavaFX, ...).
 */
public interface View {
    /**
     * Asks the user to choose a Nickname.
     */
    void askUsername();

}
