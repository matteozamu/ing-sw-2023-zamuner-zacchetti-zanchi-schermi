package it.polimi.ingsw.view.gui;

/**
 * This class represents a row of the table of EndGameSceneController
 */
public class TableRowEndScene {
    private String rank;
    private String username;
    private String points;

    TableRowEndScene(String rank, String username, String points) {
        this.rank = rank;
        this.username = username;
        this.points = points;
    }

    public String getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public String getPoints() {
        return points;
    }
}