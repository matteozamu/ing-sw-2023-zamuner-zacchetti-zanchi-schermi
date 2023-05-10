package it.polimi.ingsw.enumeration;

public enum PossibleAction {
    // TODO da cambiare
    BOARD_PICK_CARD("Choose a card from the board:"),
    CHOOSE_COLUMN("Choose a column in which you want to put the selected object cards:"),
    DELETE_LIMBO("Put back the object card you selected in the board:"),
    PASS_TURN("Pass the turn");

    private String description;

    PossibleAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
