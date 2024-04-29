package src.com.java.logic;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.piece.Piece;
import src.com.java.piece.ShapeFactory;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.*;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.utils.Debug;

public class LevelManager {

    /** Load a level from the levels.json file
     * @param id the id of the level to load
     * @return the level as a String array
     */
    public static String[] loadLevelFromJson(int id){
        try {
            // Read the .json file
            String content = new String(Files.readAllBytes(Paths.get(GuiConfig.resPath + "maps/levels.json")));

            // Parse the content into a JSONObject
            JSONObject obj = new JSONObject(content);

            // Get the levels JSONArray
            JSONArray levels = obj.getJSONArray("levels");

            // Iterate over the levels
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                if (level.getInt("id") == id) {
                    // If the id matches, return the map
                    JSONArray map = level.getJSONObject("map").getJSONArray("data");
                    String mapString = "";
                    for (int j = 0; j < map.length(); j++) {
                        mapString += map.getString(j);
                    }

                    return new String[]{mapString, String.valueOf(level.getJSONObject("map").getInt("width")), String.valueOf(level.getJSONObject("map").getInt("height"))};
                }
            }

            // If no matching id is found, return null
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadPiecesFromJson(PieceLogic pieceLogic, int id, InstanceManager guiManager) {
        try {
            // Read the .json file
            String content = new String(Files.readAllBytes(Paths.get(GuiConfig.resPath + "maps/pieces.json")));

            // Parse the content into a JSONObject
            JSONObject obj = new JSONObject(content);

            // Get the pieces JSONArray
            JSONArray levels = obj.getJSONArray("levels");

            // Initialize the piece to add
            // Iterate over the pieces
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                if (level.getInt("id") == id) {
                    JSONArray pieces = level.getJSONArray("pieces");
                    for (int j = 0; j < pieces.length(); j++) {
                        JSONObject piece = pieces.getJSONObject(j);
                        createPiece(piece, pieceLogic, guiManager);
                    }
                }
            }
            //Iterate over the targets
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                if (level.getInt("id") == id) {
                    JSONArray targets = level.getJSONArray("targets");
                    for (int j = 0; j < targets.length(); j++) {
                        JSONObject target = targets.getJSONObject(j);
                        createTarget(target, pieceLogic, guiManager);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createPiece(JSONObject piece, PieceLogic pieceLogic, InstanceManager guiManager) {

        // Get the color
        Color color = switch (piece.getString("color")) {
            case "green" -> Colours.PIECE_GREEN;
            case "red" -> Colours.PIECE_RED;
            case "purple" -> Colours.PIECE_PURPLE;
            default -> Colours.PIECE_BLUE;
        };
        // Get the position
        JSONArray positionArray = piece.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        // Create the piece
        Piece piece_to_add = new ShapeFactory(piece.getString("shape"), color, position, new IntCoordinates(0, 0), guiManager);


        // Add the decorators
        JSONArray decorators = piece.getJSONArray("decorators");
        Decorator decorator = null;
        for (int k = 0; k < decorators.length(); k++) {
            JSONObject decoratorJSON = decorators.getJSONObject(k);
            if (decorator == null) {
                decorator = switch (decoratorJSON.getString("type")) {
                    case "rotation" -> createRotation(piece_to_add, decoratorJSON);
                    case "flip" -> createFlip(piece_to_add, decoratorJSON);
                    default -> createTranslation(piece_to_add, decoratorJSON);
                };
            } else {
                decorator = switch (decoratorJSON.getString("type")) {
                    case "rotation" -> createRotation(decorator, decoratorJSON);
                    case "flip" -> createFlip(decorator, decoratorJSON);
                    default -> createTranslation(decorator, decoratorJSON);
                };
            }
        }

        // Add the piece to the arena
        pieceLogic.addPiece(decorator);
    }

    public static Translation createTranslation(Piece piece, JSONObject decoratorJSON) {
        boolean[] direction = new boolean[4];
        for (int i = 0; i < 4; i++) {
            direction[i] = decoratorJSON.getJSONArray("direction").getBoolean(i);
        }
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        return new Translation(piece, direction, position);
    }

    public static Rotation createRotation(Piece piece, JSONObject decoratorJSON) {
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        Rotation result = new Rotation(piece, position, decoratorJSON.getBoolean("clockwise"));
        // Turn the piece in the right way.
        for (int i = 0; i < decoratorJSON.getInt("repeat"); i++) {
            result.personalAction();
        }
        return result;
    }

    public static Decorator createFlip(Piece piece, JSONObject decoratorJSON) {
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        return new Flip(piece, position, decoratorJSON.getBoolean("horizontal"));
    }

    public static void createTarget(JSONObject target, PieceLogic pieceLogic, InstanceManager guiManager) {
        // Get the position
        JSONArray positionArray = target.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        // Get the rotations
        int rotations = target.getInt("rotations");
        // Get the flips
        boolean isFlippedX = target.getBoolean("flip_x");
        boolean isFlippedY = target.getBoolean("flip_y");
        // Create the target
        Target target_to_add = new Target(pieceLogic.getPieces().get(target.getInt("id")), position, rotations, isFlippedX, isFlippedY, guiManager);
        // Add the target to the pieceLogic
        pieceLogic.addTarget(target_to_add);
    }
}
