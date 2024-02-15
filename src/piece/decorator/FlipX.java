package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

public class FlipX extends Decorator {

    public FlipX(Pieces piece) {
        this.piece = piece;
    }

    @Override
    public void action() {
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
