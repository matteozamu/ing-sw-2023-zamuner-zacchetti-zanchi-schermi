package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the main game logic, including object card and common goal management.
 */
public class Game {
    public static final int MAX_PLAYER = 4;
    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;
    private List<PersonalGoalCard> personalGoalCardsContainer;
    private List<Player> players;
    private Board board;
    private List<CommonGoal> commonGoals;

    public Game() {
        this.objectCardContainer = new ArrayList<>();
        this.commonGoalContainer = new ArrayList<>();
        this.personalGoalCardsContainer = new ArrayList<>();
        this.players = new ArrayList<>();
        this.board = new Board();
        this.commonGoals = new ArrayList<>();
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

    public Board getBoard() {
        return board;
    }

    public List<CommonGoal> getCommonGoals() {
        return commonGoals;
    }

    /**
     * Load into the game all the object cards
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
    public ObjectCard getRandomAvailableObjectCard() {
        if (this.objectCardContainer.size() == 0) return null;

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
     * Load into the game all personal goal cards from a json file
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
