package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Class that represent the decorator pattern for the y-axis flip.
 */
public class FlipY extends Decorator {
    /**
     * Constructor for the decorator FlipY.
     * @param p Piece on which we add the decorator.
     */
    public FlipY(Pieces p) {
        super(p);
    }

    /**
     * Action of flipping the piece in the y-axis.
     */
    @Override
    public void personalAction() {
        // TODO: Add the flip action in the y-axis.
    }

}
