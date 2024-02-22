package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

public class Translation extends Decorator {
    Direction d; // enum avec N S E W ca sera plus simple au moment des rotations / symetries
    public East(Pieces piece) {
        super(piece);
    }

    @Override
    public void personnalAction() {
        IntCoordinates pos = this.piece.getPos();
        switch d :
        case E :
        this.getPiece().setPos(new IntCoordinates(pos.x() + 1, pos.y()));
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
