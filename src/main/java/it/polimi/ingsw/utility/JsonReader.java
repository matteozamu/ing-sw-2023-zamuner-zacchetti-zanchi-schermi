package it.polimi.ingsw.utility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class JsonReader {
    private static int maxPlayers;
    private static int minPlayers;
    private static int board2Matrix[][];

    public static void readJsonConstant(String filename) {
        try {
            // Read JSON from a file
            File file = new File(filename);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);

            // Parse the JSON data
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject jsonObject = new JSONObject(tokener);

            maxPlayers = jsonObject.getInt("maxPlayers");
            minPlayers = jsonObject.getInt("minPlayers");
            JSONArray board2 = jsonObject.getJSONArray("board2");

            board2Matrix = new int[board2.getJSONArray(0).length()][board2.length()];
            for (int i = 0; i < board2.length(); i++) {
                JSONArray rowArray = board2.getJSONArray(i);
                for (int j = 0; j < rowArray.length(); j++) {
                    board2Matrix[i][j] = rowArray.getInt(j);
                }
            }

            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getMaxPlayers() {
        return maxPlayers;
    }

    public static int getMinPlayers() {
        return minPlayers;
    }

    public static int[][] getBoard(int numberOfPlayers) {
        if (numberOfPlayers == 2) return board2Matrix;
        else return null;
    }
}
