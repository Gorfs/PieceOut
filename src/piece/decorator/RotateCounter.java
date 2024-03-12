package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.PieceBrute;

/**
 * Class that represent the decorator pattern for the counterclockwise rotation.
 */
public class RotateCounter extends Decorator {
    /**
     * Pivot point of the nextPiece.
     */
    private IntCoordinates pivotPoint;

    /**
     * Constructor for the decorator RotateCounter.
     * @param p Piece on which we add the decorator.
     */
    public RotateCounter(PieceBrute p, IntCoordinates pp) {
        super(p, pp);
    }

    /**
     * Action of rotating counterclockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        // TODO: Add the rotate action counterclockwise.
    }

}
