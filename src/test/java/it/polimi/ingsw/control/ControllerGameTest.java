package it.polimi.ingsw.control;

import it.polimi.ingsw.enumeration.MessageStatus;
import it.polimi.ingsw.enumeration.PossibleGameState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.LoadShelfRequest;
import it.polimi.ingsw.network.message.ObjectCardRequest;
import it.polimi.ingsw.network.message.ReorderLimboRequest;
import it.polimi.ingsw.network.message.Response;
import it.polimi.ingsw.network.server.Server;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ControllerGameTest extends TestCase {
    private ControllerGame cg;
    private PersonalGoalCard pg;
    private Shelf shelf;

    private Server server;

    @BeforeEach
    public void setUp() {
        cg = new ControllerGame(server);
        cg.setGame(new Game());
        cg.getGame().setNumberOfPlayers(2);
        //cg.fillBoard();

    }

    @Test
    void testGetKeysAsArrayList() {
        Map<Coordinate, ObjectCard> map = new HashMap<>();
        map.put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        map.put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.randomObjectCardType(), "01"));
        map.put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.randomObjectCardType(), "02"));

        ArrayList<Coordinate> keys = cg.getKeysAsArrayList(map);

        assertEquals(3, keys.size());

        assertTrue(keys.contains(new Coordinate(1, 1)));
        assertTrue(keys.contains(new Coordinate(2, 2)));
        assertTrue(keys.contains(new Coordinate(3, 3)));
    }

    @Test
    public void testOnMessageGameEnded() {
        cg.setGameState(PossibleGameState.GAME_ENDED);
        Response response = (Response) cg.onMessage(
                new ObjectCardRequest("federica", null, null));
        assertEquals(MessageStatus.ERROR, response.getStatus());
    }

    @Test
    public void testOnMessage() {

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
    public void testGameSetupHandler() {
        cg.getGame().setNumberOfPlayers(4);
        assertFalse(cg.getIsLobbyFull());

        cg.gameSetupHandler();

        assertFalse(cg.getIsLobbyFull());
    }
    
    @Test
    public void testUsernameNull() {
        assertThrows(NullPointerException.class, () -> {
            cg.isUsernameAvailable(null);
        });
    }

    @Test
    void testLoadShelfHandler_ValidRequest() {
        LoadShelfRequest request = new LoadShelfRequest("Armando", null, 1);

        ArrayList<PersonalGoal> goals = new ArrayList<>();
        goals.add(new PersonalGoal(1, 1, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(2, 2, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(2, 3, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(4, 5, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(5, 2, ObjectCardType.randomObjectCardType()));
        goals.add(new PersonalGoal(3, 6, ObjectCardType.randomObjectCardType()));

        Player currentPlayer = new Player("Player 1", new Shelf(), new PersonalGoalCard(goals, "1"));
        cg.getGame().setCurrentPlayer(currentPlayer);

        LinkedHashMap<Coordinate, ObjectCard> limbo = new LinkedHashMap<>();
        limbo.put(new Coordinate(-3, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), "02"));
        limbo.put(new Coordinate(-3, 1), new ObjectCard(ObjectCardType.randomObjectCardType(), "02"));
        limbo.put(new Coordinate(-2, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), "02"));
        cg.getGame().setLimbo(limbo);

        Response response = cg.loadShelfHandler(request);

        assertEquals("Cards moved", response.getMessage());
        assertEquals(MessageStatus.OK, response.getStatus());
        assertEquals(0, cg.getGame().getLimbo().size());
    }


    @Test
    public void testRefillBoardEmptyBoard() {
        cg.getGame().setNumberOfPlayers(2);
        cg.refillBoard();
        assertEquals(29, cg.getGame().getBoard().getGrid().size());
    }

    @Test
    public void testRefillBoardFewTilesLeft() {
        cg.getGame().setNumberOfPlayers(2);
        cg.getGame().getBoard().getGrid().put(new Coordinate(3, -1), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().getGrid().put(new Coordinate(-3, 0), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
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
    public void testObjectCardAvailableNoLimboCards() {
//            ControllerGame cg = new ControllerGame(server);
//            cg.setGame(new Game());
        cg.fillBoard();
        cg.getGame().setLimbo(new LinkedHashMap<>());
        Coordinate coordinate = new Coordinate(-3, 0);
        boolean result = cg.isObjectCardAvailable(coordinate);
        assertTrue(result);
    }

    @Test
    public void testObjectCardAvailableOneLimboCardCloseToCoordinate() {
        cg.fillBoard();
        cg.getGame().setLimbo(new LinkedHashMap<>());
        Coordinate limboCardCoordinate = new Coordinate(-3, 0);
        cg.getGame().getLimbo().put(limboCardCoordinate, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().removeObjectCard(limboCardCoordinate);
        Coordinate coordinate = new Coordinate(-3, 1);
        boolean result = cg.isObjectCardAvailable(coordinate);
        assertTrue(result);
    }

    @Test
    public void testObjectCardAvailableOneLimboCardNotCloseToCoordinate() {
        cg.fillBoard();
        cg.getGame().setLimbo(new LinkedHashMap<>());
        Coordinate limboCardCoordinate = new Coordinate(2, 2);
        cg.getGame().getLimbo().put(limboCardCoordinate, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().removeObjectCard(limboCardCoordinate);
        Coordinate coordinate = new Coordinate(1, 1);
        boolean result = cg.isObjectCardAvailable(coordinate);
        assertFalse(result);
    }

    @Test
    public void testObjectCardAvailableTwoLimboCardsInLineWitchCoordinate() {
        cg.fillBoard();
        cg.getGame().setLimbo(new LinkedHashMap<>());
        cg.getGame().getBoard().removeObjectCard(new Coordinate(-3, 0));
        cg.getGame().getBoard().removeObjectCard(new Coordinate(-3, 1));

        Coordinate limboCard1 = new Coordinate(-2, -1);
        Coordinate limboCard2 = new Coordinate(-2, 0);
        cg.getGame().getLimbo().put(limboCard1, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().removeObjectCard(limboCard1);
        cg.getGame().getLimbo().put(limboCard2, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getBoard().removeObjectCard(limboCard2);
        Coordinate coordinate = new Coordinate(-2, 1);
        boolean result = cg.isObjectCardAvailable(coordinate);
        assertTrue(result);
    }

    @Test
    public void testObjectCardAvailableTwoLimboCardsNotInLineWithCoordinate() {
        cg.fillBoard();
        cg.getGame().setLimbo(new LinkedHashMap<>());
        cg.getGame().getBoard().removeObjectCard(new Coordinate(-3, 0));
        cg.getGame().getBoard().removeObjectCard(new Coordinate(-3, 1));
        Coordinate limboCard1 = new Coordinate(-2, -1);
        Coordinate limboCard2 = new Coordinate(-2, 0);
        cg.getGame().getLimbo().put(limboCard1, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        cg.getGame().getLimbo().put(limboCard2, new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));
        Coordinate coordinate = new Coordinate(-1, 1);
        boolean result = cg.isObjectCardAvailable(coordinate);
        assertFalse(result);
    }

//    @Test
//    public void testIsObjectCardAvailableAllEmptyDirections() {
//        Coordinate c = new Coordinate(0, 0);
//        assertTrue(cg.isObjectCardAvailable(c));
//    }

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


    @Test
    public void testCalculateWinner() {
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        Player player1 = new Player("Player 1", new Shelf(), new PersonalGoalCard(goals, "1"));
        Player player2 = new Player("Player 2", new Shelf(), new PersonalGoalCard(goals, "2"));
        Player player3 = new Player("Player 3", new Shelf(), new PersonalGoalCard(goals, "3"));

        cg.getGame().addPlayer(player1);
        cg.getGame().addPlayer(player2);
        cg.getGame().addPlayer(player3);

        player1.setCurrentPoints(5);
        player2.setCurrentPoints(10);
        player3.setCurrentPoints(15);

        System.out.println(player1.getCurrentPoints());
        System.out.println(player2.getCurrentPoints());
        System.out.println(player3.getCurrentPoints());

        cg.calculateWinner();

        assertTrue(player3.isWinner());
        assertFalse(player2.isWinner());
        assertFalse(player1.isWinner());
    }

    @Test
    public void testReorderList() {
        List<Coordinate> list1 = new ArrayList<>(Arrays.asList(
                new Coordinate(1, 1),
                new Coordinate(2, 2),
                new Coordinate(3, 3),
                new Coordinate(4, 4)
        ));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(2, 0, 3, 1));

        cg.reorderList(list1, list2);

        assertEquals(new Coordinate(3, 3), list1.get(0));

        assertEquals(new Coordinate(1, 1), list1.get(1));

        assertEquals(new Coordinate(4, 4), list1.get(2));

        assertEquals(new Coordinate(2, 2), list1.get(3));
    }

    @Test
    public void testReorderMap() {
        LinkedHashMap<Coordinate, ObjectCard> map = new LinkedHashMap<>();
        map.put(new Coordinate(1, 1), new ObjectCard(ObjectCardType.randomObjectCardType(), "10"));
        map.put(new Coordinate(2, 2), new ObjectCard(ObjectCardType.randomObjectCardType(), "20"));
        map.put(new Coordinate(3, 3), new ObjectCard(ObjectCardType.randomObjectCardType(), "30"));
        map.put(new Coordinate(4, 4), new ObjectCard(ObjectCardType.randomObjectCardType(), "00"));

        List<Coordinate> order = new ArrayList<>(Arrays.asList(
                new Coordinate(3, 3),
                new Coordinate(1, 1),
                new Coordinate(4, 4),
                new Coordinate(2, 2)
        ));
        LinkedHashMap<Coordinate, ObjectCard> orderedMap = cg.reorderMap(map, order);
        assertEquals("30", orderedMap.get(new Coordinate(3, 3)).getId());
        assertEquals("10", orderedMap.get(new Coordinate(1, 1)).getId());
        assertEquals("00", orderedMap.get(new Coordinate(4, 4)).getId());
        assertEquals("20", orderedMap.get(new Coordinate(2, 2)).getId());

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

        for (int i = 0; i < 5; i++) {
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