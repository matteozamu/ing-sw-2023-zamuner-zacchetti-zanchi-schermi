package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.PossibleAction;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.MessageBuilder;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for the graphical interface of the game
 */
public class GameSceneController {
    private static final String USERNAME_PROPERTY = "username";
    private static final String POINTS_PROPERTY = "Points: ";
    private static final String TRANSPARENT_IMAGEVIEW_ID = "transparent";
    private static final String CSS_BUTTON = "button";
    private static final String CSS_SHELF = "shelf";
    private static final String CSS_SHELF_GRIDPANE = "shelfGridPane";
    private static final String CSS_SHELF_LABEL = "shelfLabel";
    private static final String CSS_PLAYERINFO_LABEL = "playerInfo";
    private static final String CSS_PLAYERINFO_SEPARATOR = "labelSeparator";
    private static final double OPAQUE = 0.2;
    private static final double NOT_OPAQUE = 1;
    private static final double COMMONGOAL_CARD_WIDTH = 138.5;
    private static final double COMMONGOAL_CARD_HEIGHT = 91.3;
    private static final double BOARD_OBJECT_CARD_WIDTH = 60.0;
    private static final double BOARD_OBJECT_CARD_HEIGHT = 60.0;
    private static final double SHELF_OBJECT_CARD_WIDTH = 47.0;
    private static final double SHELF_OBJECT_CARD_HEIGHT = 47.0;
    private static final double PERSONALGOAL_CARD_WIDTH = 137.0;
    private static final double PERSONALGOAL_CARD_HEIGHT = 207.9;
    private static final double PERSONALGOAL_CARD_TRANSLATE_Y = 51.5;
    private static final double SHELF_WIDTH = 400.0;
    private static final double SHELF_HEIGHT = 400.0;
    private static final double LIMBO_OBJECT_CARD_WIDTH = 75.0;
    private static final double LIMBO_OBJECT_CARD_HEIGHT = 75.0;
    private static final String SHELF_PATH = "/img/board_shelf/shelf_orth.png";
    private static final double ACTION_BUTTON_WIDTH = 190.0;
    private static final double ACTION_BUTTON_HEIGHT = 70.0;

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
    VBox playersInfoVBox;
    @FXML
    StackPane myStackPane;
    @FXML
    StackPane shelfStackPane2;
    @FXML
    StackPane shelfStackPane3;
    @FXML
    StackPane shelfStackPane4;
    @FXML
    StackPane actionListStackPane;
    @FXML
    ImageView winnerTile;
    @FXML
    ImageView pointsTile;
    @FXML
    HBox limboHBoxArea;
    @FXML
    StackPane personalGoalCardPane;
    @FXML
    FlowPane zoomPanel;
    @FXML
    BorderPane infoPanel;
    @FXML
    BorderPane actionPanel;
    @FXML
    HBox commonGoalCardsHBoxArea;
    @FXML
    HBox shelfHBoxImages;
    @FXML
    AnchorPane columnArrowAnchorPane;
    @FXML
    ImageView arrowShelf0;
    @FXML
    ImageView arrowShelf1;
    @FXML
    ImageView arrowShelf2;
    @FXML
    ImageView arrowShelf3;
    @FXML
    ImageView arrowShelf4;
    @FXML
    ImageView scoring81;
    @FXML
    ImageView scoring82;
    @FXML
    ImageView endGameTokenImage;

    private GuiManager guiManager;
    private Map<String, ImageView> objectCards;
    private Map<String, ImageView> commonGoalCards;
    private Map<String, ImageView> personalGoalCards;
    private List<GridPane> shelvesGridPane;
    private List<StackPane> shelvesStackPane;
    private ArrayList<Integer> orderLimboObjectCards;
    private Set<ImageView> imageViewsWithListener;

    @FXML
    private void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setGameSceneController(this);

        limboHBoxArea.setVisible(true);

