package it.polimi.ingsw.model;

public class PersonalGoal {
    private int row;
    private int column;
    private ObjectCardType type;

    public PersonalGoal(int row, int column, ObjectCardType type) {
        this.row = row;
        this.column = column;
        this.type = type;
    }

//    public ObjectCardType getType() {
//        return type;
//    }

    @Override
    public String toString() {
        return "PersonalGoal{" +
                "row=" + row +
                ", column=" + column +
                ", type=" + type +
                '}';
    }
}