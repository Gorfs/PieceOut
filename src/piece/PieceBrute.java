package src.piece;


import src.geometry.IntCoordinates;
import java.awt.Color;


/**
 * Abstract class used to create pieces for the game.
 */
public abstract class PieceBrute extends Piece {
    /**
     * Color of the nextPiece.
     */
    protected Color color;
    /**
     * Position of the nextPiece.
     */
    private IntCoordinates pos;
    /**
     * Pivot point of the piece.
     */
    private IntCoordinates fixturePoint;// coordinates of the point where the piece is attached to pos.
    /**
     * Shape of the piece.
     */
    private boolean[][] shape;

    /**
     * boolean for finding out if the shape is movable
     */
    public boolean decorated = false;
    /**
     * Nothing to do here, we went through all the decorator pattern.
     */
    public void action(IntCoordinates pos) {}

    /**
     * @return Returns the color of the nextPiece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Returns the pivot point of the piece.
     */
    public IntCoordinates getFixturePoint(){
        return fixturePoint;
    }

    /**
     * Method that set the new pivotPoint of the piece.
     * @param pivotPoint IntCoordinates of the pivot point.
     */
    public void setFixturePoint(IntCoordinates pivotPoint) {
        this.fixturePoint = pivotPoint;
    }

    /**
     * @return Returns the current shape of the piece.
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * Method that set a new shape to the nextPiece.
     * @param shape New shape we want to give to our nextPiece.
     */
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }

    /**
     * @return Returns the position of the nextPiece.
     */
    public IntCoordinates getPos() {
        return pos;
    }

    /**
     * Manually change the position of the nextPiece.
     * @param pos New position of the nextPiece.
     */
    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }

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
