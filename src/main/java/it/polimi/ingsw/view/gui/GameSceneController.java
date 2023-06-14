package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.JsonReader;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import it.polimi.ingsw.model.Player.*;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.utility.MessageBuilder;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static it.polimi.ingsw.model.ObjectCardType.cat;
import static java.awt.Transparency.OPAQUE;

/**
 * Class for the graphical interface of the game
 */
public class GameSceneController {
    private static final String USERNAME_PROPERTY = "username";
    private static final String CSS_BUTTON = "button";
    private static final String CSS_SHELF = "shelf";
    private static final String CSS_SHELF_GRIDPANE = "shelfGridPane";
    private static final String CSS_SHELF_LABEL = "shelfLabel";
    private static final double OPAQUE = 0.2;
    private static final double NOT_OPAQUE = 1;
    private static final double COMMONGOAL_CARD_WIDTH = 138.5;
    private static final double COMMONGOAL_CARD_HEIGHT = 91.3;
    private static final double BOARD_OBJECT_CARD_WIDTH = 60.0;
    private static final double BOARD_OBJECT_CARD_HEIGHT = 60.0;
    private static final double PERSONALGOAL_CARD_WIDTH = 137.0;
    private static final double PERSONALGOAL_CARD_HEIGHT = 207.9;
    private static final double PERSONALGOAL_CARD_TRANSLATE_Y = 51.5;
    private static final double SHELF_WIDTH = 400.0;
    private static final double SHELF_HEIGHT = 400.0;
    private static final double LIMBO_OBJECT_CARD_WIDTH = 75.0;
    private static final double LIMBO_OBJECT_CARD_HEIGHT = 75.0;

    private static final String SHELF_PATH = "/img/board_shelf/shelf_orth.png";

    @FXML
    Pane mainPane;
    @FXML
    StackPane gameStackPaneArea;
    @FXML
    StackPane boardStackPaneArea;
    @FXML
    GridPane boardGridPane;
    @FXML
    StackPane commonGoalCard1StackPane;
    @FXML
    StackPane commonGoalCard2StackPane;
    @FXML
    ImageView boardImage;
    @FXML
    HBox boardShelfHBoxArea;
    @FXML
    VBox boardCommonGoalCardsVBoxArea;
    @FXML
    VBox shelfLimboVBoxArea;
    @FXML
    FlowPane commonGoalCardInfoPanel;
    @FXML
    ImageView winnerTile;
    @FXML
    ImageView pointsTile;
    @FXML
    ImageView chair;
    //TODO: Mappa per associare nome shelf e immagine shelf?
    @FXML
    ImageView shelf1;
    @FXML
    ImageView shelf2;
    @FXML
    ImageView shelf3;
    @FXML
    ImageView shelf4;
    @FXML
    HBox shelfHBoxImages;
    @FXML
    Label shelfLabel1;
    @FXML
    Label shelfLabel2;
    @FXML
    Label shelfLabel3;
    @FXML
    Label shelfLabel4;
    @FXML
    GridPane shelf1GridPane;
    @FXML
    GridPane shelf2GridPane;
    @FXML
    GridPane shelf3GridPane;
    @FXML
    GridPane shelf4GridPane;
    @FXML
    HBox limboHBoxArea;
    @FXML
    StackPane personalGoalCardPane;
    @FXML
    Label pointLabel;
    @FXML
    VBox usernameList;
    @FXML
    FlowPane zoomPanel;
    @FXML
    BorderPane infoPanel;
    @FXML
    Pane playersNamePaneArea;
    @FXML
    BorderPane actionPanel;
    @FXML
    HBox commonGoalCardsHBoxArea;
    @FXML
    AnchorPane columnArrowAnchorPane;
    @FXML
    ImageView arrowShelf1;
    @FXML
    ImageView arrowShelf2;
    @FXML
    ImageView arrowShelf3;
    @FXML
    ImageView arrowShelf4;
    @FXML
    ImageView arrowShelf5;
    @FXML
    ImageView transparentImage1;
    @FXML
    ImageView transparentImage2;
    @FXML
    ImageView transparentImage3;
    @FXML
    ImageView scoring81;
    @FXML
    ImageView scoring82;
    @FXML
    ImageView endGameTokenImage;
    @FXML
    Label namePlayer1;
    @FXML
    Label namePlayer2;
    @FXML
    Label namePlayer3;
    @FXML
    Label namePlayer4;
    @FXML
    Label pointsPlayer1;
    @FXML
    Label pointsPlayer2;
    @FXML
    Label pointsPlayer3;
    @FXML
    Label pointsPlayer4;


