package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Serializable;
import java.util.*;

/**
 * Represents the main game logic, including object card and common goal management.
 */
public class Game implements Serializable {
    public static final int MAX_PLAYERS = 4;
    public static final int MIN_PLAYERS = 2;
    private static Game instance;
    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;
    private List<PersonalGoalCard> personalGoalCardsContainer;
    private List<Player> players;
    private Player currentPlayer;
    private Board board;
    private List<CommonGoal> commonGoals;
    private boolean hasStarted;
    private int numberOfPlayers;
    private Map<Coordinate, ObjectCard> limbo;

    /**
     * constructor of the class
     */
    public Game() {
        this.objectCardContainer = new ArrayList<>();
        this.commonGoalContainer = new ArrayList<>();
        this.personalGoalCardsContainer = new ArrayList<>();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.commonGoals = new ArrayList<>();
        this.hasStarted = false;
        this.numberOfPlayers = -1;
        this.limbo = new HashMap<>();

        loadObjectCards();
        loadPersonalGoaldCards();
        loadCommonGoalCards();
        commonGoals.add(getRandomAvailableCommonGoal());
        commonGoals.add(getRandomAvailableCommonGoal());
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Map<Coordinate, ObjectCard> getLimbo() {
        return limbo;
    }

    public void setLimbo(Map<Coordinate, ObjectCard> limbo) {
        this.limbo = limbo;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public List<CommonGoal> getCommonGoalContainer() {
        return commonGoalContainer;
    }

    public List<PersonalGoalCard> getPersonalGoalCardsContainer() {
        return personalGoalCardsContainer;
    }

    public void setPersonalGoalCardsContainer(List<PersonalGoalCard> personalGoalCardsContainer) {
        this.personalGoalCardsContainer = personalGoalCardsContainer;
    }

    public List<ObjectCard> getObjectCardContainer() {
        return objectCardContainer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean doesPlayerExists(String username) {
        for (Player p : players) {
            if (p.getName().equals(username)) return true;
        }
        return false;
    }

    public boolean addPlayer(Player p) {
        if (p == null) return false;
        if (this.players.size() < MAX_PLAYERS) {
            this.players.add(p);
            return true;
        } else return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }


    /**
     * Move to the next player
     *
     * @return the next player
     */
    public Player nextPlayer() {
        if (this.players.size() == 0) return null;

        if (this.players.indexOf(this.currentPlayer) == this.players.size() - 1)
            this.currentPlayer = this.players.get(0);
        else this.currentPlayer = this.players.get(this.players.indexOf(currentPlayer) + 1);

        return this.currentPlayer;
    }

    public List<String> getPlayersNames() {
        List<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getName());
        }
        return names;
    }

    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) return p;
        }
        return null;
    }

    /**
     * Method that adds a list of ObjectCards in the first available cells of the specified column.
     *
     * @param col is the column where to insert the object cards.
     * @return true if the cards are successfully added.
     * @throws IllegalStateException if there is not enough space to add the cards.
     */
    //TODO: gestire caso limbo vuoto (ora torna true)
    public boolean addObjectCardsToShelf(List<ObjectCard> limbo, int col) {
        Shelf s = this.currentPlayer.getShelf();
        int availableRows = s.getFreeCellsPerColumn(col);
        if (availableRows < limbo.size()) return false;

        for (ObjectCard card : limbo) {
            s.getGrid().put(new Coordinate(6 - availableRows, col), card);
            availableRows--;
        }
        if (s.getGrid().size() == s.ROWS * s.COLUMNS) s.setFull(true);

        return true;
    }

    /**
     * Load into the objectCardContainer all the object cards
     */
    public void loadObjectCards() {
        List<ObjectCardType> types = List.of(ObjectCardType.values());

        for (int i = 0; i < ObjectCardType.SIZE; i++) {
            for (int j = 0; j < 7; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), 0));
            for (int j = 0; j < 7; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), 1));
            for (int j = 0; j < 8; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), 2));
        }
    }

    /**
     * Load into the game all the common goal cards
     */
    public void loadCommonGoalCards() {
        commonGoalContainer.add(new CommonGoalType1());
        commonGoalContainer.add(new CommonGoalType2());
        commonGoalContainer.add(new CommonGoalType3());
        commonGoalContainer.add(new CommonGoalType4());
        commonGoalContainer.add(new CommonGoalType5());
        commonGoalContainer.add(new CommonGoalType6());
        commonGoalContainer.add(new CommonGoalType7());
        commonGoalContainer.add(new CommonGoalType8());
        commonGoalContainer.add(new CommonGoalType9());
        commonGoalContainer.add(new CommonGoalType10());
        commonGoalContainer.add(new CommonGoalType11());
        commonGoalContainer.add(new CommonGoalType12());
    }

    /**
     * Get a random object card out of the container and remove the card from it.
     *
     * @return An ObjectCard randomly selected from the container.
     */
    public ObjectCard getRandomAvailableObjectCard() {
        if (this.objectCardContainer == null || this.objectCardContainer.size() == 0) return null;

        Random RANDOM = new Random();
        int index = RANDOM.nextInt(this.objectCardContainer.size());
        ObjectCard oc = this.objectCardContainer.get(index);
        this.objectCardContainer.remove(index);
        return oc;
    }

    /**
     * Get a random common goal card out of the container and remove the card from it.
     *
     * @return A CommonGoal randomly selected from the container.
     */
    public CommonGoal getRandomAvailableCommonGoal() {
        if (this.commonGoalContainer == null || this.commonGoalContainer.size() == 0) return null;

        Random RANDOM = new Random();
        int index = RANDOM.nextInt(this.commonGoalContainer.size());
        CommonGoal cg = this.commonGoalContainer.get(index);
        this.commonGoalContainer.remove(index);
        return cg;
    }

    /**
     * Get a random personal goal card out of the container and remove the card from it.
     *
     * @return A PersonalGoalCard randomly selected from the container.
     */
    public PersonalGoalCard getRandomAvailablePersonalGoalCard() {
        if (this.personalGoalCardsContainer == null || this.personalGoalCardsContainer.size() == 0) return null;

        Random RANDOM = new Random();
        int index = RANDOM.nextInt(this.personalGoalCardsContainer.size());
        PersonalGoalCard pg = this.personalGoalCardsContainer.get(index);
        this.personalGoalCardsContainer.remove(index);
        return pg;
    }

    /**
     * Load into the personalGoalCardsContainer all personal goal cards from a json file
     */
    public void loadPersonalGoaldCards() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("personalGoalCards.json")) {
            this.personalGoalCardsContainer = gson.fromJson(reader, new TypeToken<List<PersonalGoalCard>>() {
            }.getType());

//            for (PersonalGoalCard personalGoal : this.personalGoalCardsContainer) {
//                List<PersonalGoal> goals = personalGoal.getGoals();
//                System.out.println("--------------------");
//                for (PersonalGoal goal : goals) System.out.println(goal);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "objectCardContainer=" + objectCardContainer +
                ", commonGoalContainer=" + commonGoalContainer +
                ", personalGoalCardsContainer=" + personalGoalCardsContainer +
                ", players=" + players +
                ", currentPlayer=" + currentPlayer +
                ", board=" + board +
                ", commonGoals=" + commonGoals +
                ", hasStarted=" + hasStarted +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}
