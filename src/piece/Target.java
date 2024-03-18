package src.piece;

import src.geometry.IntCoordinates;
import src.piece.decorator.Direction;

public class Target {
    private final IntCoordinates dest;
    private final Direction destRotation;

    public Target(IntCoordinates dest, Direction destRotation) {
        this.dest = dest;
        this.destRotation = destRotation;
    }

    public IntCoordinates getDest() {
        return dest;
    }

    public Direction getDestRotation() {
        return destRotation;
    }

}
