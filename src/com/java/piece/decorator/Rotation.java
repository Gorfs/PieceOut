package com.java.piece.decorator;

import com.java.geometry.IntCoordinates;
import com.java.piece.Piece;


/**
 * Class that represent the decorator pattern for the clockwise rotation.
 * The rotation is done by rotating the shape of the piece 90 degrees clockwise if the direction (clockwise) is true,
 * or 90 degrees anti-clockwise if the direction (clockwise) is false.
 * @see Decorator
 */
public class Rotation extends Decorator {
    /**
     * Direction of the rotation, true for clockwise, false for anti-clockwise.
     */
    private boolean clockwise = true;

    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator.*
     */
    public Rotation(Piece p, IntCoordinates pp) {
        super(p, pp);
        this.setActionPoint(pp);
    }

    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator
     * @param clockwise boolean that represents the direction of the rotation
     */
    public Rotation(Piece p, IntCoordinates pp, boolean clockwise) {
        this(p, pp);
        this.clockwise = clockwise;
    }

    /**
     * Apply the rotation to the rotation decorator.
     * @param direction boolean that true when we rotate clockwise and false when we rotate anti-clockwise
     */
    @Override
    public void applyRotation(boolean direction) {
        this.rotateActionPoint(direction);
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyRotation(direction);
        }
    }

    /**
     * Apply the flip to the rotation decorator.
     * @param xAxis boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis
     */
    @Override
    public void applyFlip(boolean xAxis) {
        // updating the decorator's action points for a flip depending on the axis of flip
        this.clockwise = !this.clockwise;
        this.flipActionPoint(xAxis);
        // recursively apply flip to the rest of the list
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    /**
     * Action of rotating the piece.
     * The rotation is done by rotating the shape of the piece 90 degrees clockwise or anti-clockwise.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getPiece().getShape();
        boolean[][] rotatedShape = new boolean[initShape[0].length][initShape.length]; // new shape after rotation
        this.getPiece().addRotation(this.clockwise);
        this.offsetPosition(this.getActionPoint());

        // changing action point for all the decorators in the list
        Decorator piece = this.getLast();
        piece.applyRotation(this.clockwise);

        // rotating matrix 90 degrees clockwise
        for (int i = 0; i < initShape.length; i++) {
            for (int j = 0; j < initShape[0].length; j++) {
                if (!this.clockwise) {
                    // rotating the shape anti-clockwise
                    rotatedShape[initShape[0].length - j - 1][i] = initShape[i][j]; // rotating the shape
                } else {
                    // the rotation is clockwise
                    rotatedShape[j][initShape.length - i - 1] = initShape[i][j]; // rotating the shape
                }
            }
        }
        // applying change to piece
        this.getPiece().setShape(rotatedShape);
        this.getPiece().setFixturePoint(this.getActionPoint());
    }

    /**
     * Action of rotating the piece in the opposite direction.
     */
    @Override
    public void personalReverseAction() {
        this.clockwise = !this.clockwise;
        personalAction();
        this.clockwise = !this.clockwise;
    }

    /**
     * @return the direction of the rotation. True for clockwise, false for anti-clockwise
     */
    public boolean getRotationDirection() {
        return this.clockwise;
    }
}
