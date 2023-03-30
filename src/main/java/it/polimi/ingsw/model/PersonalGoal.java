package it.polimi.ingsw.model;

/**
 * Represents a personal goal for a player in the game.
 */
public class PersonalGoal {
    private int row;
    private int column;
    private ObjectCardType type;

    /**
     * Constructs a PersonalGoal with the given row, column, and ObjectCardType.
     *
     * @param row    The row of the target position for the personal goal.
     * @param col The column of the target position for the personal goal.
     * @param type   The ObjectCardType associated with the personal goal.
     */
    public PersonalGoal(int row, int col, ObjectCardType type) {
        this.row = row;
        this.column = col;
        this.type = type;
    }

    @Override
    public String toString() {
        return "PersonalGoal{" +
                "row=" + row +
                ", column=" + column +
                ", type=" + type +
                '}';
    }
}
