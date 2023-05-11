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

//                case SPAWN:
//                    handleBegin();
//                    break;

            case GET_CARD_BOARD:
                playerState = UserPlayerState.SECOND_ACTION;
                break;

            case SECOND_ACTION:
                handleSecondMove();
                break;

            case FIRST_FRENZY_ACTION:
                handleFirstFrenzy();
                break;

            case FIRST_SCOPE_USAGE:
                handleFirstScope();
                break;

            case BOT_ACTION:
                playerState = UserPlayerState.ENDING_PHASE;
                break;

            case ENDING_PHASE:
            case BOT_RESPAWN:
                playerState = UserPlayerState.END;
                break;

            case DEAD:
            case GRENADE_USAGE:
                playerState = UserPlayerState.FIRST_ACTION;
                break;

            case END:
//                throw new ClientRoundManagerException("Error, in the UserPlayerState.END state you must call the endTurn() method");

            default:
//                throw new ClientRoundManagerException("Invalid State!");
        }
    }

    /**
     * Handles the next state in the begin phase of the game
     */
    private void handleBegin() {
        playerState = UserPlayerState.GET_CARD_BOARD;
    }

    /**
     * Handles the next state after the second move of the player
     */
    private void handleSecondMove() {
        playerState = UserPlayerState.ENDING_PHASE;
    }

    /**
     * Handles the next state after the first frenzy move
     */
    private void handleFirstFrenzy() {
        if (true) {
            playerState = UserPlayerState.BOT_ACTION;
        } else {
            playerState = UserPlayerState.ENDING_PHASE;
        }
    }

    /**
     * Handles the next state in case of scope usage
     */
    private void handleFirstScope() {
        playerState = UserPlayerState.SECOND_ACTION;
    }

    /**
     * Set the state to the dead state
     */
    void death() {
        playerState = UserPlayerState.DEAD;
    }

    /**
     * Set the state to the targeting scope state
     */
    void targetingScope() {
        if (playerState == UserPlayerState.FIRST_ACTION || playerState == UserPlayerState.FIRST_FRENZY_ACTION) {
            playerState = UserPlayerState.FIRST_SCOPE_USAGE;
        } else if (playerState == UserPlayerState.SECOND_ACTION || playerState == UserPlayerState.SECOND_FRENZY_ACTION) {
            playerState = UserPlayerState.SECOND_SCOPE_USAGE;
        } else {
//            throw new InvalidGameStateException();
        }
    }

    /**
     * Set the state after a reconnection
     */
    void reconnection() {
        handleBegin();
    }

    /**
     * Begins the round
     */
    void startTurn() {
        turnStarted = true;
    }

    /**
     * Set the state to {@code FIRST_ACTION}, reset {@code botMoved} to false
     */
    void endTurn() {
        playerState = UserPlayerState.FIRST_ACTION;
        turnStarted = false;
    }

    /**
     * Sets frenzy first action
     */
    void setFrenzyFirstAction() {
        playerState = UserPlayerState.FIRST_FRENZY_ACTION;
    }

    /**
     * Sets the player state
     *
     * @param playerState player state that have to be setted
     */
    void setPlayerState(UserPlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * @return the current user player state
     */
    UserPlayerState getUserPlayerState() {
        return playerState;
    }
}
