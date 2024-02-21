package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

public class South extends Decorator {

    public South(Pieces piece) {
        this.piece = piece;
    }

    @Override
    public void action() {
        IntCoordinates pos = this.piece.getPos();
        this.getPiece().setPos(new IntCoordinates(pos.x(), pos.y() + 1));
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
