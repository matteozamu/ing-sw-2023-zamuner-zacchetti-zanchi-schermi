package it.polimi.ingsw.enumeration;

/**
 * Enumeration containing all the possible states that the ControllerGame can have
 * while "controlling" the evolving of the game.
 */
public enum PossibleGameState {
    /**
     * Game State when wait to entry in lobby
     */
    GAME_ROOM,
    /**
     * Game started
     */
    GAME_STARTED,
    GAME_ENDED,
}
