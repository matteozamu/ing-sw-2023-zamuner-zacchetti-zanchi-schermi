package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.model.GameSerialized;

import java.util.List;

public class AvailableColumnsResponse extends Message {
    private static final long serialVersionUID = 2725986184174583892L;

    private final GameSerialized gameSerialized;
    private final List availableColumns;
    private final MessageStatus status;

    public AvailableColumnsResponse(String username, MessageStatus status, List<Integer> availableColumns) {
        super("serverUser", null, MessageContent.AVAILABLE_COLUMNS_RESPONSE);
        this.gameSerialized = new GameSerialized(username);
        this.status = status;
        this.availableColumns = availableColumns;
    }

    public GameSerialized getGameSerialized() {
        return gameSerialized;
    }

    public List getAvailableColumns() {
        return availableColumns;
    }

    public MessageStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AvailableColumnsResponse{" +
                "gameSerialized=" + gameSerialized +
                ", availableColumns=" + availableColumns +
                ", status=" + status +
                '}';
    }
}
