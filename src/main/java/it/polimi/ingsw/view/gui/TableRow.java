package it.polimi.ingsw.view.gui;

import javafx.scene.image.ImageView;

/**
 * This class represents a row of the table of EndGameSceneController
 */
public class TableRow {
    private String rank;
    private String username;
    private String points;

    TableRow(String rank, String username, String points) {
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