package src.Piece.Decorator;

import src.Geometry.IntCoordinates;
import src.Piece.Pieces;

public class RotateCounter extends Decorator {
    public RotateCounter(Pieces pieces) {
        this.piece = pieces;
    }

    @Override
    public void action() {
        // TODO: Add the rotate action counter clockwise.
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
