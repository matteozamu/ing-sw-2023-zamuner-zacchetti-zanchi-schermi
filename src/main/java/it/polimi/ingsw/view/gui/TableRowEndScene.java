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

    /**
     * Gets the rank
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Gets the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the points
     * @return the points
     */
    public String getPoints() {
        return points;
    }
}