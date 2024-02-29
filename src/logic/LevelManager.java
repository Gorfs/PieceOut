package src.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class LevelManager {
    
    /** Load a level from the levels.json file
     * @param id the id of the level to load
     * @return the map of the level
     */
    public static String loadLevelFromJson(int id){
        try {
            // Read the .json file
            String content = new String(Files.readAllBytes(Paths.get("resources/maps/levels.json")));

            // Parse the content into a JSONObject
            JSONObject obj = new JSONObject(content);

            // Get the levels JSONArray
            JSONArray levels = obj.getJSONArray("levels");

            // Iterate over the levels
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                if (level.getInt("id") == id) {
                    // If the id matches, return the map
                    return level.getString("map");
                }
            }

            // If no matching id is found, return null
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
