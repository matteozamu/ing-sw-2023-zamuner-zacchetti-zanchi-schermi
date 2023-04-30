package it.polimi.ingsw.utility;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.Server;

import java.util.List;
import java.util.stream.Collectors;

public class InputValidator {
    private static final int MAX_POWERUPS = 3;

    private InputValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean validatePlayerUsername(List<Player> players, Message checkingMessage) {
        if (!checkBaseMessage(checkingMessage)) {
            return false;
        }

        for (Player player : players) {
            if (player.getName().equals(checkingMessage.getSenderUsername())) {
                return true;
            }
        }

        return false;
    }

    public static boolean validateInput(Message checkingMessage) {
        if (checkingMessage == null) return false;

        if (!checkBaseMessage(checkingMessage)) {
            return false;
        }

        try {
            switch (checkingMessage.getContent()) {
                case PASS_TURN:
                case LOBBY_VOTE:
                    return true;
                case ADD_PLAYER:
                    return true;
                case NUMBER_OF_PLAYERS:
                    return true;
                default:    // always one of this kind of messages needs to be validated
                    return false;
            }
        } catch (ClassCastException e) {
            Server.LOGGER.severe("Invalid cast of a message from " + checkingMessage.getSenderUsername());
            return false;
        }

    }

    private static boolean checkBaseMessage(Message message) {
        if (message.getSenderUsername() == null) {
            return false;
        }

        if (message.getSenderUsername().equalsIgnoreCase("ServerUser") || message.getSenderUsername().equalsIgnoreCase("Botname")) {
            return false;
        }

        return message.getContent() != null;
    }

    private static boolean checkDuplicatesInArguments(List<?> checkingList) {
        List<?> distinctList = checkingList.stream().distinct().collect(Collectors.toList());
        return distinctList.size() == checkingList.size();
    }
}
