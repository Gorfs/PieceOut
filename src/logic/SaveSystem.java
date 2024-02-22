package src.logic;

import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SaveSystem {
    /** Save function that takes some data and saves it in a Json file
     * @param nbMovesPerLevel a list of int that contains the number of moves per level
     * @param nbStarsPerLevel an array of int that contains the number of stars per level
     * @param nbMovesCurrent an int that contains the number of moves in the current level
     * @param currentLevel an int that contains the current level
     */

    public static void save(List<Integer> nbMovesPerLevel, List<Integer> nbStarsPerLevel, int nbMovesCurrent, int currentLevel){
        JSONObject obj = new JSONObject();
        obj.put("nbMovesPerLevel", new JSONArray(nbMovesPerLevel));
        obj.put("nbStarsPerLevel", new JSONArray(nbStarsPerLevel));
        obj.put("nbMovesCurrent", nbMovesCurrent);
        obj.put("currentLevel", currentLevel);

        try {
            Files.write(Paths.get("resources/savePieceOut.json"), obj.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Load function that reads a Json file and load the data in Player Class
     */
    public static void load(){
        try{
            String content = new String(Files.readAllBytes(Paths.get("resources/savePieceOut.json")));
            JSONObject obj = new JSONObject(content);
            JSONArray nbMovesPerLevel = obj.getJSONArray("nbMovesPerLevel");
            JSONArray nbStarsPerLevel = obj.getJSONArray("nbStarsPerLevel");
            for (int i = 0; i < nbMovesPerLevel.length(); i++){
                Player.getNbMovesPerLevel().add(nbMovesPerLevel.getInt(i));
                Player.getNbStarsPerLevel().add(nbStarsPerLevel.getInt(i));
                Player.setNbMovesCurrent(obj.getInt("nbMovesCurrent"));
                Player.setCurrentLevel(obj.getInt("currentLevel"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