        objectCards = new HashMap<>();
        commonGoalCards = new HashMap<>();
        personalGoalCards = new HashMap<>();
        shelvesGridPane = new ArrayList<>();
        orderLimboObjectCards = new ArrayList<>();
        imageViewsWithListener = new HashSet<>();
        shelvesStackPane = List.of(shelfStackPane2, shelfStackPane3, shelfStackPane4);

//        arrowShelf0.setMouseTransparent(true);
//        arrowShelf1.setMouseTransparent(true);
//        arrowShelf2.setMouseTransparent(true);
//        arrowShelf3.setMouseTransparent(true);
//        arrowShelf4.setMouseTransparent(true);
//        boardGridPane.setMouseTransparent(true);
//        //impostare il limbo con la scritta LIMBO
//        myStackPane.setMouseTransparent(true);
//        shelfStackPane2.setMouseTransparent(true);
//        shelfStackPane3.setMouseTransparent(true);
//        shelfStackPane4.setMouseTransparent(true);

        loadObjectCards();
        loadCommonGoalCards();
        loadPersonalGoalCards();
    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {
        bindCommonGoalCardInfoPanelZoom();
        //bindPanels();
        bindChooseColumn();

        setShelves(gameSerialized);
        setPersonalGoalCard(gameSerialized.getPersonalGoalCard());
        setPlayerInfo(gameSerialized);
        updateGameArea(gameSerialized);
    }

