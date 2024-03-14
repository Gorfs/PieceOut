package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;

/**
 * Class that represent the decorator pattern for the x-axis flip.
 */
public class FlipX extends Decorator {
    /**
     * Constructor for the decorator FlipX.
     * @param p Piece on which we add the decorator.
     */
    public FlipX(Piece p, IntCoordinates local) {
        super(p, local);
    }

    /**
     * Action of flipping the nextPiece in the x-axis.
     */
    @Override
    public void personalAction() {
        // TODO: Add the flip action in the x-axis.
    }
}
