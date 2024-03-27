package src.logic;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;
import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.Piece;
import src.piece.ShapeFactory;
import src.piece.Target;
import src.piece.decorator.*;

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

    public static void loadPiecesFromJson(PieceLogic pieceLogic, int id) {
        try {
            // Read the .json file
            String content = new String(Files.readAllBytes(Paths.get("resources/maps/pieces.json")));

            // Parse the content into a JSONObject
            JSONObject obj = new JSONObject(content);

            // Get the pieces JSONArray
            JSONArray levels = obj.getJSONArray("levels");

            // Initialize the piece to add
            // Iterate over the pieces
            for (int i = 0; i < levels.length(); i++) {
                JSONObject level = levels.getJSONObject(i);
                if (level.getInt("id") == id) {
                    JSONArray targets = level.getJSONArray("targets");
                    Target target_to_add;
                    int n = 0;
                    JSONArray pieces = level.getJSONArray("pieces");
                    for (int j = 0; j < pieces.length(); j++) {
                        if (n < targets.length()) {
                            JSONObject target = targets.getJSONObject(n);
                            if (target.getInt("id") == j) {
                                target_to_add = createTarget(target);
                                n++;
                            } else {
                                target_to_add = null;
                            }
                        } else {
                            target_to_add = null;
                        }
                        JSONObject piece = pieces.getJSONObject(j);
                        createPiece(piece, target_to_add, pieceLogic);
                    }
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createPiece(JSONObject piece, Target target, PieceLogic pieceLogic) {

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
        Piece piece_to_add = new ShapeFactory(piece.getString("shape"), color, position, new IntCoordinates(0, 0), target);

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
        Direction direction = switch (decoratorJSON.getString("direction")) {
            case "up" -> Direction.NORTH;
            case "down" -> Direction.SOUTH;
            case "left" -> Direction.WEST;
            default -> Direction.EAST;
        };
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

    public static Target createTarget(JSONObject target) {
        // Get the position
        JSONArray positionArray = target.getJSONArray("position");
        IntCoordinates position = new IntCoordinates(positionArray.getInt(0), positionArray.getInt(1));
        Direction direction = switch (target.getString("direction")) {
            case "up" -> Direction.NORTH;
            case "down" -> Direction.SOUTH;
            case "left" -> Direction.WEST;
            default -> Direction.EAST;
        };
        // Create the target
        return new Target(position, direction, target.getBoolean("flip_x"), target.getBoolean("flip_y"));
    }
}
