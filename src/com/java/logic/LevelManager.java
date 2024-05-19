package com.java.logic;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.piece.Piece;
import com.java.piece.PieceBrute;
import com.java.piece.ShapeFactory;
import com.java.piece.Target;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Flip;
import com.java.piece.decorator.Rotation;
import com.java.piece.decorator.Translation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * The LevelManager class is responsible for loading levels from the levels.json file.
 */
public class LevelManager {

    /**
     * Load a level from the levels.json file.
     * It returns the map as a String array with '0'
     * representing non-playable space and '1' representing playable space.
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
                    StringBuilder mapString = new StringBuilder();
                    for (int j = 0; j < map.length(); j++) {
                        mapString.append(map.getString(j));
                    }

                    return new String[]{mapString.toString(),
                            String.valueOf(level.getJSONObject("map").getInt("width")),
                            String.valueOf(level.getJSONObject("map").getInt("height"))};
                }
            }
            // If no matching id is found, return null
            return null;
        } catch (IOException e) {
            System.out.println("Error while loading the level from the JSON file.");
            return null;
        }
    }

    /**
     * Load the number of moves for a level from the levels.json file.
     * @param id the id of the level to load
     * @return the number of moves for the level
     */
    public static int NumMovesFromJson(int id){
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
                    return level.getInt("moves");
                }
            }
            // If no matching id is found, return null
            return -1;
        } catch (IOException e) {
            System.out.println("Error while loading the level from the JSON file.");
            System.out.println(e.getMessage());
            return -1;
        }
    }

    /**
     * Load the pieces from the pieces.json file.
     * It creates the pieces and the targets and adds them to the pieceLogic.
     * @param pieceLogic the pieceLogic to add the pieces to
     * @param id the id of the level to load
     */
    public static void loadPiecesFromJson(PieceLogic pieceLogic, int id) {
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
                        createPiece(piece, pieceLogic);
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
                        createTarget(target, pieceLogic);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading the pieces from the JSON file.");
        }
    }

    /**
     * Create a piece from a JSONObject and add it to the pieceLogic.
     * It also creates the decorators and adds them to the piece.
     * @param piece the piece to create
     * @param pieceLogic the pieceLogic to add the piece to
     */
    public static void createPiece(JSONObject piece, PieceLogic pieceLogic) {
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
        PieceBrute piece_to_add = new ShapeFactory(piece.getString("shape"), color, position,
                new IntCoordinates(0, 0));

        // Add the decorators
        JSONArray decorators = piece.getJSONArray("decorators");
        Decorator decorator = null;
        String type;
        Piece prevPiece;

        for (int k = 0; k < decorators.length(); k++) {
            JSONObject decoratorJSON = decorators.getJSONObject(k);
            prevPiece = decorator == null ? piece_to_add : decorator;
            type = decoratorJSON.getString("type");
            decorator = switch (type) {
                case "rotation" -> createRotation(prevPiece, decoratorJSON);
                case "flip" -> createFlip(prevPiece, decoratorJSON);
                default -> createTranslation(prevPiece, decoratorJSON);
            };
        }


        // Add the piece to the arena
        pieceLogic.addPiece(decorator);
    }

    /**
     * Create a translation decorator from a JSONObject.
     * @param piece the piece to add the decorator to
     * @param decoratorJSON the JSONObject representing the decorator
     * @return the created decorator
     */
    public static Translation createTranslation(Piece piece, JSONObject decoratorJSON) {
        // Get the direction
        boolean[] direction = new boolean[4];
        for (int i = 0; i < 4; i++) {
            direction[i] = decoratorJSON.getJSONArray("direction").getBoolean(i);
        }
        // Get the position
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        return new Translation(piece, direction, position);
    }

    /**
     * Create a rotation decorator from a JSONObject.
     * @param piece the piece to add the decorator to
     * @param decoratorJSON the JSONObject representing the decorator
     * @return the created decorator
     */
    public static Rotation createRotation(Piece piece, JSONObject decoratorJSON) {
        // Get the position
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        // Create the rotation
        Rotation result = new Rotation(piece, position, decoratorJSON.getBoolean("clockwise"));
        // Turn the piece in the right way.
        for (int i = 0; i < decoratorJSON.getInt("repeat"); i++) {
            result.personalAction();
        }
        return result;
    }

    /**
     * Create a flip decorator from a JSONObject.
     * @param piece the piece to add the decorator to
     * @param decoratorJSON the JSONObject representing the decorator
     * @return the created decorator
     */
    public static Decorator createFlip(Piece piece, JSONObject decoratorJSON) {
        // Get the position
        JSONArray positionArray = decoratorJSON.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        // Create the flip
        return new Flip(piece, position, decoratorJSON.getBoolean("horizontal"));
    }

    /**
     * Create a target from a JSONObject and add it to the pieceLogic.
     * @param target the target to create
     * @param pieceLogic the pieceLogic to add the target to
     */
    public static void createTarget(JSONObject target, PieceLogic pieceLogic) {
        // Get the position
        JSONArray positionArray = target.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        // Get the rotations
        int rotations = target.getInt("rotations");
        // Get the flips
        boolean isFlippedX = target.getBoolean("flip_x");
        boolean isFlippedY = target.getBoolean("flip_y");
        // Create the target
        Target target_to_add = new Target(pieceLogic.getPieces().get(target.getInt("id")), position, rotations,
                isFlippedX, isFlippedY);
        // Add the target to the pieceLogic
        pieceLogic.addTarget(target_to_add);
    }

    /**
     * Check if a level is locked in the levels.json file.
     * @param id the id of the level to check
     * @return true if the level is locked, false otherwise
     */
    public static boolean isLocked(int id){
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
                    return level.getBoolean("locked");
                }
            }

            // If no matching id is found, return null
            return true;
        } catch (IOException e) {
            System.out.println("Error while opening the JSON file.");
            return true;
        }
    }

    /**
     * Unlock a level in the levels.json file.
     * @param id the id of the level to unlock
     */
    public static void unlockLevel(int id){
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
                if (level.getInt("id") == id || level.getInt("id") == id + 1){
                    // If the id matches, return the map
                    level.put("locked", false);
                }
            }

            // Write the new content
            FileWriter file = new FileWriter(GuiConfig.resPath + "maps/levels.json");
            file.write(obj.toString(4));
            file.close();
        } catch (IOException e) {
            System.out.println("Error while unlocking the level in the JSON file.");
        }
    }
}
