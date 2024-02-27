package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Class that represent the decorator pattern for the counterclockwise rotation.
 */
public class RotateCounter extends Decorator {
    /**
     * Constructor for the decorator RotateCounter.
     * @param p Piece on which we add the decorator.
     */
    public RotateCounter(Pieces p) {
        super(p);
    }

    /**
     * Action of rotating counterclockwise the piece.
     */
    @Override
    public void personalAction() {
        // TODO: Add the rotate action counterclockwise.
    }

    @Override
    public boolean[][] getShape() {
        return piece.getShape();
    }

    @Override
    public IntCoordinates getPivotPoint() {
        return piece.getPivotPoint();
    }
}
