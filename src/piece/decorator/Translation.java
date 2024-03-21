package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;

/**
 * Class that represent the decorator pattern for the translation.
 * (North, East, South, West, according to the Direction.java enumeration
 * class).
 */
public class Translation extends Decorator {
    /**
     * Direction of the translation
     */
    private Direction dir;

    /**
     * Constructor for the decorator Translation.
     * 
     * @param p Piece on which we want to create the decorator
     * @param d Direction of the translation
     */
    public Translation(Piece p, Direction d, IntCoordinates local) {
        super(p, local);
        this.dir = d;
    }

    @Override
    public void applyRotation(boolean clockwise) {
        // TODO apply rotation element when flipping action is completed
        if (clockwise) {
            switch (dir) {
                case NORTH -> dir = Direction.EAST;
                case EAST -> dir = Direction.SOUTH;
                case SOUTH -> dir = Direction.WEST;
                case WEST -> dir = Direction.NORTH;
            }
        } else {
            switch (dir) {
                case NORTH -> dir = Direction.WEST;
                case EAST -> dir = Direction.NORTH;
                case SOUTH -> dir = Direction.EAST;
                case WEST -> dir = Direction.SOUTH;
            }
        }
    }

    /**
     * Translation action of the nextPiece.
     */
    @Override
    public void personalAction() {
        IntCoordinates pos = this.getLast().getPos();
        // Switch to check for each direction
        switch (getDir()) {
            case NORTH -> this.getLast().setPos(new IntCoordinates(pos.x(), pos.y() - 1));
            case EAST -> this.getLast().setPos(new IntCoordinates(pos.x() + 1, pos.y()));
            case SOUTH -> this.getLast().setPos(new IntCoordinates(pos.x(), pos.y() + 1));
            case WEST -> this.getLast().setPos(new IntCoordinates(pos.x() - 1, pos.y()));
        }
    }

    @Override
    public void personalReverseAction() {
        IntCoordinates pos = this.getLast().getPos();
        // Switch to check for each direction
        switch (getDir()) {
            case NORTH -> this.getLast().setPos(new IntCoordinates(pos.x(), pos.y() + 1));
            case EAST -> this.getLast().setPos(new IntCoordinates(pos.x() - 1, pos.y()));
            case SOUTH -> this.getLast().setPos(new IntCoordinates(pos.x(), pos.y() - 1));
            case WEST -> this.getLast().setPos(new IntCoordinates(pos.x() + 1, pos.y()));
        }
    }

    public Direction getDir() {
        return dir;
    }
}
