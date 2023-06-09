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

//TODO : Vedere differenza tra ID e classe in CSS

/**
 * Class for the graphical interface of the game
 */
public class GameSceneController {
    private static final String USERNAME_PROPERTY = "username";
    private static final double OPAQUE = 0.2;
    private static final double NOT_OPAQUE = 1;
    private static final String CSS_BUTTON = "button";
    private static final String CSS_SQUARE_CLICK_BUTTON = "squareClickButton";
    private static final String CSS_SQUARE_OWNER_CLICK_BUTTON = "squareOwnerClickButton";

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
    ImageView personalGoalCard;
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
    //TODO : Perché le definizioni di tipo di pane posso differire tra controller e FXML?
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
    ImageView book1;
    @FXML
    ImageView book2;
    @FXML
    ImageView book3;
    @FXML
    ImageView book4;
    @FXML
    ImageView book5;
    @FXML
    ImageView book6;
    @FXML
    ImageView book7;
    @FXML
    ImageView book8;
    @FXML
    ImageView book9;
    @FXML
    ImageView book10;
    @FXML
    ImageView book11;
    @FXML
    ImageView book12;
    @FXML
    ImageView book13;
    @FXML
    ImageView book14;
    @FXML
    ImageView book15;
    @FXML
    ImageView book16;
    @FXML
    ImageView book17;
    @FXML
    ImageView book18;
    @FXML
    ImageView book19;
    @FXML
    ImageView book20;
    @FXML
    ImageView book21;
    @FXML
    ImageView book22;
    @FXML
    ImageView book23;
    @FXML
    ImageView book24;
    @FXML
    ImageView book25;
    @FXML
    ImageView book26;
    @FXML
    ImageView book27;
    @FXML
    ImageView book28;
    @FXML
    ImageView book29;
    @FXML
    ImageView book30;
    @FXML
    ImageView book31;
    @FXML
    ImageView book32;
    @FXML
    ImageView book33;
    @FXML
    ImageView book34;
    @FXML
    ImageView game1;
    @FXML
    ImageView game2;
    @FXML
    ImageView game3;
    @FXML
    ImageView game4;
    @FXML
    ImageView game5;
    @FXML
    ImageView game6;
    @FXML
    ImageView plant1;
    @FXML
    ImageView plant2;
    @FXML
    ImageView plant3;
    @FXML
    ImageView plant4;
    @FXML
    ImageView plant5;
    @FXML
    ImageView trophy1;
    @FXML
    ImageView trophy2;
    @FXML
    ImageView trophy3;
    @FXML
    ImageView trophy4;
    @FXML
    ImageView trophy5;
    @FXML
    ImageView trophy6;
    @FXML
    ImageView cat1;
    @FXML
    ImageView cat2;
    @FXML
    ImageView cat3;
    @FXML
    ImageView cat4;
    @FXML
    ImageView cat5;
    @FXML
    ImageView cat6;
    @FXML
    ImageView frame1;
    @FXML
    ImageView frame2;
    @FXML
    ImageView frame3;
    @FXML
    ImageView frame4;
    @FXML
    ImageView frame5;
    @FXML
    ImageView commonGoal1;
    @FXML
    ImageView commonGoal2;
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
    private  Map<String, ImageView> objectCards;
    private Map<String, ImageView> commonGoalCards;

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

        loadObjectCards();
        loadCommonGoalCards();
    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {
//        addObjectCards(gameSerialized);
        bindCommonGoalCardInfoPanelZoom();
        bindPanels();
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

    private void addObjectCardImagesToMap(String type, String level, int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView();
            String id = Character.toUpperCase(type.charAt(0)) + type.substring(1) + "-" + level;
            imageView.getStyleClass().add("button");
            imageView.setId(id);
            objectCards.put(imageView.getId(), imageView);
        }
    }

    private void loadCommonGoalCards() {
        ImageView imageView = new ImageView();
        String id1 = "commonGoalCard-1";
        imageView.getStyleClass().add("button");
        imageView.setId(id1);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id2 = "commonGoalCard-2";
        imageView.getStyleClass().add("button");
        imageView.setId(id2);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id3 = "commonGoalCard-3";
        imageView.getStyleClass().add("button");
        imageView.setId(id3);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id4 = "commonGoalCard-4";
        imageView.getStyleClass().add("button");
        imageView.setId(id4);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id5 = "commonGoalCard-5";
        imageView.getStyleClass().add("button");
        imageView.setId(id5);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id6 = "commonGoalCard-6";
        imageView.getStyleClass().add("button");
        imageView.setId(id6);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id7 = "commonGoalCard-7";
        imageView.getStyleClass().add("button");
        imageView.setId(id7);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id8 = "commonGoalCard-8";
        imageView.getStyleClass().add("button");
        imageView.setId(id8);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id9 = "commonGoalCard-9";
        imageView.getStyleClass().add("button");
        imageView.setId(id9);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id10 = "commonGoalCard-10";
        imageView.getStyleClass().add("button");
        imageView.setId(id10);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id11 = "commonGoalCard-11";
        imageView.getStyleClass().add("button");
        imageView.setId(id11);
        commonGoalCards.put(imageView.getId(), imageView);

        imageView = new ImageView();
        String id12 = "commonGoalCard-12";
        imageView.getStyleClass().add("button");
        imageView.setId(id12);
        commonGoalCards.put(imageView.getId(), imageView);
    }

    /**
     * Adds the object cards to the board
     *
     * @param gameSerialized state of the game at the time of the join
     */
    private void addObjectCards(GameSerialized gameSerialized) {
        Board board = guiManager.getGameSerialized().getBoard();

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
                        String cardTypeText = objectCard.toString();
                        String visibleCardTypeText = cardTypeText.replaceAll("\u001B\\[[;\\d]*m", "");
                        String cardNameType = visibleCardTypeText + "-" + objectCard.getId();

                        ImageView imageView = objectCards.get(cardNameType);
                        if (imageView != null) {
                            imageView.setFitWidth(60);
                            imageView.setFitHeight(60);
                            imageView.setPreserveRatio(true);
                            imageView.setPickOnBounds(true);

                            boardGridPane.add(imageView, i, j);  // aggiunge l'immagine alla cella (j, i) del GridPane
                        }
                    }
                }
            }
        }
    }

    void addCommonGoalCards(List<CommonGoal> commonGoals) {
        String cardTypeText0 = commonGoals.get(0).toString();

        ImageView imageView0 = commonGoalCards.get(cardTypeText0);
        if (imageView0 != null) {
            imageView0.setFitWidth(138.5);
            imageView0.setFitHeight(91.3);
            imageView0.setPreserveRatio(true);
            imageView0.setPickOnBounds(true);

            commonGoalCard1StackPane.getChildren().add(imageView0);
        }

        String cardTypeText1 = commonGoals.get(1).toString();

        ImageView imageView1 = commonGoalCards.get(cardTypeText1);
        if (imageView1 != null) {
            imageView1.setFitWidth(138.5);
            imageView1.setFitHeight(91.3);
            imageView1.setPreserveRatio(true);
            imageView1.setPickOnBounds(true);

            commonGoalCard2StackPane.getChildren().add(imageView1);
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
     * Sets a opacity value for every element on the board
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
        updateBoard();
    }

    /**
     * Updates element on the board
     *
     */
    private void updateBoard() {
//        ObservableList<Node> childrens = boardGridPane.getChildren();
//        childrens.clear();
//        addObjectCards(guiManager.getGameSerialized());
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
    void onPlayerDisconnect(String player) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), "Disconnection", player + " disconnected from the server");
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