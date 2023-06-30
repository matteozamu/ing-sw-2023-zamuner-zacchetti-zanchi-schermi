package it.polimi.ingsw.enumeration;

/**
 * Enumeration representing the Status of a Response {@link it.polimi.ingsw.network.message.Message Message}
 */
public enum MessageStatus {
    /**
     * Represents a successful response.
     */
    OK,

    /**
     * Represents an error response.
     */
    ERROR,

    /**
     * Represents a response indicating no further response is expected.
     */
    NO_RESPONSE,

    /**
     * Represents a response indicating that the game has ended.
     */
    GAME_ENDED,

    /**
     * Represents a response indicating that a game has been created.
     */
    GAME_CREATED,

    /**
     * Represents a response indicating that a player has joined a game.
     */
    GAME_JOINED,

    /**
     * Represents a response indicating that a card is not valid.
     */
    NOT_VALID_CARD,

    /**
     * Represents a response indicating that a player has quit.
     */
    QUIT,

    /**
     * Represents a response indicating that the limbo state should be printed.
     */
    PRINT_LIMBO
}
