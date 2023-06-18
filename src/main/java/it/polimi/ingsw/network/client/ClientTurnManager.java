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
//        if (!turnStarted)
//            throw new ClientRoundManagerException("Error, round not started yet (before call nextState() you must call startTurn())");

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

            case END:
//                throw new ClientRoundManagerException("Error, in the UserPlayerState.END state you must call the endTurn() method");

            default:
//                throw new ClientRoundManagerException("Invalid State!");
        }
    }

    /**
     * Set the state to the delete limbo state
     */
    void deleteLimbo() {
        playerState = UserPlayerState.DELETE_LIMBO;
    }

    void loadingShelf() {
        playerState = UserPlayerState.LOADING_SHELF;
    }

    void endingPhase() {
        playerState = UserPlayerState.ENDING_PHASE;
    }

//    /**
//     * Set the state to the targeting scope state
//     */
//    void targetingScope() {
//        if (playerState == UserPlayerState.FIRST_ACTION || playerState == UserPlayerState.FIRST_FRENZY_ACTION) {
//            playerState = UserPlayerState.FIRST_SCOPE_USAGE;
//        } else if (playerState == UserPlayerState.SECOND_ACTION || playerState == UserPlayerState.SECOND_FRENZY_ACTION) {
//            playerState = UserPlayerState.SECOND_SCOPE_USAGE;
//        } else {
////            throw new InvalidGameStateException();
//        }
//    }

    // TODO Reconnection handler
    /**
     * Set the state after a reconnection
     */
//    void reconnection() {
//        handleBegin();
//    }

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

    /**
     * Sets the player state
     *
     * @param playerState player state that have to be setted
     */
    void setUserPlayerState(UserPlayerState playerState) {
        this.playerState = playerState;
    }

}