    private GuiManager guiManager;
    private Map<String, ImageView> objectCards;
    private Map<String, ImageView> commonGoalCards;
    private Map<String, ImageView> personalGoalCards;
    private List<GridPane> shelves;

    @FXML
    private void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setGameSceneController(this);

        shelfLabel1.setText("Federico");
        shelfLabel2.setText("Matteo");
        shelfLabel3.setText("Federica");
        shelfLabel4.setText("Simone");

        objectCards = new HashMap<>();
        commonGoalCards = new HashMap<>();
        personalGoalCards = new HashMap<>();
        shelves = new ArrayList<>();
    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {
        bindCommonGoalCardInfoPanelZoom();
        bindPanels();

        //aggiungere aggiornamento punteggi
        updateGameArea(gameSerialized);
        setPersonalGoalCard(gameSerialized.getPersonalGoalCard());
        loadObjectCards();
        loadCommonGoalCards();
        loadPersonalGoalCards();
    }

    /**
     * Binds click events on the panels
     */
    private void bindPanels() {
        zoomPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideZoomPanel());
    }

    /**
     * Binds common goal info zoom on card click
     */
    private void bindCommonGoalCardInfoPanelZoom() {
        for (ImageView commonGoalCard : commonGoalCards.values()) {
            commonGoalCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showCommonGoalCardInfoPanelZoom);
        }
    }

    private void loadObjectCards() {
        List<String> types = Arrays.stream(ObjectCardType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (int i = 0; i < ObjectCardType.SIZE; i++) {
            addObjectCardImagesToMap(types.get(i), "0", 7);
            addObjectCardImagesToMap(types.get(i), "1", 7);
            addObjectCardImagesToMap(types.get(i), "2", 8);
        }
    }

    private void addObjectCardImagesToMap(String type, String ID, int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView();
            String id = Character.toUpperCase(type.charAt(0)) + type.substring(1) + "-" + ID;
            imageView.getStyleClass().add(CSS_BUTTON);
            imageView.setId(id);
            objectCards.put(imageView.getId(), imageView);
        }
    }

    private void loadCommonGoalCards() {
        for (int i = 1; i <= 12; i++) {
            ImageView imageView = new ImageView();
            String id = "commonGoalCard-" + i;
            imageView.getStyleClass().add(CSS_BUTTON);
            imageView.setId(id);
            commonGoalCards.put(imageView.getId(), imageView);
        }
    }

    private void loadPersonalGoalCards() {
        for (int i = 1; i <= 12; i++) {
            ImageView imageView = new ImageView();
            String id = "personalGoalCard-" + i;
            imageView.getStyleClass().add(CSS_BUTTON);
            imageView.setId(id);
            personalGoalCards.put(imageView.getId(), imageView);
        }
    }

    /**
     * Adds the object cards to the board
     *
     * @param gameSerialized state of the game at the time of the join
     */
    private void setBoard(GameSerialized gameSerialized) {
        Board board = gameSerialized.getBoard();

        ObjectCard objectCard;
        JsonReader.readJsonConstant("GameConstant.json");
        List<Player> players = gameSerialized.getAllPlayers();
        int playerNumber = players.size();
        int[][] boardMatrix = JsonReader.getBoard(playerNumber);

        for (int i = 0; i < boardMatrix.length / 2; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                if (boardMatrix[i][j] == 1) {
                    objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                    if (objectCard != null) {
                        String cardTypeText = objectCard.getType().getText();
                        String cardNameType = cardTypeText + "-" + objectCard.getId();

                        ImageView imageView = objectCards.get(cardNameType);
                        if (imageView != null) {
                            imageView.setFitWidth(BOARD_OBJECT_CARD_WIDTH);
                            imageView.setFitHeight(BOARD_OBJECT_CARD_HEIGHT);
                            imageView.setPreserveRatio(true);
                            imageView.setPickOnBounds(true);

                            boardGridPane.add(imageView, i, j);
                        }
                    }
                }
            }
        }
    }

    void setCommonGoalCards(List<CommonGoal> commonGoals) {
        for (int i = 0; i < commonGoals.size(); i++) {
            String cardTypeText = commonGoals.get(i).toString();
            ImageView imageView = commonGoalCards.get(cardTypeText);

            if (imageView != null) {
                imageView.setFitWidth(COMMONGOAL_CARD_WIDTH);
                imageView.setFitHeight(COMMONGOAL_CARD_HEIGHT);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);

                if (i == 0) {
                    commonGoalCard1StackPane.getChildren().add(imageView);
                    imageView.toBack();
                } else if (i == 1) {
                    commonGoalCard2StackPane.getChildren().add(imageView);
                    imageView.toBack();
                }
            }
        }
    }

    private void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        String cardTypeText = personalGoalCard.getID();
        ImageView imageView = personalGoalCards.get(cardTypeText);

        if (imageView != null) {
            imageView.setFitWidth(PERSONALGOAL_CARD_WIDTH);
            imageView.setFitHeight(PERSONALGOAL_CARD_HEIGHT);
            imageView.setTranslateY(PERSONALGOAL_CARD_TRANSLATE_Y);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
            StackPane.setAlignment(imageView, Pos.CENTER);

            personalGoalCardPane.getChildren().add(imageView);
        }
    }

    void setShelves(GameSerialized gameSerialized) {
        int i = 0;
        List<Player> players = gameSerialized.getAllPlayers();
        String myName = guiManager.getUsername();

        StackPane myStackPane = new StackPane();
        myStackPane.setId("myStackPane");
        shelfHBoxImages.getChildren().add(myStackPane);

        ImageView myShelf = new ImageView(SHELF_PATH);
        myShelf.setId("myShelfImageView");
        myShelf.setFitWidth(SHELF_WIDTH);
        myShelf.setFitHeight(SHELF_HEIGHT);
        myShelf.getStyleClass().add(CSS_SHELF);
        myShelf.setPreserveRatio(true);
        myShelf.setPickOnBounds(true);
        StackPane.setAlignment(myShelf, Pos.CENTER);
        myStackPane.getChildren().add(myShelf);

        GridPane myShelfGridPane = new GridPane();
        myShelfGridPane.setId("myShelfGridPane");
        myShelfGridPane.setHgap(17.0);
        myShelfGridPane.setVgap(9.0);
        myShelfGridPane.setMaxHeight(324.0);
        myShelfGridPane.setMaxWidth(304.0);
        myShelfGridPane.setTranslateY(-11.0);
        myShelfGridPane.getStyleClass().add(CSS_SHELF_GRIDPANE);
        myStackPane.getChildren().add(myShelfGridPane);
        myShelfGridPane.toFront();

        Label myNameLabel = new Label(myName);
        myNameLabel.setId("myNameLabel");
        myNameLabel.getStyleClass().add(CSS_SHELF_LABEL);
        StackPane.setAlignment(myNameLabel, Pos.BOTTOM_CENTER);
        myStackPane.getChildren().add(myNameLabel);
        myNameLabel.toFront();

        shelves.add(myShelfGridPane);

        // Aggiungere le object card

        for(Player player : players) {
            if(!player.getName().equals(myName)) {
                StackPane stackPane = new StackPane();
                stackPane.setId("stackPane" + i);
                shelfHBoxImages.getChildren().add(stackPane);

                ImageView imageView = new ImageView(SHELF_PATH);
                imageView.setId("shelfImageView" + i);
                imageView.setFitWidth(SHELF_WIDTH);
                imageView.setFitHeight(SHELF_HEIGHT);
                imageView.getStyleClass().add(CSS_SHELF);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);
                StackPane.setAlignment(imageView, Pos.CENTER);
                stackPane.getChildren().add(imageView);

                GridPane gridPane = new GridPane();
                gridPane.setId("shelfGridPane" + i);
                gridPane.setHgap(17.0);
                gridPane.setVgap(9.0);
                gridPane.setMaxHeight(324.0);
                gridPane.setMaxWidth(304.0);
                gridPane.setTranslateY(-11.0);
                gridPane.getStyleClass().add(CSS_SHELF_GRIDPANE);
                stackPane.getChildren().add(gridPane);
                gridPane.toFront();

                Label playerNameLabel = new Label(player.getName());
                playerNameLabel.setId("playerNameLabel" + i);
                playerNameLabel.getStyleClass().add(CSS_SHELF_LABEL);
                StackPane.setAlignment(playerNameLabel, Pos.BOTTOM_CENTER);
                stackPane.getChildren().add(playerNameLabel);
                playerNameLabel.toFront();

                shelves.add(gridPane);

                // Aggiungere le object card

                i++;
            }
        }
    }

    void setLimbo(GameSerialized gameSerialized) {
        List<ObjectCard> limboCards = gameSerialized.getAllLimboCards();

        if(!limboCards.isEmpty()){
            for(ObjectCard objectCard : limboCards) {
                if(objectCard != null) {
                    String cardTypeText = objectCard.getType().getText();
                    String cardNameType = cardTypeText + "-" + objectCard.getId();
                    ImageView imageView = objectCards.get(cardNameType);

                    if (imageView != null) {
                        imageView.setFitWidth(LIMBO_OBJECT_CARD_WIDTH);
                        imageView.setFitHeight(LIMBO_OBJECT_CARD_HEIGHT);
                        imageView.setPreserveRatio(true);
                        imageView.setPickOnBounds(true);

                        limboHBoxArea.getChildren().add(imageView);
                    }
                }

            }
        }

    }

    // Da completare
    void setPlayerInfo(GameSerialized gameSerialized) {
        List<Player> players = gameSerialized.getAllPlayers();
        String myName = guiManager.getUsername();

        Label myNameLabel = new Label(myName);
        Label myPointsLabel = new Label();

        for(Player player : players) {
            if(!player.getName().equals(myName)) {
                Label playerNameLabel = new Label(player.getName());
                Label playerPointsLabel = new Label();
            }
        }
    }


    /**
     * Hides the zoom panel
     */
    private void hideZoomPanel() {
        zoomPanel.getChildren().clear();
        zoomPanel.setVisible(false);

        setBoardOpaque(NOT_OPAQUE);
    }

    /**
     * Sets an opacity value for every element on the board
     *
     * @param value opacity value
     */
    private void setBoardOpaque(double value) {
        boardStackPaneArea.opacityProperty().setValue(value);

//        for (ImageView ammotile : ammoTiles) {
//            ammotile.opacityProperty().setValue(value);
//        }
//
//        for (ImageView playerFigure : playerFigures) {
//            playerFigure.opacityProperty().setValue(value);
//        }
//
//        for (ImageView killshots : killshotsImages) {
//            killshots.opacityProperty().setValue(value);
//        }
//
//        for (Node node : actionList.getChildren()) {
//            node.opacityProperty().setValue(value);
//        }
//
//        for (Node node : iconList.getChildren()) {
//            node.opacityProperty().setValue(value);
//        }
    }

    /**
     * Shows the zoom on a weapon in the zoom panel
     *
     * @param event of the click on a weapon
     */
    private void showCommonGoalCardInfoPanelZoom(Event event) {
//        ImageView commonGoalCardTarget = (ImageView) event.getTarget();
//
//        if (commonGoalCardTarget != null) {
//            setBoardOpaque(OPAQUE);
//
//            zoomPanel.toFront();
//            ImageView commonGoalCard = new ImageView(commonGoalCardTarget.getImage());
//
//            String commonGoalCardType = commonGoalCards.getKey(commonGoalCard.getId());
//            if (color != null) {
//                String className = null;
//
//                switch (color) {
//                    case BLUE:
//                        className = "weaponZoomImageBlue";
//                        break;
//                    case RED:
//                        className = "weaponZoomImageRed";
//                        break;
//                    case YELLOW:
//                        className = "weaponZoomImageYellow";
//                        break;
//                }
//
//                commonGoalCard.getStyleClass().add(className);
//
//                zoomPanel.getChildren().add(commonGoalCard);
//                zoomPanel.setVisible(true);
//                zoomPanel.toFront();
//            }
//        }
    }

    /**
     * Updates the elements of the board
     */
    void onStateUpdate() {
        updateGameArea(guiManager.getGameSerialized());
        //aggiungere aggiornamento punteggi
    }

    /**
     * Updates element on the game area
     *
     * @param gameSerialized game update
     */
    private void updateGameArea(GameSerialized gameSerialized) {
        updateBoard(gameSerialized);
        updateShelves(gameSerialized);
        // Aggiungere altri elementi da aggiornare

    }

    /**
     * Updates element on the board
     *
     */
    private void updateBoard(GameSerialized gameSerialized) {
        ObservableList<Node> children = boardGridPane.getChildren();
        children.clear();
        setBoard(gameSerialized);
    }

    /**
     * Updates element on the shelves
     *
     */
    private void updateShelves(GameSerialized gameSerialized) {
        for(GridPane shelf : shelves) {
            ObservableList<Node> children = shelf.getChildren();
            children.clear();
            setShelves(gameSerialized);
        }
    }

    /**
     * Called when an error occurs. Displays an alert with the error message
     *
     * @param error message of the error
     */
    void onError(String error) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, error);
    }

    // per impedire che un giocatore non di turno possa compiere azioni
    void notYourTurn(String turnOwner) {
        mainPane.setMouseTransparent(true);
    }

    /**
     * Displays action buttons
     *
     * @param possibleActions possible actions
     */
    void displayAction(List<PossibleAction> possibleActions) {

    }

    /**
     * Returns the CSS ID of the action based on the PossibleAction
     *
     * @param possibleAction possible action passed
     * @return the CSS ID
     */
    private String getActionIDFromPossibleAction(PossibleAction possibleAction) {
//        switch (possibleAction) {
//
//        }
        return null;
    }

    /**
     * Handles the pass turn
     */
    //TODO: non c'è buildPassTurnRequest in MessageBuilder? Forse non serve
    void passTurn() {
//        if (!guiManager.sendRequest(MessageBuilder.buildPassTurnRequest(guiManager.getClientToken(), guiManager.getPlayer()))) {
//            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, GuiManager.SEND_ERROR);
//        }
    }

    //TODO: Può servire per selezionare le objectcards dalla board
