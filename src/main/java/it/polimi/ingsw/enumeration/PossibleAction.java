package it.polimi.ingsw.enumeration;

/**
 * enumeration for the possible action
 */
public enum PossibleAction {
    BOARD_PICK_CARD("Choose a card from the board:"),
    LOAD_SHELF("Choose a column in which you want to put the selected object cards:"),
    DELETE_LIMBO("Put back the object card you selected in the board:"),
    PASS_TURN("Pass the turn"),
    REORDER_LIMBO("Reorder the selected object cards:"),
    JOIN_GAME("Join an existing game"),
    SHOW_COMMON_GOALS("Show common goals"),
    SHOW_PERSONAL_GOAL("Show personal goal card"),
    CREATE_GAME("Create a new game"),
    SHOW_SHELF("Show your personal shelf");

    private final String description;

    PossibleAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
