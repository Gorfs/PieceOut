package com.java.piece;

import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.PieceLogic;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Class factory for shapes, each shape can be imported from the files.
 * It is used to create a piece.
 * @see PieceBrute
 */
public class ShapeFactory extends PieceBrute {
    /**
     * Constructor of ShapeFactory. It creates a piece with a shape from a file.
     * @param filePath File name like "OShape".
     * @param color Color of the nextPiece.
     * @param fixturePoint point of the face.
     * @param pos Position of the piece.
     */
    public ShapeFactory(String filePath, Color color, IntCoordinates pos, IntCoordinates fixturePoint) {
        this.color = color;
        this.setPos(pos);
        this.setFixturePoint(fixturePoint);
        this.setShape(generateShape(filePath));
    }

    /**
     * used mostly for test purposes
     */
    public ShapeFactory(){
        this(null, null, new IntCoordinates(0, 0), new IntCoordinates(0, 0));
    }

    /**
     * Method that generate a nextPiece shape from a file based on the method used to import a map.
     * @param filePath File name like "OShape".
     * @return Returns the shape generated from the file.
     */
    public boolean[][] generateShape(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(GuiConfig.resPath + "shapes.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray shapeArray = jsonObject.getJSONArray("shapes");

            for (int i = 0; i < shapeArray.length(); i++) {
                JSONObject type = shapeArray.getJSONObject(i);
                // If the shape is found, get the shape
                if (type.getString("type").equals(filePath)) {

                    // adding the type of the shape to the piece itself
                    String shapeType = type.getString("type");
                    this.setType(shapeType);

                    JSONArray form = type.getJSONArray("form");
                    // Create the shape
                    boolean[][] shape = new boolean[type.getInt("height")][type.getInt("width")];
                    for (int j = 0; j < shape.length; j++) {
                        // get a row of the shape
                        JSONArray row = form.getJSONArray(j);
                        for (int k = 0; k < shape[0].length; k++) {
                            shape[j][k] = row.getInt(k) == 1;
                        }
                    }
                    return shape;
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file, unable to generate the shape.");
            return new boolean[][]{{false}};
        }
        return null;
    }

    @Override
    public boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // Does nothing, we went through all the decorator pattern
        return false;
    }
}
