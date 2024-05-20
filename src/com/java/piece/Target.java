package com.java.piece;

import com.java.geometry.IntCoordinates;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Flip;
import com.java.piece.decorator.Rotation;


/**
 *
 */
public class Target {
    /**
     * Targeted piece.
     */
    private final Decorator piece;
    /**
     * Destination of the target (the end position of the piece).
     */
    private final IntCoordinates dest;
    /**
     * Shape of the target (also the shape of the piece).
     */
    private final boolean[][] shape;

    /**
     * Constructor for the Target class. It creates a target with a piece, a destination, and a shape.
     * @param piece Piece that is targeted
     * @param dest Destination of the target
     * @param rotations Number of rotations to apply to the target
     * @param isFlippedX If the target is flipped on the X-axis
     * @param isFlippedY If the target is flipped on the Y-axis
     */
    public Target(Decorator piece, IntCoordinates dest, int rotations, boolean isFlippedX, boolean isFlippedY) {
        this.piece = piece;
        this.dest = dest;
        this.shape = createShape(rotations, isFlippedX, isFlippedY);
    }

    /**
     * Method that creates the shape of the target.
     * @param rotations number of rotations to apply to the target
     * @param isFlippedX if the target is flipped on the X-axis
     * @param isFlippedY if the target is flipped on the Y-axis
     * @return the shape of the target
     */
    private boolean[][] createShape(int rotations, boolean isFlippedX, boolean isFlippedY){
        boolean[][] shape = new boolean[piece.getShape().length][piece.getShape()[0].length];
        for (int i = 0; i < piece.getShape().length; i++) {
            for (int j = 0; j < piece.getShape()[0].length; j++) {
                shape[i][j] = piece.getShape()[i][j];
            }
        }
        PieceBrute pieceBrute = new ShapeFactory();
        pieceBrute.setShape(shape);
        Decorator rotation = new Rotation(pieceBrute, new IntCoordinates(0,0));
        Decorator flipX = new Flip(rotation, new IntCoordinates(0,0));
        Decorator flipY = new Flip(flipX, new IntCoordinates(0,0), false);

        for (int i = 0; i < rotations; i++) rotation.personalAction();
        if (isFlippedX) flipX.personalAction();
        if (isFlippedY) flipY.personalAction();

        return flipY.getShape();
    }

    /**
     * @return the shape of the target
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * @return the destination of the target
     */
    public IntCoordinates getDest() {
        return dest;
    }

    /**
     * @return the targeted piece
     */
    public Decorator getPiece(){
        return this.piece;
    }
}
