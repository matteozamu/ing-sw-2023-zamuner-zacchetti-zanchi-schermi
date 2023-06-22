package it.polimi.ingsw.control;

import it.polimi.ingsw.enumeration.MessageContent;
import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.GameStateRequest;
import it.polimi.ingsw.network.message.ObjectCardRequest;
import it.polimi.ingsw.network.message.ReorderLimboRequest;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utility.JsonReader;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerGameTest extends TestCase {
    private ControllerGame cg;
    private PersonalGoalCard pg;
    private Shelf shelf;

    private Server server;

    @BeforeEach
    public void setUp(){
        cg = new ControllerGame(server);
        cg.setGame(new Game());
        cg.getGame().setNumberOfPlayers(2);
        //cg.fillBoard();

    }

    @Test
    public void testOnMessage(){

        //PICK_OBJECT_CARD Case null coordinates
        Response response = (Response) cg.onMessage(
                new ObjectCardRequest("federica", null, null));
        assertEquals(MessageStatus.ERROR, response.getStatus());

//        //PICK_OBJECT_CARD Case right coordinates
//        response = (Response) cg.onMessage(
//                new ObjectCardRequest("federica", null, new Coordinate(-3, 1)));
//        assertEquals(MessageStatus.PRINT_LIMBO, response.getStatus());

        // REORDER_LIMBO_REQUEST Case null limbo
        response = (Response) cg.onMessage(
                new ReorderLimboRequest("federica", null, null));
        assertEquals(MessageStatus.ERROR, response.getStatus());

//        // REORDER_LIMBO_REQUEST Case full limbo
//        ArrayList<ObjectCard> limbo = new ArrayList<>();
//        Map<Coordinate, ObjectCard> limbo2 = new HashMap<>();
//        limbo2.put(new Coordinate(0, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        limbo2.put(new Coordinate(1, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//
//        limbo.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        limbo.add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        cg.getGame().setLimbo(limbo2);
//
//        response = (Response) cg.onMessage(
//                new ReorderLimboRequest("federica", null, limbo));
//        assertEquals(MessageStatus.PRINT_LIMBO, response.getStatus());
    }

    @Test
    public void testGameSetupHandler(){
        cg.getGame().setNumberOfPlayers(4);
        assertFalse(cg.getIsLobbyFull());

        cg.gameSetupHandler();

        assertFalse(cg.getIsLobbyFull());
    }
    //TODO: non passa
//    @Test
//    public void testGameSetupHandler_LobbyFull() {
//        cg.getGame().setNumberOfPlayers(2);
//        assertFalse(cg.getIsLobbyFull());
//
//        this.cg.getGame().addPlayer(new Player("Matteo", new Shelf(), null));
//        this.cg.getGame().addPlayer(new Player("Simone", new Shelf(), null));
//        cg.setGame(Game.getInstance("Matteo"));
//        cg.setGame(Game.getInstance("Simone"));
//
//        cg.gameSetupHandler();
//
//        assertTrue(cg.getIsLobbyFull());
//    }

    @Test
    public void testUsernameNull() {
        assertThrows(NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    //TODO: non passa

//    @Test
//    public void testIsUsernameAvailable(){
//        cg.getGame().addPlayer(new Player("Matteo", new Shelf(), null));
//        cg.getGame().addPlayer(new Player("Simone", new Shelf(), null));
//        cg.setGame(Game.getInstance("Matteo"));
//        cg.setGame(Game.getInstance("Simone"));
//
//        assertFalse(cg.isUsernameAvailable("Matteo"));
//        assertFalse(cg.isUsernameAvailable("Simone"));
//        assertTrue(cg.isUsernameAvailable("Davide"));
//    }
public void printBoard(Board board) {
    ObjectCard objectCard;
    StringBuilder boardView = new StringBuilder();

    int playerNumber = 2;
    JsonReader.readJsonConstant("GameConstant.json");
    int[][] boardMatrix = JsonReader.getBoard(2);

    if (playerNumber == 2) {
        boardView.append(" ".repeat(15));
        boardView.append("-3       -2        -1       0        1        2        3\n");
    } else if (playerNumber == 3 || playerNumber == 4) {
        boardView.append(" ".repeat(7));
        boardView.append("-4       -3       -2       -1       0        1        2        3        4\n\n");
    }

    for (int i = 0; i < boardMatrix.length / 2; i++) {
        if (!(playerNumber == 2 && 4 - i == 4)) {
            boardView.append(String.format("%2s ", 4 - i));
        }
        for (int j = 0; j < boardMatrix[i].length; j++) {
            if (boardMatrix[i][j] == 1) {
                objectCard = board.getGrid().get(new Coordinate(4 - i, j - 4));
                if (objectCard != null) {
                    String cardText = objectCard.getType().getText();
                    int visibleCardLength = cardText.length();
                    if (visibleCardLength % 2 == 0) {
                        boardView.append("║").append(" ".repeat((8 - visibleCardLength) / 2)).append(objectCard).append(" ".repeat((8 - visibleCardLength) / 2));
                    } else {
                        boardView.append("║").append(" ".repeat((8 - visibleCardLength) / 2)).append(objectCard).append(" ".repeat((int) Math.ceil((double) (8 - visibleCardLength) / 2)));
                    }
                } else {
                    boardView.append("║").append(" ".repeat(8));
                }
                if (playerNumber == 2) {
                    if ((4 - i == 3 && j - 4 == 0) || (4 - i == 2 && j - 4 == 1) || (4 - i == 1 && j - 4 == 3)) {
                        boardView.append("║");
                    }
                }
            } else {
                boardView.append(" ".repeat(9));
            }
        }
        boardView.append("\n");
    }
    System.out.print(boardView);
}


    @Test
    public void testRefillBoardEmptyBoard(){
        cg.getGame().setNumberOfPlayers(2);
        cg.refillBoard();
        assertEquals(29, cg.getGame().getBoard().getGrid().size());
    }

    @Test
    public void testRefillBoardFewTilesLeft(){
        cg.getGame().setNumberOfPlayers(2);
        cg.getGame().getBoard().getGrid().put(new Coordinate(3,-1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().getGrid().put(new Coordinate(-3,0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.refillBoard();
        assertEquals(29, cg.getGame().getBoard().getGrid().size());
    }

//------------------------------------------------------------
//    @BeforeEach
//    public void setUp() {
//        this.cg = new ControllerGame(new Server());
//        this.cg.getGame().loadObjectCards();
//
//        ArrayList<PersonalGoal> goals = new ArrayList<>();
//        goals.add(new PersonalGoal(1, 1, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(2, 2, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(2, 3, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(4, 5, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(5, 2, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(3, 6, ObjectCardType.randomObjectCardType()));
//
//        this.pg = new PersonalGoalCard(goals);
//        this.shelf = new Shelf();
//    }
//
//    @Test
//    public void testUsernameAvailable() {
//        assertTrue(cg.isUsernameAvailable("Madeleine"));
//
//        Player p = new Player("Madeleine", this.shelf, this.pg);
//        cg.getGame().addPlayer(p);
//
//        assertFalse(cg.isUsernameAvailable("Madeleine"));
//        assertTrue(cg.isUsernameAvailable("Daphne"));
//    }

    //    @Test
//    public void testAddObjectEmptyLimbo() {
//        assertFalse(cg.addObjectCards(1));
//    }
//
//    @Test
//    public void testAddObjectLimboTooBig() {
//        Player p = new Player("Kelleigh", this.shelf, this.pg);
//        cg.getGame().addPlayer(p);
//        cg.getGame().setCurrentPlayer(p);
//
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 0));
//
//        assertFalse(cg.getGame().addObjectCardsToShelf(cg.getLimbo(), 0));
//
//    }
//
//    @Test
//    public void testAddObjectCardsNoSpaceShelfColumn() {
//        Player p = new Player("Estela", this.shelf, this.pg);
//        cg.addPlayer(p);
//        cg.setCurrentPlayer(p);
//
//        for (int i = 0; i < 6; i++) {
//            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        }
//
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
//        assertFalse(cg.addObjectCards(0));
//    }
//
//    @Test
//    public void testAddObjectCards() {
//        Player p = new Player("Laia", this.shelf, this.pg);
//        cg.addPlayer(p);
//        cg.setCurrentPlayer(p);
//
//        for (int i = 0; i < 4; i++) {
//            cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        }
//
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 2));
//
//        assertTrue(cg.addObjectCards(0));
//    }
//
//    @Test
//    public void testAddObjectCardsCardRightPositionShelf() {
//        Player p = new Player("Ines", this.shelf, this.pg);
//        cg.addPlayer(p);
//        cg.setCurrentPlayer(p);
//
//        ObjectCard oc = new ObjectCard(ObjectCardType.randomObjectCardType(), 2);
//        cg.getLimbo().add(oc);
//
//        cg.addObjectCards(0);
//        assertEquals(cg.getCurrentPlayer().getShelf().getGrid().get(new Coordinate(0, 0)), oc);
//    }
//
//    @Test
//    public void testAddObjectCardsShelfIsFull() {
//        Player p = new Player("Juanita", this.shelf, this.pg);
//        cg.addPlayer(p);
//        cg.setCurrentPlayer(p);
//
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 4; j++) {
//                cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, j), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//            }
//        }
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(0, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(1, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(2, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(3, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.getCurrentPlayer().getShelf().getGrid().put(new Coordinate(4, 5), new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//
//        cg.getLimbo().add(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//
//        cg.addObjectCards(5);
//        assertTrue(cg.getCurrentPlayer().getShelf().isFull());
//    }
//
//

    @Test
    public void testIsObjectCardAvailableAllEmptyDirections() {
        Coordinate c = new Coordinate(0, 0);
        assertTrue(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testIsObjectCardAvailableOneDirectionFull() {
        Coordinate c = new Coordinate(0, 3);
        cg.fillBoard();
        assertTrue(cg.isObjectCardAvailable(c));
    }

    @Test
    public void testIsObjectCardAvailableAllDirectiosnFull() {
        Player p = new Player("Becky", this.shelf, this.pg);
        cg.getGame().addPlayer(p);
        Coordinate c = new Coordinate(0, 0);

        cg.fillBoard();
        assertFalse(cg.isObjectCardAvailable(c));
    }

//
//    @Test
//    public void testAddObjectCardToLimbo() {
//        assertTrue(cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1)));
//    }
//
//    @Test
//    public void testAddObjectCardToLimboSizeLimit() {
//        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1));
//        assertFalse(cg.addObjectCardToLimbo(new ObjectCard(ObjectCardType.randomObjectCardType(), 1)));
//    }
//
//    @Test
//    public void testAddObjectCardToLimboNullCard() {
//        assertThrows(NullPointerException.class, () -> {
//            cg.addObjectCardToLimbo(null);
//        });
//    }
//
    @Test
    public void testPointsCalculatorNoCompletedRows() {
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        goals.add(new PersonalGoal(1, 1, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(2, 2, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(2, 3, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(4, 5, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(5, 2, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(3, 6, ObjectCardType.randomObjectCardType()));

        this.pg = new PersonalGoalCard(goals, "personalGoalCard-1");
        this.shelf = new Shelf();
        Player p = new Player("Alice", this.shelf, this.pg);
        cg.getGame().addPlayer(p);
        cg.getGame().setCurrentPlayer(p);

        assertEquals(0, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorOneCompletedRowInShelf() {
        ArrayList<PersonalGoal> goals = new ArrayList<>();
//        goals.add(new PersonalGoal(1, 1, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(2, 2, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(2, 3, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(4, 5, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(5, 2, ObjectCardType.randomObjectCardType()));
//        goals.add(new PersonalGoal(3, 6, ObjectCardType.randomObjectCardType()));
        cg.getGame().loadPersonalGoaldCards();
        PersonalGoalCard pgc = cg.getGame().getRandomAvailablePersonalGoalCard();
        System.out.println(pgc);
        this.pg = pgc;
        this.shelf = new Shelf();

        Player p = new Player("Wejdene", this.shelf, this.pg);
        cg.getGame().addPlayer(p);
        cg.getGame().setCurrentPlayer(p);

        ObjectCardType type = ObjectCardType.randomObjectCardType();
        ObjectCard oc = new ObjectCard(type, "00");

        for (int i = 0; i < 5; i++){
            cg.getGame().getCurrentPlayer().getShelf().getGrid().put(new Coordinate(i, 0), oc);
        }

        assertEquals(5, cg.pointsCalculator());
    }

    @Test
    public void testPointsCalculatorPersonalGoalCard() {
        cg.getGame().loadPersonalGoaldCards();
        PersonalGoalCard pgc = cg.getGame().getRandomAvailablePersonalGoalCard();
        this.shelf = new Shelf();
        this.pg = pgc;
        Player p = new Player("Karol", this.shelf, this.pg);
        cg.getGame().addPlayer(p);
        cg.getGame().setCurrentPlayer(p);

        cg.getGame().getCurrentPlayer().getPersonalGoalCard().setTargetsReached(4);
        assertEquals(6, cg.pointsCalculator());
    }

//    @Test
//    public void testPickObjectCardFailed() {
//        cg.fillBoard();
//
//        // pick an object card from the board
//        Coordinate coordinate = new Coordinate(0, 0);
//        ObjectCard card = cg.getGame().getBoard().getObjectCard(coordinate);
//        assertTrue(cg.isObjectCardAvailable(coordinate));
//        cg.pickObjectCard(coordinate);
//        assertFalse(cg.isObjectCardAvailable(coordinate));
//        assertTrue(cg.getLimbo().contains(card));
//
//        // pick an object card from the same cell
//        assertFalse(cg.pickObjectCard(coordinate));
//        // check that the object card was not added to the limbo
//        List<ObjectCard> limbo = cg.getLimbo();
//        assertTrue(limbo.isEmpty());
//
//    }

}