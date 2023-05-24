package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
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

import it.polimi.ingsw.model.GameSerialized;
import it.polimi.ingsw.model.Player.*;
import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.utility.MessageBuilder;

import java.util.*;
import java.util.stream.Collectors;

import static java.awt.Transparency.OPAQUE;

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
    StackPane boardArea;
    @FXML
    HBox boardShelf;
    @FXML
    ImageView board;
    @FXML
    ImageView personalGoalCard;
    @FXML
    ImageView winnerTile;
    @FXML
    ImageView pointsTile;
    @FXML
    ImageView chair;
    @FXML
    ImageView shelf;
    @FXML
    Label pointLabel;
    @FXML
    VBox usernameList;
    @FXML
    FlowPane zoomPanel; //forse non serve
    @FXML
    BorderPane infoPanel;
    @FXML
    BorderPane actionPanel;

    private GuiManager guiManager;

    private List<ImageView> objectCards;
    private List<ImageView> commonGoalCards;

    private String infoPanelUsername = null;

    @FXML
    private void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setGameSceneController(this);


        objectCards = new ArrayList<>();
        commonGoalCards = new ArrayList<>();
    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {
        Board board = gameSerialized.getBoard();

        pointLabel.setText("Points: " + gameSerialized.getPoints());

        setPlayerIcons(gameSerialized);

        bindCommonGoalCardZoom();
        bindPanels();

        updateBoard(gameSerialized);
    }

    /**
     * Binds click events on the panels
     */
    private void bindPanels() {
        infoPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideInfoPanel());
        zoomPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideZoomPanel());
    }

    /**
     * Binds weapon zoom on card click
     */
    private void bindCommonGoalCardZoom() {
//        for (ImageView weaponSlot : weaponSlotList) {
//            weaponSlot.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showCommonGoalCardZoom);
//        }
    }

    /**
     * Set the username of the players on the left of the board
     *
     * @param gameSerialized status of the game at the time of the join
     */
    private void setPlayerIcons(GameSerialized gameSerialized) {
        Label label;

        for (Player player : gameSerialized.getPlayers()) {
            label = new Label();
            label.setText(player.getName());

            usernameList.getChildren().add(label);
            //label.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showPlayerInfo);
        }
    }


    /**
     * Highlights the username of the player who owns the turn and sets opacity to the others
     *
     * @param turnOwner username of the player who owns the turn
     */
    void setTurnOwnerIcon(String turnOwner) {
        for (Node children : usernameList.getChildren()) {
            children.getStyleClass().clear();

            String labelOwner = ((Label) children).getText();

            if (labelOwner.equals(turnOwner)) {
                children.getStyleClass().add("turnOwner");
            } else {
                children.getStyleClass().add("notTurnOwner");
            }
        }
    }


    /**
     * Updates the elements of the board
     */
    void onStateUpdate() {
        setTurnOwnerIcon(GuiManager.getInstance().getTurnOwner());
        updateBoard(guiManager.getGameSerialized());

        pointLabel.setText("Points: " + guiManager.getGameSerialized().getPoints());

//        if (infoPanelUsername != null) {
//            showPlayerInfo(infoPanelUsername);
//        }
    }

    /**
     * Updates element on the board
     *
     * @param gameSerialized game update
     */
    private void updateBoard(GameSerialized gameSerialized) {
        setObjectCards(gameSerialized.getBoard());
    }

    /**
     * Sets Object Cards on the board
     *
     * @param board board of the game
     */
    // per distribuire le object cards sulla board
    private void setObjectCards(Board board) {
//        for (ImageView ammoTile : ammoTiles) {
//            boardArea.getChildren().remove(ammoTile);
//        }
//        ammoTiles.clear();
//
//        for (int y = 0; y < GameMap.MAX_COLUMNS; ++y) {
//            for (int x = 0; x < GameMap.MAX_ROWS; ++x) {
//                Square square = gameMap.getSquare(x, y);
//                if (square != null && square.getSquareType() == SquareType.TILE) {
//                    CardSquare cardSquare = (CardSquare) square;
//
//                    ImageView ammoTile = (cardSquare.isAmmoTilePresent() && cardSquare.getAmmoTile() != null) ?
//                            new ImageView(cardSquare.getAmmoTile().getImagePath()) : new ImageView();
//
//                    ammoTile.setFitHeight(32);
//                    ammoTile.setFitWidth(32);
//
//                    StackPane.setAlignment(ammoTile, Pos.TOP_LEFT);
//                    StackPane.setMargin(ammoTile, MapInsetsHelper.getAmmoTileInsets(gameMap.getMapID(), x, y));
//
//                    boardArea.getChildren().add(ammoTile);
//                    ammoTiles.add(ammoTile);
//                }
//            }
//        }
    }

    /**
     * Shows the zoom on a weapon in the zoom panel
     *
     * @param event of the click on a weapon
     */
    // metodo per mostrare il testo delle commongoal cards
    private void showCommonGoalCardZoom(Event event) {

    }

    /**
     * Hides the zoom panel
     */
    private void hideZoomPanel() {
        zoomPanel.getChildren().clear();
        zoomPanel.setVisible(false);

        setBoardOpaque(NOT_OPAQUE);
        setTurnOwnerIcon(guiManager.getTurnOwner());
    }

    /**
     * Hides the action panel
     */
    private void hideActionPanel() {
        actionPanel.getChildren().clear();
        actionPanel.setVisible(false);

        setBoardOpaque(NOT_OPAQUE);
        setTurnOwnerIcon(guiManager.getTurnOwner());
    }

    /**
     * Sets a opacity value for every element on the board
     *
     * @param value opacity value
     */
    // Per il prelevamento dell object cards
    private void setBoardOpaque(double value) {
//        board.opacityProperty().setValue(value);
//        powerupDeck.opacityProperty().setValue(value);
//        weaponDeck.opacityProperty().setValue(value);
//        blueWeapon0.opacityProperty().setValue(value);
//        blueWeapon1.opacityProperty().setValue(value);
//        blueWeapon2.opacityProperty().setValue(value);
//        redWeapon0.opacityProperty().setValue(value);
//        redWeapon1.opacityProperty().setValue(value);
//        redWeapon2.opacityProperty().setValue(value);
//        yellowWeapon0.opacityProperty().setValue(value);
//        yellowWeapon1.opacityProperty().setValue(value);
//        yellowWeapon2.opacityProperty().setValue(value);
//
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
     * Called when an error occurs. Displays an alert with the error message
     *
     * @param error message of the error
     */
    void onError(String error) {
        GuiManager.showDialog((Stage) mainPane.getScene().getWindow(), GuiManager.ERROR_DIALOG_TITLE, error);
    }

    // per impedire che un giocatore non di turno possa compiere azioni
    void notYourTurn(String turnOwner) {
//        actionList.getChildren().clear();
        setTurnOwnerIcon(turnOwner);
    }

    /**
     * Returns the CSS ID of the action based on the PossibleAction
     *
     * @param possibleAction possible action passed
     * @return the CSS ID
     */
    private String getActionIDFromPossibleAction(PossibleAction possibleAction) {
        switch (possibleAction) {
//            case SPAWN_BOT:
//            case RESPAWN_BOT:
//                return "spawnBotAction";
//            case CHOOSE_SPAWN:
//            case CHOOSE_RESPAWN:
//                return "playerSpawnAction";
//            case POWER_UP:
//                return "powerupAction";
//            case GRENADE_USAGE:
//                return "grenadeAction";
//            case SCOPE_USAGE:
//                return "scopeAction";
//            case MOVE:
//                return "moveAction";
//            case MOVE_AND_PICK:
//                return "movePickAction";
//            case SHOOT:
//                return "shootAction";
//            case RELOAD:
//                return "reloadAction";
//            case ADRENALINE_PICK:
//                return "adrenalinePickAction";
//            case ADRENALINE_SHOOT:
//                return "adrenalineShootAction";
//            case FRENZY_MOVE:
//                return "frenzyMoveAction";
//            case FRENZY_PICK:
//                return "frenzyPickAction";
//            case FRENZY_SHOOT:
//                return "frenzyShootAction";
//            case LIGHT_FRENZY_PICK:
//                return "lightFrenzyPickAction";
//            case LIGHT_FRENZY_SHOOT:
//                return "lightFrenzyShootAction";
//            case BOT_ACTION:
//                return "botAction";
//            case PASS_TURN:
//                return "passTurnAction";
//            default:
//                return null;
        }
        return null;
    }

//    /**
//     * Shows the info of a player in the info panel
//     *
//     * @param username username of the player who you want to displays informations
//     */
//    private void showPlayerInfo(String username) {
//        infoPanelUsername = username;
//
//        if (guiManager.getUsername().equals(username)) {
//            showMyPlayerInfo(guiManager.getPlayer());
//        } else {
//            showOthersPlayerInfo(guiManager.getPlayer());
//        }
//
//        setBoardOpaque(OPAQUE);
//        infoPanel.setVisible(true);
//        infoPanel.toFront();
//    }

//    /**
//     * Shows the player info in the info panel
//     *
//     * @param event event of the click on a label
//     */
//    private void showPlayerInfo(Event event) {
//        Label playerLabel = (Label) event.getTarget();
//        String username = playerLabel.getText();
//
//        showPlayerInfo(username);
//    }


//    /**
//     * Shows the player info of the client owner
//     *
//     * @param me UserPlayer of the client owner
//     */
//    private void showMyPlayerInfo(Player me) {
//        setUsernamePlayerInfo(me.getName());
//
//        addPlayerBoardToPlayerInfo(me);
//    }

//    /**
//     * Shows the player info of another player
//     *
//     * @param other UserPlayer who you wants to displays informations
//     */
//    private void showOthersPlayerInfo(Player other) {
//        setUsernamePlayerInfo(other.getName());
//
//        addPlayerBoardToPlayerInfo(other);
//
//    }

//    /**
//     * Sets the username in the info panel
//     *
//     * @param username username of the player
//     */
//    private void setUsernamePlayerInfo(String username) {
//        Label label = new Label(username);
//        label.getStyleClass().add("infoTitle");
//
//        VBox vBox = new VBox();
//        vBox.getStyleClass().add("topInfoPanel");
//        vBox.getChildren().add(label);
//
//        infoPanel.setTop(vBox);
//    }

//    /**
//     * Adds the playerboard to the info panel
//     *
//     * @param player player who you want to display the information
//     */
//    private void addPlayerBoardToPlayerInfo(Player player) {
//        PlayerColor playerColor = player.getColor();
//        PlayerBoard playerBoard = player.getPlayerBoard();
//
//        AnchorPane anchorPane = new AnchorPane();
//
//        ImageView playerBoardImageView = new ImageView(getPlayerBoardPath(playerColor, playerBoard));
//        playerBoardImageView.setFitWidth(PLAYER_BOARD_WIDTH);
//        playerBoardImageView.setFitHeight(PLAYER_BOARD_HEIGHT);
//
//        AnchorPane.setLeftAnchor(playerBoardImageView, MapInsetsHelper.playerBoardInsets.getLeft());
//
//        anchorPane.getChildren().add(playerBoardImageView);
//        infoPanel.setCenter(anchorPane);
//    }

    /**
     * Hides the info panel
     */
    private void hideInfoPanel() {
        infoPanel.getChildren().clear();
        infoPanel.setVisible(false);

        setBoardOpaque(NOT_OPAQUE);

        infoPanelUsername = null;

        setTurnOwnerIcon(guiManager.getTurnOwner());
    }

    /**
     * Sets the title of the action panel
     *
     * @param title title of the panel
     */
    private void setActionPanelTitle(String title) {
        Label label = new Label(title);
        label.getStyleClass().add("infoTitle");

        VBox vBox = new VBox();
        vBox.getStyleClass().add("topActionPanel");
        vBox.getChildren().add(label);

        actionPanel.setTop(vBox);
    }

    /**
     * Sets the bottom layout of the action panel
     */
    private void setActionPanelBottom() {
        HBox botHBox = new HBox();
        botHBox.setAlignment(Pos.BASELINE_CENTER);
        botHBox.setSpacing(20);

        ImageView backButton = new ImageView("/img/scenes/backbutton.png");
        backButton.getStyleClass().add(CSS_BUTTON);
        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> hideActionPanel());
        botHBox.getChildren().add(backButton);

        actionPanel.setBottom(botHBox);
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
//    /**
//     * Sends the pick request
//     *
//     * @param pickPosition     position of pick
//     * @param weaponCard       card picked
//     * @param paymentPowerups  list of powerups used for payment
//     * @param discardingWeapon weapon choosed for discard
//     */
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