package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Represents a personal goal for a player in the game.
 */
public class PersonalGoal implements Serializable {
    private final int row;
    private final int column;
    private final ObjectCardType type;

    /**
     * Constructs a PersonalGoal with the given row, column, and ObjectCardType.
     *
     * @param row  The row of the target position for the personal goal.
     * @param col  The column of the target position for the personal goal.
     * @param type The ObjectCardType associated with the personal goal.
     */
    public PersonalGoal(int row, int col, ObjectCardType type) {
        this.row = row;
        this.column = col;
        this.type = type;
    }

    /**
     * Return the index of the column
     *
     * @return the index of the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Return the index of the row
     *
     * @return the index of the row
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the type of Personal Goal
     *
     * @return the type of Personal Goal
     */
    public ObjectCardType getType() {
        return type;
    }
}