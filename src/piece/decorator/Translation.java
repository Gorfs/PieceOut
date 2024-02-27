package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Class that represent the decorator pattern for the translation.
 * (North, East, South, West, according to the Direction.java enumeration class).
 */
public class Translation extends Decorator {

    /**
     * Direction of the translation
     */
    private final Direction dir;

    /**
     * Constructor for the decorator Translation.
     * @param p Piece on which we want to create the decorator
     * @param d Direction of the translation
     */
    public Translation(Pieces p, Direction d) {
        super(p);
        this.dir = d;
    }

    /**
     * Translation action of the piece.
     */
    @Override
    public void personalAction() {
        IntCoordinates pos = this.piece.getPos();
        // Switch to check for each direction
        switch (getDir()) {
            case NORTH -> this.getPiece().setPos(new IntCoordinates(pos.x(), pos.y() - 1));
            case EAST -> this.getPiece().setPos(new IntCoordinates(pos.x() + 1, pos.y()));
            case SOUTH -> this.getPiece().setPos(new IntCoordinates(pos.x(), pos.y() + 1));
            case WEST -> this.getPiece().setPos(new IntCoordinates(pos.x() - 1, pos.y()));
        }
    }

    @Override
    public boolean[][] getShape() {
        return new boolean[0][];
    }

    @Override
    public IntCoordinates getPivotPoint() {
        return null;
    }

    public Direction getDir() {
        return dir;
    }
}
