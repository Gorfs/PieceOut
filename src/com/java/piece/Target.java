package com.java.piece;

import com.java.gui.InstanceManager;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Direction;
import com.java.geometry.IntCoordinates;
import com.java.piece.decorator.Flip;
import com.java.piece.decorator.Rotation;

public class Target {

    private Decorator piece;
    private IntCoordinates dest;
    private  boolean[][] shape;

    public InstanceManager instanceManager;

    public Target(Decorator piece, IntCoordinates dest, int rotations, boolean isFlippedX, boolean isFlippedY, InstanceManager instanceManager) {
        this.piece = piece;
        this.dest = dest;
        this.instanceManager = instanceManager;
        this.shape = createShape(rotations, isFlippedX, isFlippedY);
    }

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

    public boolean[][] getShape() {
        return shape;
    }

    public IntCoordinates getDest() {
        return dest;
    }

    public Decorator getPiece(){
        return this.piece;
    }
}
