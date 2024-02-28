package src.piece;

import src.config.Colours;
import src.geometry.IntCoordinates;

import java.awt.Color;


/**
 * Abstract class used to create pieces for the game.
 */
public abstract class Pieces {
    /**
     * Color of the piece.
     */
    protected Color color;
    /**
     * Position of the piece.
     */
    private IntCoordinates pos;
    /**
     * Pivot point of the piece.
     */
    private IntCoordinates pivotPoint;
    /**
     * Shape of the piece.
     */
    private boolean[][] shape;

    /**
     * Nothing to do here, we went through all the decorator pattern.
     */
    public void action(IntCoordinates pos) {}

    /**
     * @return Returns the color of the piece.
     */
    public Color getColor() {
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
     * @return Returns the position of the piece.
     */
    public IntCoordinates getPos() {
        return pos;
    }

    /**
     * Manually change the position of the piece.
     * @param pos New position of the piece.
     */
    public void setPos(IntCoordinates pos) {
        this.pos = pos;
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

}
