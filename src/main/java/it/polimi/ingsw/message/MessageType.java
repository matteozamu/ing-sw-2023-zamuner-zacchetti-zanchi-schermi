package it.polimi.ingsw.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST,
    LOGIN_REPLY,
    PLAYERS_NUMBER_REQUEST,
    PLAYERS_NUMBER_REPLY,
    LOBBY,
    BOARD,
    WIN,
    OBJ_CARD_REQUEST,
    OBJ_CARD_REPLY,
    AVAILABLE_COLUMNS,
    CHOOSEN_COLUMN,
    ORDERED_OBJ_CARD,
    SHELF,
    END_GAME,


    //utility:
    INFO,
    GAME_LOAD,
    MATCH_INFO,
    DISCONNECTION,
    GENERIC_MESSAGE,
    PING,
    ERROR,
    ENABLE_EFFECT,
    APPLY_EFFECT,
    PERSISTENCE
}
