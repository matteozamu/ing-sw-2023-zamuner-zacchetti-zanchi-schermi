package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.message.LobbyMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the main game model, including object card and common goal management.
 */
public class Game extends Observable implements Serializable {
    public static final int MAX_PLAYERS = 4;
    public static final String SERVER_NICKNAME = "server";
    private static Game instance;

    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;
    private List<PersonalGoalCard> personalGoalCardsContainer;
    private List<Player> players;
    private Player currentPlayer;
    private Board board;
    private List<CommonGoal> commonGoals;
    private int chosenPlayersNumber = 0;

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

        this.loadPersonalGoaldCards();
    }

    /**
     * @return the singleton instance.
     */
    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
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

    /**
     *
     * @return a list with all the players
     */
    public List<Player> getPlayers() {
        return players;
    }


    /**
     * Returns a list of player nicknames that are already in-game.
     *
     * @return a list with all nicknames in the Game
     */
    public List<String> getPlayersNicknames() {
        List<String> nicknames = new ArrayList<>();
        for (Player p : players) {
            nicknames.add(p.getName());
        }
        return nicknames;
    }

    /**
     * Add a new player to the game
     *
     * @param p is the object Player
     * @return true if successful, false otherwise
     */
    //TESTED
    public boolean addPlayer(Player p) {
        if (p == null) return false;
        if (this.players.size() < MAX_PLAYERS) {
            this.players.add(p);
            if (chosenPlayersNumber != 0) {
                notifyObserver(new LobbyMessage(getPlayersNicknames(), this.chosenPlayersNumber));
            }
            return true;
        } else return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Move to the next player
     *
     * @return the next player
     */

    //TESTED
    public Player nextPlayer() {
        if (this.players.size() == 0) return null;

        //this.limbo.clear();
        if (this.players.indexOf(this.currentPlayer) == this.players.size() - 1)
            this.currentPlayer = this.players.get(0);
        else this.currentPlayer = this.players.get(this.players.indexOf(currentPlayer) + 1);

        return this.currentPlayer;
    }

    /**
     * Sets the max number of players chosen by the first player joining the game.
     *
     * @param chosenMaxPlayers the max players number. Value can be {@code 0 < x < MAX_PLAYERS}.
     * @return {@code true} if the argument value is {@code 0 < x < MAX_PLAYERS}, {@code false} otherwise.
     */
    public boolean setChosenMaxPlayers(int chosenMaxPlayers) {
        if (chosenMaxPlayers > 0 && chosenMaxPlayers <= MAX_PLAYERS) {
            this.chosenPlayersNumber = chosenMaxPlayers;
            notifyObserver(new LobbyMessage(getPlayersNicknames(), this.chosenPlayersNumber));
            return true;
        }
        return false;
    }

    /**
     * @return the number of players chosen by the first player.
     */
    public int getChosenPlayersNumber() {
        return chosenPlayersNumber;
    }

    /**
     * Number of current players added in the game.
     *
     * @return the number of players.
     */
    public int getNumCurrentPlayers() {
        return players.size();
    }

    public Board getBoard() {
        return board;
    }

    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }


    /**
     * Method that adds a list of ObjectCards in the first available cells of the specified column.
     *
     * @param col is the column where to insert the object cards.
     * @return true if the cards are successfully added.
     * @throws IllegalStateException if there is not enough space to add the cards.
     */

    //TESTED
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
     * Get a random object card out of the container and remove the card from it.
     *
     * @return An ObjectCard randomly selected from the container.
     */

    //TESTED
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
    //TESTED
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

    //TESTED
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


}
