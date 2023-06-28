package it.polimi.ingsw.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.PersonalGoalCard;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class JsonReader {
    private static int socketPort;
    private static int RMIPort;
    private static int maxPlayers;
    private static int minPlayers;
    private static int[][] board2Matrix;
    private static int[][] board3Matrix;
    private static int[][] board4Matrix;
    private static List<PersonalGoalCard> personalGoalCardsContainer;

    /**
     * read the program's constant from a json file
     *
     * @param filename the file to read
     */
    public static void readJsonConstant(String filename) {
        Gson gson = new Gson();
        try {
            // Read JSON from a file
            File file = new File(filename);
            InputStream inputStream = new FileInputStream(file);

            // Parse the JSON data
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject jsonObject = new JSONObject(tokener);


            JSONArray personalGoalCardsArray = jsonObject.getJSONArray("personalGoalCards");
            Type personalGoalCardListType = new TypeToken<List<PersonalGoalCard>>() {}.getType();
            personalGoalCardsContainer = gson.fromJson(personalGoalCardsArray.toString(), personalGoalCardListType);
            socketPort = jsonObject.getInt("socketPort");
            RMIPort = jsonObject.getInt("RMIPort");
            maxPlayers = jsonObject.getInt("maxPlayers");
            minPlayers = jsonObject.getInt("minPlayers");
            JSONArray board2 = jsonObject.getJSONArray("board2");
            JSONArray board3 = jsonObject.getJSONArray("board3");
            JSONArray board4 = jsonObject.getJSONArray("board4");

            board2Matrix = new int[board2.getJSONArray(0).length()][board2.length()];
            for (int i = 0; i < board2.length(); i++) {
                JSONArray rowArray = board2.getJSONArray(i);
                for (int j = 0; j < rowArray.length(); j++) {
                    board2Matrix[i][j] = rowArray.getInt(j);
                }
            }

            board3Matrix = new int[board3.getJSONArray(0).length()][board3.length()];
            for (int i = 0; i < board3.length(); i++) {
                JSONArray rowArray = board3.getJSONArray(i);
                for (int j = 0; j < rowArray.length(); j++) {
                    board3Matrix[i][j] = rowArray.getInt(j);
                }
            }

            board4Matrix = new int[board4.getJSONArray(0).length()][board4.length()];
            for (int i = 0; i < board4.length(); i++) {
                JSONArray rowArray = board4.getJSONArray(i);
                for (int j = 0; j < rowArray.length(); j++) {
                    board4Matrix[i][j] = rowArray.getInt(j);
                }
            }


            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the maximum number of player
     */
    public static int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @return the minimum number of player
     */
    public static int getMinPlayers() {
        return minPlayers;
    }

    /**
     * return the board based on the number of players in the game
     *
     * @param numberOfPlayers number of players in the game
     * @return the specific board
     */
    public static int[][] getBoard(int numberOfPlayers) {
        if (numberOfPlayers == 2) return board2Matrix;
        if (numberOfPlayers == 3) return board3Matrix;
        if (numberOfPlayers == 4) return board4Matrix;
        else return null;
    }

    public static int getSocketPort() {
        return socketPort;
    }

    public static int getRMIPort() {
        return RMIPort;
    }

    public static List<PersonalGoalCard> getPersonalGoalCardsContainer() {
        return personalGoalCardsContainer;
    }
}
