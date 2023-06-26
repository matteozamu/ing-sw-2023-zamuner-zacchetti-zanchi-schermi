package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Represents a personal goal for a player in the game.
 */
public class PersonalGoal implements Serializable {
    private int row;
    private int column;
    private ObjectCardType type;

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
     * @return the index of the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the index of the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the type of Personal Goal
     */
    public ObjectCardType getType() {
        return type;
    }

//    @Override
//    public String toString() {
//        return "PersonalGoal{" +
//                "row=" + row +
//                ", column=" + column +
//                ", type=" + type +
//                '}';
//    }
}
