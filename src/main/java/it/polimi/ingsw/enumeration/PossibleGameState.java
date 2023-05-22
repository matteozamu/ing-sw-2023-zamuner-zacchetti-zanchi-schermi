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

    // da togliere
    FINAL_FRENZY,
    SECOND_ACTION,
    MISSING_TERMINATOR_ACTION,
    GRANADE_USAGE,
    SCOPE_USAGE,
    PASS_NORMAL_TURN,
    PASS_NORMAL_BOT_TURN,
    RELOAD_PASS,
    PASS_FRENZY_TURN,
    ACTIONS_DONE,
    FRENZY_ACTIONS_DONE,
    MANAGE_DEATHS,
    TERMINATOR_RESPAWN
}
