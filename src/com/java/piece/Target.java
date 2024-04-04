package src.com.java.piece;

import src.com.java.piece.decorator.Direction;
import src.com.java.geometry.IntCoordinates;

/**
 * Class used to create the target of the piece.
 */

/**
 * Represents a target object with destination coordinates, rotation, and flip status.
 */
public class Target {   
    /**
     * The destination coordinates of the target.
     */
    private IntCoordinates dest;
    
    /**
     * The destination rotation of the target.
     */
    private Direction destRotation;
    
    /**
     * Indicates whether the target is flipped horizontally.
     */
    private boolean isFlippedX;
    
    /**
     * Indicates whether the target is flipped vertically.
     */
    private boolean isFlippedY;
    
    /**
     * Constructs a new Target object with the specified destination, rotation, and flip status.
     * 
     * @param dest the destination coordinates of the target
     * @param destRotation the destination rotation of the target
     * @param isFlippedX indicates whether the target is flipped horizontally
     * @param isFlippedY indicates whether the target is flipped vertically
     */
    public Target(IntCoordinates dest, Direction destRotation, boolean isFlippedX, boolean isFlippedY) {
        this.dest = dest;
        this.destRotation = destRotation;
        this.isFlippedX = isFlippedX;
        this.isFlippedY = isFlippedY;
    }
    

    /**
     * Returns the destination coordinates of the target.
     * 
     * @return the destination coordinates of the target
     */
    public IntCoordinates getDest() {
        return dest;
    }

    /**
     * Returns the destination rotation of the target.
     * 
     * @return the destination rotation of the target
     */
    public Direction getDestRotation() {
        return destRotation;
    }

    /**
     * Checks whether the target is flipped horizontally.
     * 
     * @return true if the target is flipped horizontally, false otherwise
     */
    public boolean isFlippedX() {
        return isFlippedX;
    }

    /**
     * Checks whether the target is flipped vertically.
     * 
     * @return true if the target is flipped vertically, false otherwise
     */
    public boolean isFlippedY() {
        return isFlippedY;
    }

}
