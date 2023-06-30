package it.polimi.ingsw.enumeration;

/**
 * Enumeration representing all the possible states that the client round manager can have.
 * These states are mapped to the ones in the controller thanks to a mapping function
 * used to reload a game in persistency.
 */
public enum UserPlayerState {
    /**
     * Represents a disconnected state.
     */
    DISCONNECTED,

    /**
     * Represents a state where the player is picking a card from the board.
     */
    PICK_CARD_BOARD,

    /**
     * Represents a state after picking the first object card.
     */
    AFTER_FIRST_PICK,

    /**
     * Represents a state for the player's first action.
     */
    FIRST_ACTION,

    /**
     * Represents an ending phase state.
     */
    ENDING_PHASE,

    /**
     * Represents an end state.
     */
    END,

    /**
     * Represents a state for deleting object cards from limbo.
     */
    DELETE_LIMBO,

    /**
     * Represents a state for loading the player's shelf.
     */
    LOADING_SHELF,

    /**
     * Represents a state indicating that the game has ended.
     */
    GAME_ENDED
}
