package src.Piece.Decorator;

import src.Geometry.IntCoordinates;
import src.Piece.Pieces;

public class XAxis extends Decorator {

    public XAxis(Pieces piece) {
        this.piece = piece;
    }

    @Override
    public void action() {
        // TODO: Add the action to move the piece in the x-axis.
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
