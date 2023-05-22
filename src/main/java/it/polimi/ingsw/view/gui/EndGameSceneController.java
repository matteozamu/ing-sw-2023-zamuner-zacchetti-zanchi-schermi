package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller of the end game scene
 */
public class EndGameSceneController {
    @FXML
    ImageView exitButton;
    @FXML
    TableView<TableRow> table;
    @FXML
    TableColumn<TableRow, String> rankColumn;
    @FXML
    TableColumn<TableRow, String> playerColumn;
    @FXML
    TableColumn<TableRow, String> pointsColumn;

    @FXML
    public void initialize() {
        bindEvents();
        tableSetup();
    }

    /**
     * Binds the click events
     */
    private void bindEvents() {
        exitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    /**
     * Setups the table
     */
    private void tableSetup() {
        rankColumn.setReorderable(false);
        rankColumn.setResizable(false);
        rankColumn.setSortable(false);
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));

        playerColumn.setReorderable(false);
        playerColumn.setResizable(false);
        playerColumn.setSortable(false);
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        pointsColumn.setReorderable(false);
        pointsColumn.setResizable(false);
        pointsColumn.setSortable(false);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        table.setPlaceholder(new Label());
    }

    /**
     * Sets data in the table
     *
     * @param players List of player points
     */
    void setData(List<Player> players) {
        List<Player> winners = players.stream().filter(Player::isWinner).collect(Collectors.toList());

        ArrayList<TableRow> tableRows = new ArrayList<>();

        tableRows.add(new TableRow("1", winners.get(0).getName(), Integer.toString(winners.get(0).getCurrentPoints())));

        if (winners.size() > 1) {
            for (Player player : winners.subList(1, winners.size())) {
                tableRows.add(new TableRow("", player.getName(), Integer.toString(player.getCurrentPoints())));
            }
        }

        players.removeAll(winners);

        players = players.stream()
                .sorted(Comparator.comparingInt(Player::getCurrentPoints).reversed())
                .collect(Collectors.toList());

        int count = 2;

        for (Player player : players) {
            tableRows.add(new TableRow(Integer.toString(count), player.getName(), Integer.toString(player.getCurrentPoints())));
            count++;
        }

        table.getItems().addAll(tableRows);
    }
}
