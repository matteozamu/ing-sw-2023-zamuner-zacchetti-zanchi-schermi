package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.enumeration.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utility.JsonReader;
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
    private List<ImageView> commonGoalCards;

    @FXML
    private void initialize() {
        guiManager = GuiManager.getInstance();
        guiManager.setGameSceneController(this);

        shelfLabel1.setText("Federico");
        shelfLabel2.setText("Matteo");
        shelfLabel3.setText("Federica");
        shelfLabel4.setText("Simone");

        objectCards = new HashMap<>();
        commonGoalCards = new ArrayList<>();

        //loadObjectCards();
        //loadCommonGoalCards();
    }

    /**
     * Setups the board and binds all the events
     *
     * @param gameSerialized state of the game at the time of the join
     */
    void setupGame(GameSerialized gameSerialized) {
        //addObjectCards();
    }

    private void loadObjectCards() {
        List<String> types = Arrays.stream(ObjectCardType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (int i = 0; i < ObjectCardType.SIZE; i++) {
            addObjectCardImagesToMap(types.get(i), "1", 7);
            addObjectCardImagesToMap(types.get(i), "2", 7);
            addObjectCardImagesToMap(types.get(i), "3", 8);
        }
    }

    private void addObjectCardImagesToMap(String type, String level, int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView();
            String id = type + "-" + level;
            imageView.getStyleClass().add(id);
            imageView.setId(id);
            objectCards.put(imageView.getId(), imageView);
        }
    }

    private void loadCommonGoalCards() {
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-1");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-2");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-3");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-4");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-5");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-6");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-7");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-8");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-9");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-10");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-11");
        commonGoalCards.add(imageView);

        imageView = new ImageView();
        imageView.getStyleClass().add("commonGoalCard-12");
        commonGoalCards.add(imageView);
    }

    private void addObjectCards() {
        Board board = guiManager.getGameSerialized().getBoard();
        GameSerialized gameSerialized = guiManager.getGameSerialized();

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

                            boardGridPane.add(imageView, 4 - i, j - 4);  // aggiunge l'immagine alla cella (j, i) del GridPane
                        }
                    }
                }
            }
        }
    }


    /**
     * Updates the elements of the board
     */
    void onStateUpdate() {
        //updateBoard();
    }

    /**
     * Updates element on the board
     *
     */
    private void updateBoard() {
        //addObjectCards();
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