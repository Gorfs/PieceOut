package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;

/**
 * Class that represent the decorator pattern for the x-axis flip.
 */
public class Flip extends Decorator {
    /**
     * Constructor for the decorator FlipX.
     * 
     * @param p Piece on which we add the decorator.
     */
    public Flip(Piece p, IntCoordinates local) {
        super(p, local);
    }

    @Override
    public void applyRotation(boolean clockwise) {
        // TODO apply rotation element when flipping action is completed
    }

    /**
     * Action of flipping the nextPiece in the x-axis.
     */
    @Override
    public void personalAction() {
        // TODO: Add the flip action in the x-axis.
    }
}
