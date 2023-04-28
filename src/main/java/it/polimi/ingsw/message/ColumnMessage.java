package it.polimi.ingsw.message;

/**
 * this message is sent by the clien to notify which column the user has selected
 */
public class ColumnMessage extends Message {
    private static final long serialVersionUID = 63494362L;
    private final int column;

    /**
     * default constructos
     * @param username is the client who sent the message
     * @param column is che choosen column
     */
    public ColumnMessage(String username, int column) {
        super(username, MessageType.CHOOSEN_COLUMN);
        this.column = column;
    }

    /**
     *
     * @return the choosen column
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "ColumnMessage{" +
                "column=" + column +
                '}';
    }
}
