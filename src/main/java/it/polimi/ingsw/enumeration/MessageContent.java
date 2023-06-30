package it.polimi.ingsw.enumeration;

/**
 * Enumeration for the message content
 */
public enum MessageContent {
    /**
     * Represents a connection message.
     */
    CONNECTION,

    /**
     * Represents a connection response message.
     */
    CONNECTION_RESPONSE,

    /**
     * Represents a message to add a player.
     */
    ADD_PLAYER,

    /**
     * Represents a message to pass the turn.
     */
    PASS_TURN,

    /**
     * Represents a response message.
     */
    RESPONSE,

    /**
     * Represents a game state message.
     */
    GAME_STATE,

    /**
     * Represents a winner message.
     */
    WINNER,

    /**
     * Represents a disconnection message.
     */
    DISCONNECTION,

    /**
     * Represents a ping message.
     */
    PING,

    /**
     * Represents a ready message.
     */
    READY,

    /**
     * Represents a reconnection message.
     */
    RECONNECTION,

    /**
     * Represents a game load message.
     */
    GAME_LOAD,

    /**
     * Represents the number of players message.
     */
    NUMBER_OF_PLAYERS,

    /**
     * Represents the setup number of players message.
     */
    SETUP_NUMBER_OF_PLAYERS,

    /**
     * Represents a board message.
     */
    BOARD,

    /**
     * Represents the players in the lobby message.
     */
    PLAYERS_IN_LOBBY,

    /**
     * Represents an object card validity message.
     */
    OBJECT_CARD_VALID,

    /**
     * Represents a message to pick an object card.
     */
    PICK_OBJECT_CARD,

    /**
     * Represents a message to load a shelf.
     */
    LOAD_SHELF_REQUEST,

    /**
     * Represents a response message for available columns.
     */
    AVAILABLE_COLUMNS_RESPONSE,

    /**
     * Represents a reorder limbo request message.
     */
    REORDER_LIMBO_REQUEST,

    /**
     * Represents a delete limbo message.
     */
    DELETE_LIMBO,

    /**
     * Represents a game ended message.
     */
    GAME_ENDED,

    /**
     * Represents a list game message.
     */
    LIST_GAME,

    /**
     * Represents a join game message.
     */
    JOIN_GAME,

    /**
     * Represents a create game message.
     */
    CREATE_GAME,

    /**
     * Represents a reconnection request message.
     */
    RECONNECTION_REQUEST,
}
