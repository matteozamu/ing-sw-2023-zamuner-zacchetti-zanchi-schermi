package it.polimi.ingsw.message;

/**
 * This enum contains all the message type available and used by the server and clients.
 */
public enum MessageType {
    LOGIN_REQUEST,
    LOGIN_REPLY,
    BOARD,
    WIN,
    OBJ_CARD_REQUEST,
    OBJ_CARD_REPLY,
    AVAILABLE_COLUMNS,
    CHOOSEN_COLUMN,
    ORDERED_OBJ_CARD,
    SHELF,


    //utility:
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
