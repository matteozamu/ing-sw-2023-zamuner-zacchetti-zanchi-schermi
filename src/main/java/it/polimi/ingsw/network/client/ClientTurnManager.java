package it.polimi.ingsw.network.client;

import it.polimi.ingsw.enumeration.UserPlayerState;

/**
 * Owns the game state machine for a client
 */
class ClientTurnManager {
    private UserPlayerState playerState;

    private boolean turnStarted;

    ClientTurnManager() {
        this.playerState = UserPlayerState.PICK_CARD_BOARD;
    }

    /**
     * Change the state of the player to the next one
     */
    void nextState() {
        switch (playerState) {
            case PICK_CARD_BOARD:
                playerState = UserPlayerState.AFTER_FIRST_PICK;
                break;

            case LOADING_SHELF:
                playerState = UserPlayerState.ENDING_PHASE;
                break;

            case DELETE_LIMBO:
                playerState = UserPlayerState.PICK_CARD_BOARD;
                break;
            default:
                break;
        }
    }

    /**
     * Set the state to the delete limbo state
     */
    void deleteLimbo() {
        playerState = UserPlayerState.DELETE_LIMBO;
    }

    /**
     * Begins the round
     */
    void startTurn() {
        turnStarted = true;
        playerState = UserPlayerState.PICK_CARD_BOARD;
    }

    /**
     * Set the state to {@code FIRST_ACTION}, reset {@code botMoved} to false
     */
    void endTurn() {
        playerState = UserPlayerState.FIRST_ACTION;
        turnStarted = false;
    }

    /**
     * @return the current user player state
     */
    UserPlayerState getUserPlayerState() {
        return playerState;
    }

}
