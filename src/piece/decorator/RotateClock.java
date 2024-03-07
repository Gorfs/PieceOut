package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class RotateClock extends Decorator {
    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator.
     */
    public RotateClock(Pieces p) {
        super(p);
    }

    /**
     * Action of rotating clockwise the piece.
     */
    @Override
    public void personalAction() {
        // TODO: Add the rotate action clockwise.
    }

}
