package src.piece;

import src.config.Colours;
import src.geometry.IntCoordinates;


/**
 * Abstract class used to create pieces for the game.
 */
public abstract class Pieces {
    // Color of the piece
    protected Colours color;
    private IntCoordinates pos;
    // Pivot point of the piece
    private IntCoordinates pivotPoint;
    // shape of the piece
    private boolean[][] shape;

    /**
     * @return Returns the color of the piece.
     */
    public Colours getColor() {
        return color;
    }

    /**
     * @return Returns the pivot point of the piece.
     */
    public IntCoordinates getPivotPoint() {
        return pivotPoint;
    }

    /**
     * Method that set the new pivotPoint of the piece.
     * @param pivotPoint IntCoordinates of the pivot point.
     */
    public void setPivotPoint(IntCoordinates pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    /**
     * @return Returns the current shape of the piece.
     */
    public boolean[][] getShape() {
        return shape;
    }
    /**
     * Method that set a new shape to the piece.
     * @param shape New shape we want to give to our piece.
     */
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }

    /**
     * @return Returns a String that represents the piece.
     */
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (boolean[] line: shape) {
            for (boolean tile: line) {
                if (tile) text.append("1 ");
                else text.append("0 ");
            }
            text.append("\n");
        }
        return text.toString();
    }

    public IntCoordinates getPos() {
        return pos;
    }

    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }
}
