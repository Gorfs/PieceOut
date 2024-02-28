package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Class that represent the decorator pattern for the x-axis flip.
 */
public class FlipX extends Decorator {
    /**
     * Constructor for the decorator FlipX.
     * @param p Piece on which we add the decorator.
     */
    public FlipX(Pieces p) {
        super(p);
    }

    /**
     * Action of flipping the piece in the x-axis.
     */
    @Override
    public void personalAction() {
        // TODO: Add the flip action in the x-axis.
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
