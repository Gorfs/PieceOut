package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.PieceBrute;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class RotateClock extends Decorator {
    /**
     * Pivot point of the nextPiece.
     */
    private IntCoordinates pivotPoint;

    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator.
     */
    public RotateClock(PieceBrute p, IntCoordinates pp) {
        super(p);
        this.setPivotPoint(pp);
    }

    /**
     * Action of rotating clockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        // TODO: Add the rotate action clockwise.
    }

    /**
     * @return Returns the pivot point of the nextPiece.
     */
    public IntCoordinates getPivotPoint() {
        return pivotPoint;
    }

    /**
     * Method that set the new pivotPoint of the nextPiece.
     * @param pivotPoint IntCoordinates of the pivot point.
     */
    public void setPivotPoint(IntCoordinates pivotPoint) {
        this.pivotPoint = pivotPoint;
    }
}
