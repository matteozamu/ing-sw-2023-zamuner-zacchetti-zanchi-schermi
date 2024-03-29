package it.polimi.ingsw.model;

import it.polimi.ingsw.utility.JsonReader;

import java.io.Serializable;
import java.util.*;

/**
 * Represents the main game logic, including object card and common goal management.
 */
public class Game implements Serializable {
    private static Map<String, Game> instanceMap = new HashMap<>();
    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;
    private List<PersonalGoalCard> personalGoalCardsContainer;
    private List<Player> players;
    private Player currentPlayer;
    private Board board;
    private List<CommonGoal> commonGoals;
    private boolean started;
    private int numberOfPlayers;
    private LinkedHashMap<Coordinate, ObjectCard> limbo;
    private List<ObjectCard> limboOrder;
    private String gameName;

    /**
     * Constructor of the class
     */
    public Game() {
        this.objectCardContainer = new ArrayList<>();
        this.commonGoalContainer = new ArrayList<>();
        this.personalGoalCardsContainer = new ArrayList<>();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.commonGoals = new ArrayList<>();
        this.started = false;
        this.numberOfPlayers = -1;
        this.limbo = new LinkedHashMap<>();
        this.gameName = "GenericGame";

        loadObjectCards();
        loadPersonalGoaldCards();
        loadCommonGoalCards();
        commonGoals.add(getRandomAvailableCommonGoal());
        commonGoals.add(getRandomAvailableCommonGoal());
    }

    /**
     * Get an instance of the map
     *
     * @return an instance of the map
     */
    public static Map<String, Game> getInstanceMap() {
        return instanceMap;
    }

    /**
     * Return an instance of the game
     *
     * @param username is the username of a user
     * @return the instance of the game in which the user is playing
     */
    public static Game getInstance(String username) {
        if (!instanceMap.containsKey(username)) {
            Game instance = new Game();
            instanceMap.put(username, instance);
        }
        return instanceMap.get(username);
    }

    /**
     * Gets the game name
     *
     * @return the game name
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the name of the game
     *
     * @param gameName is the name of the game
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Return the number of players
     *
     * @return the number of player
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Set the number of players
     *
     * @param numberOfPlayers is the number of player to set
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Return the limbo of the game
     *
     * @return a map with the selected object cards with their coordinates
     */
    public LinkedHashMap<Coordinate, ObjectCard> getLimbo() {
        return limbo;
    }

    /**
     * Set the limbo
     *
     * @param limbo the limbo to set
     */
    public void setLimbo(LinkedHashMap<Coordinate, ObjectCard> limbo) {
        this.limbo = limbo;
    }

    /**
     * Returns the order of the limbo
     * @return the order of the selected object cards
     */
    public List<ObjectCard> getLimboOrder() {
        return limboOrder;
    }

    /**
     * Set the new order of the selected object cards
     *
     * @param limboOrder is the order to set
     */
    public void setLimboOrder(List<ObjectCard> limboOrder) {
        this.limboOrder = limboOrder;
    }

    /**
     * Return if the game has started or not
     *
     * @return true if the game is started, false otherwise
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Method used to set that the game is started or ended
     *
     * @param started is true if the game is started, false otherwise
     */
    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Get the list of the common goals
     *
     * @return a list of the Common Goal Cards present in the game
     */
    public List<CommonGoal> getCommonGoalContainer() {
        return commonGoalContainer;
    }

    /**
     * Return a list of the personal goal cards
     *
     * @return a list of the Personal Goal Cards present in the game
     */
    public List<PersonalGoalCard> getPersonalGoalCardsContainer() {
        return personalGoalCardsContainer;
    }

    /**
     * Set personal goal cards
     *
     * @param personalGoalCardsContainer is the list of the Personal Goal Cards to set
     */
    public void setPersonalGoalCardsContainer(List<PersonalGoalCard> personalGoalCardsContainer) {
        this.personalGoalCardsContainer = personalGoalCardsContainer;
    }

    /**
     * Return the list of the object cards
     *
     * @return a list of the Object Cards present in the game
     */
    public List<ObjectCard> getObjectCardContainer() {
        return objectCardContainer;
    }

    /**
     * Return the list of the players
     *
     * @return a list of the players present in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Method used to add a player in the game
     *
     * @param p is the player to add
     * @return true if the player has been successfully added
     */
    public boolean addPlayer(Player p) {
        if (p == null) return false;
        if (this.players.size() < JsonReader.getMaxPlayers()) {
            this.players.add(p);
            return true;
        } else return false;
    }

    /**
     * Method to return the player with the given username
     *
     * @return the current player of the game
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Set the current player of the game
     *
     * @param currentPlayer is the current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Return the board of the game
     *
     * @return the board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the list of the common goal cards
     *
     * @return the Common Goal Cards of the game
     */
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

        int currentPlayerIndex = this.players.indexOf(this.currentPlayer);
        int nextPlayerIndex = currentPlayerIndex;

        do {
            nextPlayerIndex = (nextPlayerIndex + 1) % this.players.size();
            Player nextPlayer = this.players.get(nextPlayerIndex);
            if (nextPlayer.isConnected()) {
                this.currentPlayer = nextPlayer;
                return this.currentPlayer;
            }
        } while (nextPlayerIndex != currentPlayerIndex);

        return this.currentPlayer;
    }

    /**
     * Return the list of name of the players
     *
     * @return the list of the players that are connected
     */
    public List<String> getPlayersNames() {
        List<String> names = new ArrayList<>();
        for (Player p : players) {
            names.add(p.getName());
        }
        return names;
    }

    /**
     * Return a Player given is username
     *
     * @param name the name of the player
     * @return the player with that username
     */
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
            for (int j = 0; j < 7; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), "0" + j));
            for (int j = 0; j < 7; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), "1" + j));
            for (int j = 0; j < 8; j++) this.objectCardContainer.add(new ObjectCard(types.get(i), "2" + j));
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
        PersonalGoalCard personalGoalCard = this.personalGoalCardsContainer.get(index);
        this.personalGoalCardsContainer.remove(index);
        return personalGoalCard;
    }

    /**
     * Load into the personalGoalCardsContainer all personal goal cards from a json file
     */
    public void loadPersonalGoaldCards() {
        this.personalGoalCardsContainer = JsonReader.getPersonalGoalCardsContainer();
    }

    /**
     * Override of the toString method
     *
     * @return a string representation
     */
    @Override
    public String toString() {
        return "Game{" +
                ", players=" + players +
                ", currentPlayer=" + currentPlayer +
                ", board=" + board +
                ", started=" + started +
                ", numberOfPlayers=" + numberOfPlayers +
                '}';
    }
}