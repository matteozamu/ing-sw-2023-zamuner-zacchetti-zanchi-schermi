package it.polimi.ingsw.model;

import java.util.*;

/**
 * Represents the main game logic, including object card and common goal management.
 */
public class Game {
    public static final int MAX_PLAYER = 4;
    private List<ObjectCard> objectCardContainer = new ArrayList<>();
    private List<CommonGoal> commonGoalContainer = new ArrayList<>();

    /**
     * Constructs a new Game and initializes the object cards.
     */
    public Game() {
        //TODO : Non conviene spostare la logica di creazione delle carte in un metodo (initializeCards()) separato dal costruttore?
        List<ObjectCardType> types = List.of(ObjectCardType.values());

        for(int i=0; i<6; i++){
            for(int j=0; j<22; j++){
                this.objectCardContainer.add(new ObjectCard(types.get(i), j));
            }
        }
    }

    /**
     * Get a random object card out of the container and remove the card from it.
     *
     * @return An ObjectCard randomly selected from the container.
     */
    public ObjectCard getRandomAvailableObjectCard() {
        Random RANDOM = new Random();
        int index = RANDOM.nextInt(this.objectCardContainer.size() - 1);
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
        int index = RANDOM.nextInt(this.commonGoalContainer.size() - 1);
        CommonGoal cg = this.commonGoalContainer.get(index);
        this.commonGoalContainer.remove(index);
        return cg;
    }

    //TODO da togliere, i personal goal sono presi dal file json
    /*
    public ArrayList<PersonalGoal> createPersonalGoals(){
        Random rand = new Random();
        ArrayList<PersonalGoal> goals = new ArrayList<>();
        ObjectCardType[] objTypes = ObjectCardType.values();

        for (int i = 0; i < 6; i++){
            PersonalGoal pg = new PersonalGoal(rand.nextInt(6), rand.nextInt(5), objTypes[i]);
            goals.add(pg);
        }
        return goals;
    }
     */

}
