package it.polimi.ingsw.model;

import java.util.*;

public class Game {
    public int MAX_PLAYER = 4;
    private List<ObjectCard> objectCardContainer;
    private List<CommonGoal> commonGoalContainer;

    /**
     *
     */
    public Game() {
        this.objectCardContainer = new ArrayList<>();
        List<ObjectCardType> types = List.of(ObjectCardType.values());
        for(int i=0; i<6; i++){
            for(int j=0; j<22; j++){
                this.objectCardContainer.add(new ObjectCard(types.get(i), j));
            }
        }
        this.commonGoalContainer = new ArrayList<>();
    }

    /**
     * Get a random object card out of the container and remove the card from it
     * @return ObjectCard
     */
    public ObjectCard getRandomAvailableObjectCard() {
        Random r = new Random();
        int index = r.nextInt(this.objectCardContainer.size()-1);
        ObjectCard oc = objectCardContainer.get(index);
        objectCardContainer.remove(index);
        return oc;
    }

    /**
     * Get a random common goal card out of the container and remove the card from it
     * @return CommonGoal
     */
    public CommonGoal getRandomAvailableCommonGoal() {
        Random r = new Random();
        int index = r.nextInt(this.commonGoalContainer.size()-1);
        CommonGoal cg = commonGoalContainer.get(index);
        commonGoalContainer.remove(index);
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