package src.piece;

import src.geometry.IntCoordinates;
import src.piece.decorator.Direction;

/**
 * Class used to create the target of the piece.
 */
/**
 * Represents a target piece with its destination coordinates, rotation, and flip status.
 */
public class Target {
    /**
     * Represents a target piece with its destination coordinates, rotation, and flip status.
     */
    private final IntCoordinates dest;
    private final Direction destRotation;
    private final boolean isFlippedX;
    private final boolean isFlippedY;
    
    public Target(IntCoordinates dest, Direction destRotation, boolean isFlippedX, boolean isFlippedY) {
        this.dest = dest;
        this.destRotation = destRotation;
        this.isFlippedX = isFlippedX;
        this.isFlippedY = isFlippedY;
    }

    public IntCoordinates getDest() {
        return dest;
    }

    public Direction getDestRotation() {
        return destRotation;
    }

    public boolean isFlippedX() {
        return isFlippedX;
    }

    public boolean isFlippedY() {
        return isFlippedY;
    }

}
