package com.java.piece.decorator;

import com.java.utils.Debug;
import com.java.piece.Piece;
import com.java.geometry.IntCoordinates;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class Rotation extends Decorator {

    // rotation direction is -1 for clockwise and 1 for anti-clockwise
    private boolean rotationDirectionClockwise = true;

    public boolean getRotationDirection() {
        return this.rotationDirectionClockwise;
    }

    public void flipDirection() {
        this.rotationDirectionClockwise = !this.rotationDirectionClockwise;
    }

    /**
     * Constructor for the decorator RotateClock.
     * 
     * @param p Piece on which we add the decorator.
     */
    public Rotation(Piece p, IntCoordinates pp) {
        super(p, pp);
        this.setActionPoint(pp);
    }

    @Override
    public void applyFlip(boolean xAxis) {
        // updating the decorator's action points for a flip depending on axis of flip
        this.rotationDirectionClockwise = !this.rotationDirectionClockwise;
        this.flipActionPoint(xAxis);
        // recursively apply flip to the rest of the list
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    @Override
    public void applyRotation(boolean direction) {
        this.rotateActionPoint(direction);
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyRotation(direction);
        }
    }

    public Rotation(Piece p, IntCoordinates pp, boolean directionIsClockwise) {
        super(p, pp);
        this.rotationDirectionClockwise = directionIsClockwise;
    }

    public void setPivotPoint(IntCoordinates pp) {
        this.setActionPoint(pp);
    }

    public IntCoordinates getPivotPoint() {
        return this.getActionPoint();
    }

    /**
     * Action of rotating clockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getLast().getShape();
        boolean[][] rotatedShape = new boolean[initShape[0].length][initShape.length]; // new shape after rotation
        this.getLast().addRotation(this.rotationDirectionClockwise);
        this.offsetPosition(this.getActionPoint());

        // changing action point for all the decorators in the list
        Decorator piece = (Decorator) this.getFirst();
        piece.applyRotation(rotationDirectionClockwise);

        // rotating matrix 90 degrees clockwise
        for (int i = 0; i < initShape.length; i++) {
            for (int j = 0; j < initShape[0].length; j++) {
                if (!this.rotationDirectionClockwise) {
                    // rotating the shape anti-clockwise

                    rotatedShape[initShape[0].length - j - 1][i] = initShape[i][j]; // rotating the shape

                } else {
                    // the rotation is clockwise
                    rotatedShape[j][initShape.length - i - 1] = initShape[i][j]; // rotating the shape

                }
            }
        }

        // applying change to piece
        this.getLast().setShape(rotatedShape);
        this.getLast().setFixturePoint(this.getActionPoint());
    }

    @Override
    public void personalReverseAction() {
        this.rotationDirectionClockwise = !this.rotationDirectionClockwise;
        personalAction();
        this.rotationDirectionClockwise = !this.rotationDirectionClockwise;
    }

}
