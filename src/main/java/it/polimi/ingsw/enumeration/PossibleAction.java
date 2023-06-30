package it.polimi.ingsw.enumeration;

/**
 * Enumeration for the possible actions
 */
public enum PossibleAction {
    /**
     * Represents the action of choosing a card from the board.
     */
    BOARD_PICK_CARD("Choose a card from the board:"),

    /**
     * Represents the action of choosing a column to load selected object cards.
     */
    LOAD_SHELF("Choose a column in which you want to put the selected object cards:"),

    /**
     * Represents the action of putting back the selected object card in the board.
     */
    DELETE_LIMBO("Put back the object card you selected in the board:"),

    /**
     * Represents the action of reordering the selected object cards.
     */
    REORDER_LIMBO("Reorder the selected object cards:"),

    /**
     * Represents the action of passing the turn.
     */
    PASS_TURN("Pass the turn"),

    /**
     * Represents the action of joining an existing game.
     */
    JOIN_GAME("Join an existing game"),

    /**
     * Represents the action of showing the common goals.
     */
    SHOW_COMMON_GOALS("Show common goals"),

    /**
     * Represents the action of showing the personal goal card.
     */
    SHOW_PERSONAL_GOAL("Show personal goal card"),

    /**
     * Represents the action of creating a new game.
     */
    CREATE_GAME("Create a new game"),

    /**
     * Represents the action of showing the personal shelf.
     */
    SHOW_SHELF("Show your personal shelf"),

    /**
     * Represents the action of canceling an action.
     */
    CANCEL("Cancel");

    private final String description;

    PossibleAction(String description) {
        this.description = description;
    }

    /**
     * Get the description of the action.
     *
     * @return The description of the action.
     */
    public String getDescription() {
        return description;
    }
}
