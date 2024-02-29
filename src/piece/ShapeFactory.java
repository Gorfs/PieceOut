package src.piece;

import src.config.Colours;
import src.geometry.IntCoordinates;

import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Color;
import java.io.IOException;


/**
 * Class factory for shapes, each shape can be imported from the files.
 */
public class ShapeFactory extends Pieces{

    /**
     * Constructor of ShapeFactory
     * @param filePath File name like "OShape".
     * @param color Color of the piece.
     * @param pivotPoint Pivot point of the face.
     */
    public ShapeFactory(String filePath, Color color, IntCoordinates pos, IntCoordinates pivotPoint) {
        this.color = color;
        this.setPos(pos);
        this.setPivotPoint(pivotPoint);
        this.setShape(generateShape(filePath));
    }

    /**
     * Method that generate a piece shape from a file based on the method used to import map.
     * @param filePath File name like "OShape".
     * @return Returns the shape generated from the file.
     */
    public boolean[][] generateShape(String filePath) {
        boolean[][] shape = new boolean[4][4];
        try {
            FileReader fileReader = new FileReader("ressources/shapes/" + filePath + ".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null) {
                for (int col = 0; col < line.length(); col += 2) { // increment by 2 to skip spaces
                    shape[row][col / 2] = Character.getNumericValue(line.charAt(col)) != 0; // divide by 2 to adjust for spaces
                }
                row++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shape;
    }
}