//    private void sendPickRequest(final PlayerPosition pickPosition, final WeaponCard weaponCard, final ArrayList<Integer> paymentPowerups, final WeaponCard discardingWeapon) {
//        hideActionPanel();
//
//        try {
//            if (!guiManager.sendRequest(MessageBuilder.buildMovePickRequest(guiManager.getClientToken(), guiManager.getPlayer(), pickPosition, paymentPowerups, weaponCard, discardingWeapon))) {
//                GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, GuiManager.SEND_ERROR);
//            }
//        } catch (
//                PowerupCardsNotFoundException e) {
//            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, e.getMessage());
//        }
//    }

    /**
     * Communicates the disconnection of a player
     *
     * @param player username of a player who disconnected
     */
    void onPlayerDisconnection(String player) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), "Disconnection", player + " disconnected from the server");
    }

    /**
     * Communicates the reconnection of a player
     *
     */
    void onPlayerReconnection(String message) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), "Reconnection", message);
    }

    /**
     * Handles the game end
     *
     * @param players player of the leaderboard
     */
    void onGameEnd(List<Player> players) {
        EndGameSceneController endGameSceneController = GuiManager.setLayout(mainPane.getScene(), "fxml/endGameScene.fxml");

        if (endGameSceneController != null) {
            endGameSceneController.setData(players);
        }
    }

    /**
     * Handles the disconnection
     */
    void onDisconnection() {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), "Disconnection", "You were disconnected from the server");
    }
}