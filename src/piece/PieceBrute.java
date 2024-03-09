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
     * Shape of the nextPiece.
     */
    private boolean[][] shape;

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
