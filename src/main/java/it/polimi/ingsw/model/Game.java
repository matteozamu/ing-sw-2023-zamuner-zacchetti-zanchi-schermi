package it.polimi.ingsw.model;

import java.util.*;

public class Game {
    public int MAX_PLAYER = 4;
    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;

    public Game() {
        this.objectCardContainer = new ArrayList<>();
        for(int i=0; i<6; i++){
            List<ObjectCardType> types = List.of(ObjectCardType.values());
            for(int j=0; j<22; j++){
                this.objectCardContainer.add(new ObjectCard(types.get(i), j));
            }
        }
    }

    /**
     * Get a random object card from the container and take the card out from it
     * @return ObjectCard
     */
    public ObjectCard getRandomAvailableObjectCard() {
        Random r = new Random();
        return this.objectCardContainer.get(r.nextInt(this.objectCardContainer.size()-1));
    }

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

}