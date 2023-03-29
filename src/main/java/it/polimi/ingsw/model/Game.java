package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Game {
    public static final int MAX_PLAYER = 4;
    private List<ObjectCard> objectCardContainer = new ArrayList<>();
    private List<CommonGoal> commonGoalContainer = new ArrayList<>();

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
     * Get a random object card out of the container and remove the card from it
     * @return ObjectCard
     */
    public ObjectCard getRandomAvailableObjectCard() {
        Random r = new Random();
        int index = r.nextInt(this.objectCardContainer.size()-1);
        ObjectCard oc = this.objectCardContainer.get(index);
        this.objectCardContainer.remove(index);
        return oc;
    }

    /**
     * Get a random common goal card out of the container and remove the card from it
     * @return CommonGoal
     */
    public CommonGoal getRandomAvailableCommonGoal() {
        Random r = new Random();
        int index = r.nextInt(this.commonGoalContainer.size()-1);
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

    public void readPersonalGoaldCards() {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader("test.json")) {
                List<PersonalGoalCard> goalsDataList = gson.fromJson(reader, new TypeToken<List<PersonalGoalCard>>(){}.getType());
                System.out.println(goalsDataList);
                for (PersonalGoalCard goalsData : goalsDataList) {
                    List<PersonalGoal> goals = goalsData.getGoals();
                    for (PersonalGoal goal : goals) {
                        System.out.println(goal);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}