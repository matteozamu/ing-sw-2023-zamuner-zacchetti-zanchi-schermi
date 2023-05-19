package it.polimi.ingsw.utility;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class JsonReader {
    int max_players;
    int min_players;

    public static void readJsonConstant(String filename) {
        try {
            // Read JSON from a file
            File file = new File(filename);
            InputStream inputStream = null;
            inputStream = new FileInputStream(file);

            // Parse the JSON data
            JSONTokener tokener = new JSONTokener(inputStream);
            JSONObject jsonObject = new JSONObject(tokener);

            // Access JSON properties
            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            JSONArray hobbies = jsonObject.getJSONArray("hobbies");

            // Print the values
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Hobbies: " + hobbies);

            // Close the input stream
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