    private void bindChooseColumn() {
        arrowShelf0.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onChooseColumnButtonClick(0));
        arrowShelf1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onChooseColumnButtonClick(1));
        arrowShelf2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onChooseColumnButtonClick(2));
        arrowShelf3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onChooseColumnButtonClick(3));
        arrowShelf4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onChooseColumnButtonClick(4));
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
            String id = Character.toUpperCase(type.charAt(0)) + type.substring(1) + "-" + ID + i;
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
        int numRows = 9;
        int numCols = 9;

        ObjectCard objectCard;
        int[][] boardMatrix = gameSerialized.getBoardMatrix();

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

                            int row = numRows/2 - (4 - i);
                            int col = (j - 4) + numCols/2;
                            boardGridPane.add(imageView, col, row);
                            int finalI = i;
                            int finalJ = j;

                            if (!imageViewsWithListener.contains(imageView)) {
                                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onObjectCardClick(4 - finalI, finalJ - 4));
                                imageViewsWithListener.add(imageView);
                            }
                        }
                    }
                } else if (boardMatrix[i][j] == 0) {
                    ImageView transparentImageView = new ImageView();
                    transparentImageView.setFitWidth(BOARD_OBJECT_CARD_WIDTH);
                    transparentImageView.setFitHeight(BOARD_OBJECT_CARD_HEIGHT);
                    transparentImageView.setPreserveRatio(true);
                    transparentImageView.setPickOnBounds(true);
                    transparentImageView.setId(TRANSPARENT_IMAGEVIEW_ID);

                    int row = numRows/2 - (4 - i);
                    int col = (j - 4) + numCols/2;
                    boardGridPane.add(transparentImageView, col, row);
                }
            }
        }

        for (int i = boardMatrix.length / 2; i < boardMatrix.length; i++) {
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

                            int row = numRows/2 - (4 - i);
                            int col = (j - 4) + numCols/2;
                            boardGridPane.add(imageView, col, row);
                            int finalI = i;
                            int finalJ = j;

                            if (!imageViewsWithListener.contains(imageView)) {
                                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onObjectCardClick(4 - finalI, finalJ - 4));
                                imageViewsWithListener.add(imageView);
                            }
                        }
                    }
                } else if (boardMatrix[i][j] == 0) {
                    ImageView transparentImageView = new ImageView();
                    transparentImageView.setFitWidth(BOARD_OBJECT_CARD_WIDTH);
                    transparentImageView.setFitHeight(BOARD_OBJECT_CARD_HEIGHT);
                    transparentImageView.setPreserveRatio(true);
                    transparentImageView.setPickOnBounds(true);
                    transparentImageView.setId(TRANSPARENT_IMAGEVIEW_ID);

                    int row = numRows/2 - (4 - i);
                    int col = (j - 4) + numCols/2;
                    boardGridPane.add(transparentImageView, col, row);
                }
            }
        }
    }

    private void updateShelfGrid(GameSerialized gameSerialized, GridPane shelfGrid, String shelfOwner) {
        List<Player> players = gameSerialized.getAllPlayers();
        Shelf onWorkShelf = players.stream()
                .filter(player -> player.getName().equals(shelfOwner))
                .map(Player::getShelf)
                .findFirst()
                .orElse(null);

        for (int row = Shelf.ROWS - 1; row >= 0; row--) {
            for (int col = 0; col < Shelf.COLUMNS; col++) {
                Coordinate coord = new Coordinate(row, col);
                ObjectCard objectCard = onWorkShelf.getObjectCard(coord);
                if (objectCard == null) {
                    ImageView transparentImageView = new ImageView();
                    transparentImageView.setFitWidth(SHELF_OBJECT_CARD_WIDTH);
                    transparentImageView.setFitHeight(SHELF_OBJECT_CARD_HEIGHT);
                    transparentImageView.setPreserveRatio(true);
                    transparentImageView.setPickOnBounds(true);
                    transparentImageView.setId(TRANSPARENT_IMAGEVIEW_ID);

                    shelfGrid.add(transparentImageView, col, Shelf.ROWS - 1 - row);
                } else {
                    String cardTypeText = objectCard.getType().getText();
                    String cardNameType = cardTypeText + "-" + objectCard.getId();

                    ImageView imageView = objectCards.get(cardNameType);
                    if (imageView != null) {
                        imageView.setFitWidth(SHELF_OBJECT_CARD_WIDTH);
                        imageView.setFitHeight(SHELF_OBJECT_CARD_HEIGHT);
                        imageView.setPreserveRatio(true);
                        imageView.setPickOnBounds(true);
                        imageView.setMouseTransparent(true);

                        shelfGrid.add(imageView, col, Shelf.ROWS - 1 - row);
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
        myShelfGridPane.getProperties().put(USERNAME_PROPERTY, myName);
        myStackPane.getChildren().add(myShelfGridPane);
        myShelfGridPane.toFront();

        Label myNameLabel = new Label(myName);
        myNameLabel.setId("myNameLabel");
        myNameLabel.getStyleClass().add(CSS_SHELF_LABEL);
        StackPane.setAlignment(myNameLabel, Pos.BOTTOM_CENTER);
        myStackPane.getChildren().add(myNameLabel);
        myNameLabel.toFront();

        shelvesGridPane.add(myShelfGridPane);

        for (Player player : players) {
            if (!player.getName().equals(myName)) {
                StackPane playerStackPane = shelvesStackPane.get(i);

                ImageView imageView = new ImageView(SHELF_PATH);
                imageView.setId("shelfImageView" + i);
                imageView.setFitWidth(SHELF_WIDTH);
                imageView.setFitHeight(SHELF_HEIGHT);
                imageView.getStyleClass().add(CSS_SHELF);
                imageView.setPreserveRatio(true);
                imageView.setPickOnBounds(true);
                StackPane.setAlignment(imageView, Pos.CENTER);
                playerStackPane.getChildren().add(imageView);

                GridPane gridPane = new GridPane();
                gridPane.setId("shelfGridPane" + i);
                gridPane.setHgap(17.0);
                gridPane.setVgap(9.0);
                gridPane.setMaxHeight(324.0);
                gridPane.setMaxWidth(304.0);
                gridPane.setTranslateY(-11.0);
                gridPane.getStyleClass().add(CSS_SHELF_GRIDPANE);
                gridPane.getProperties().put(USERNAME_PROPERTY, player.getName());
                playerStackPane.getChildren().add(gridPane);
                gridPane.toFront();

                Label playerNameLabel = new Label(player.getName());
                playerNameLabel.setId("playerNameLabel" + i);
                playerNameLabel.getStyleClass().add(CSS_SHELF_LABEL);
                StackPane.setAlignment(playerNameLabel, Pos.BOTTOM_CENTER);
                playerStackPane.getChildren().add(playerNameLabel);
                playerNameLabel.toFront();

                shelvesGridPane.add(gridPane);

                i++;
            }
        }
    }

    void setLimbo(GameSerialized gameSerialized) {
        List<ObjectCard> limboCards = gameSerialized.getAllLimboCards();
        limboHBoxArea.getChildren().clear();

        if (!limboCards.isEmpty()) {
            for (int i = 0; i < limboCards.size(); i++) {
                ObjectCard objectCard = limboCards.get(i);
                if (objectCard != null) {
                    String cardTypeText = objectCard.getType().getText();
                    String cardNameType = cardTypeText + "-" + objectCard.getId();
                    ImageView imageView = objectCards.get(cardNameType);

                    if (imageView != null) {
                        imageView.setFitWidth(LIMBO_OBJECT_CARD_WIDTH);
                        imageView.setFitHeight(LIMBO_OBJECT_CARD_HEIGHT);
                        imageView.setPreserveRatio(true);
                        imageView.setPickOnBounds(true);
                        //imageView.setMouseTransparent(false);

                        limboHBoxArea.getChildren().add(imageView);

                        int finalI = i;
                        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onObjectCardInLimboClick(finalI, limboCards));
                    }
                }

            }
        }
    }

    private void onObjectCardInLimboClick(int index, List<ObjectCard> limboCards) {
        orderLimboObjectCards.add(index);

        if(orderLimboObjectCards.size() == limboCards.size()) {
            onReorderLimboRequest(orderLimboObjectCards);
        }
    }

    void setPlayerInfo(GameSerialized gameSerialized) {
        playersInfoVBox.getChildren().clear();

        int i = 0;
        List<Player> players = gameSerialized.getAllPlayers();
        String myName = guiManager.getUsername();
        String myPoints = POINTS_PROPERTY + gameSerialized.getPoints();

        Separator upSeparator = new Separator();
        upSeparator.getStyleClass().add(CSS_PLAYERINFO_SEPARATOR);
        playersInfoVBox.getChildren().add(upSeparator);

        Label myNameLabel = new Label(myName);
        myNameLabel.setId("myNameLabel");
        myNameLabel.getStyleClass().add(CSS_PLAYERINFO_LABEL);
        playersInfoVBox.getChildren().add(myNameLabel);

        Label myPointsLabel = new Label(myPoints);
        myPointsLabel.setId("myPointsLabel");
        myPointsLabel.getStyleClass().add(CSS_PLAYERINFO_LABEL);
        playersInfoVBox.getChildren().add(myPointsLabel);

        Separator downSeparator = new Separator();
        downSeparator.getStyleClass().add(CSS_PLAYERINFO_SEPARATOR);
        playersInfoVBox.getChildren().add(downSeparator);

        for (Player player : players) {
            if (!player.getName().equals(myName)) {
                String playerName = player.getName();
                String playerPoints = POINTS_PROPERTY + player.getCurrentPoints();

                Label playerNameLabel = new Label(playerName);
                playerNameLabel.setId("playerNameLabel" + i);
                playerNameLabel.getStyleClass().add(CSS_PLAYERINFO_LABEL);
                playersInfoVBox.getChildren().add(playerNameLabel);

                Label playerPointsLabel = new Label(playerPoints);
                playerPointsLabel.setId("playerPointsLabel" + i);
                playerPointsLabel.getStyleClass().add(CSS_PLAYERINFO_LABEL);
                playersInfoVBox.getChildren().add(playerPointsLabel);

                Separator separator2 = new Separator();
                separator2.getStyleClass().add(CSS_PLAYERINFO_SEPARATOR);
                playersInfoVBox.getChildren().add(separator2);

                i++;
            }
        }
    }

    private void onChooseColumnButtonClick(int column) {
        if (!guiManager.sendRequest(MessageBuilder.buildLoadShelfRequest(guiManager.getClientToken(), guiManager.getUsername(), column))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);
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
    }

    /**
     * Updates element on the game area
     *
     * @param gameSerialized game update
     */
    private void updateGameArea(GameSerialized gameSerialized) {
        updateBoard(gameSerialized);
        updateShelves(gameSerialized);
        updatePoints(gameSerialized);
        // Aggiungere altri elementi da aggiornare
    }

    /**
     * Updates element on the board
     */
    private void updateBoard(GameSerialized gameSerialized) {
        ObservableList<Node> children = boardGridPane.getChildren();
        children.clear();
        setBoard(gameSerialized);
    }

    /**
     * Updates element on the shelves
     */
    private void updateShelves(GameSerialized gameSerialized) {
        for (GridPane shelfGrid : shelvesGridPane) {
            ObservableList<Node> children = shelfGrid.getChildren();
            children.clear();
            String shelfOwner = (String) shelfGrid.getProperties().get(USERNAME_PROPERTY);
            updateShelfGrid(gameSerialized, shelfGrid, shelfOwner);
        }
    }

    private void updatePoints(GameSerialized gameSerialized) {
        setPlayerInfo(gameSerialized);
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
        actionListStackPane.getChildren().clear();
        arrowShelf0.setMouseTransparent(true);
        arrowShelf1.setMouseTransparent(true);
        arrowShelf2.setMouseTransparent(true);
        arrowShelf3.setMouseTransparent(true);
        arrowShelf4.setMouseTransparent(true);
        boardGridPane.setMouseTransparent(true);
        //impostare il limbo con la scritta LIMBO
        myStackPane.setMouseTransparent(true);
        shelfStackPane2.setMouseTransparent(true);
        shelfStackPane3.setMouseTransparent(true);
        shelfStackPane4.setMouseTransparent(true);
        limboHBoxArea.setMouseTransparent(true);
    }

    /**
     * Displays action buttons
     *
     * @param possibleActions possible actions
     */
    void displayAction(List<PossibleAction> possibleActions) {
        boolean isBoardPickCardActionPresent = false;
        boolean isLoadShelfActionPresent = false;
        boolean isReorderLimboActionPresent = false;

        for (PossibleAction possibleAction : possibleActions) {
            String actionID = getActionIDFromPossibleAction(possibleAction);

            switch (actionID) {
                case "boardPickCard":
                    setObjectsCardAvailability(false);
                    isBoardPickCardActionPresent = true;
                    break;
                case "loadShelf":
                    setShelfArrowsAvailability(false);
                    isLoadShelfActionPresent = true;
                    break;
                case "reorderLimbo":
                    setLimboAvailability(false);
                    isReorderLimboActionPresent = true;
                    break;
                default:
                    break;
            }
        }

        if (!isBoardPickCardActionPresent) {
            setObjectsCardAvailability(true);
        }

        if (!isLoadShelfActionPresent) {
            setShelfArrowsAvailability(true);
        }

        if (!isReorderLimboActionPresent) {
            setLimboAvailability(true);
        }

        actionListStackPane.getChildren().clear();
        for (PossibleAction possibleAction : possibleActions) {
            if(possibleAction.equals(PossibleAction.DELETE_LIMBO)) {
                ImageView imageView = new ImageView();
                imageView.setId(getActionIDFromPossibleAction(possibleAction));
                imageView.setFitHeight(ACTION_BUTTON_HEIGHT);
                imageView.setFitWidth(ACTION_BUTTON_WIDTH);
                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> guiManager.doAction(possibleAction));
                imageView.getStyleClass().add(CSS_BUTTON);

                actionListStackPane.getChildren().add(imageView);
            }
        }
    }

    /**
     * Returns the CSS ID of the action based on the PossibleAction
     *
     * @param possibleAction possible action passed
     * @return the CSS ID
     */
    private String getActionIDFromPossibleAction(PossibleAction possibleAction) {
        switch (possibleAction) {
            case JOIN_GAME:
            case CREATE_GAME:
            case BOARD_PICK_CARD:
                return "boardPickCard";
            case LOAD_SHELF:
                return "loadShelf";
            case REORDER_LIMBO:
                return "reorderLimbo";
            case DELETE_LIMBO:
                return "deleteLimbo";
            default:
                return null;
        }
    }

    private void onObjectCardClick(int row, int col) {
        Coordinate coordinate = new Coordinate(row, col);

        if (!guiManager.sendRequest(MessageBuilder.buildPickObjectCardRequest(guiManager.getPlayer(), guiManager.getClientToken(), coordinate))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);
        }
    }

    private void onReorderLimboRequest(ArrayList limboOrder) {
        if (!guiManager.sendRequest(MessageBuilder.buildReorderLimboRequest(guiManager.getUsername(), guiManager.getClientToken(), limboOrder))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);
        }
    }

    void deleteLimbo() {
        if (!guiManager.sendRequest(MessageBuilder.buildDeleteLimboRequest(guiManager.getUsername(), guiManager.getClientToken()))) {
            GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE,
                    GuiManager.SEND_ERROR);
        }
    }

    private void setObjectsCardAvailability(boolean isAvailable) {
        boardGridPane.setMouseTransparent(isAvailable);
    }

    private void setShelfArrowsAvailability(boolean isAvailable) {
        arrowShelf0.setMouseTransparent(isAvailable);
        arrowShelf1.setMouseTransparent(isAvailable);
        arrowShelf2.setMouseTransparent(isAvailable);
        arrowShelf3.setMouseTransparent(isAvailable);
        arrowShelf4.setMouseTransparent(isAvailable);
    }

    private void setLimboAvailability(boolean isAvailable) {
        limboHBoxArea.setMouseTransparent(isAvailable);
    }

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