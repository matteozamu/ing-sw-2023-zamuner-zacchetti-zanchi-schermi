package it.polimi.ingsw.utility;

import org.jetbrains.annotations.Contract;

/**
 * This class is used to validate the server address
 */
public class ServerAddressValidator {

    /**
     * The maximum length of the address
     */
    public static final int MAX_ADDRESS_LENGTH = 15;

    /**
     * The maximum length of the port
     */
    public static final int MAX_PORT_LENGTH = 5;

    /**
     * This class cannot be instantiated
     */
    private ServerAddressValidator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Check if the address is valid
     *
     * @param address the address to check
     * @return true if the address is valid, false otherwise
     */
    @Contract("null -> true")
    public static boolean isAddressValid(String address) {
        if (address == null || address.equals("localhost")) {
            return true;
        }

        return address.matches("\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b");
    }

    /**
     * Check if the port is valid
     *
     * @param portString the port to check
     * @return true if the port is valid, false otherwise
     */
    public static boolean isPortValid(String portString) {
        try {
            int port = Integer.parseInt(portString);
            if (port >= 1 && port <= 25565) {
                return true;
            }
        } catch (NumberFormatException e) {
        }

        return false;
    }
}