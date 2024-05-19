package com.java.piece.decorator;

import com.java.geometry.IntCoordinates;
import com.java.piece.Piece;

/**
 * Class that represents the decorator pattern for the flip action.
 */
public class Flip extends Decorator {
    /**
     * The axis on which we flip the piece. True for the X-axis, false for the Y-axis.
     */
    public boolean xFlip = true;

    /**
     * Constructor for the decorator Flip.
     * @param p Piece on which we add the decorator.
     *          The piece to which we add the flip decorator.
     * @param local IntCoordinates of the decorator on the piece.
     */
    public Flip(Piece p, IntCoordinates local) {
        super(p, local);
    }

    /**
     * Constructor for the decorator Flip.
     * @param p Piece on which we add the decorator.
     *          The piece to which we add the flip decorator.
     * @param local IntCoordinates of the decorator on the piece.
     * @param xFlip boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis.
     */
    public Flip(Piece p, IntCoordinates local, boolean xFlip) {
        super(p, local);
        this.xFlip = xFlip;
    }

    /**
     * Action of flipping the piece.
     * The flip is done by flipping the shape of the piece in the X-axis if xFlip is true else in the Y-axis.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getPiece().getShape();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape[0].length];
        this.getPiece().addFlip(this.xFlip);
       // changing the position on the map to offset change of fixturePoint
        this.offsetPosition(this.getActionPoint());

        // flipping the action point for all the decorators in the list
        Decorator first = this.getLast();
        first.applyFlip(this.xFlip);

        if (this.xFlip){
            this.xFlipped = !this.xFlipped;
        }else{
            this.yFlipped = !this.yFlipped;
        }

        // setting rotated shape to the rotation of the initial shape
        for (int i = 0; i < initShape.length; i++) {
            for (int j = 0; j < initShape[0].length; j++) {
                if (!this.xFlip) {
                    rotatedShape[i][j] = initShape[i][initShape[0].length - j - 1];
                } else {
                    rotatedShape[i][j] = initShape[initShape.length - i - 1][j];
                }
            }
        }
        // applying change of shape to the original shape
        this.getPiece().setShape(rotatedShape);
        this.getPiece().setFixturePoint(this.getActionPoint());

    }

    /**
     * Reverse the flip action of the piece.
     */
    @Override
    public void personalReverseAction() {
        this.personalAction();
    }

    @Override
    public void applyRotation(boolean clockwise) {
        this.xFlip = !this.xFlip;
        this.rotateActionPoint(clockwise);
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyRotation(clockwise);
        }
    }

    /**
     * @return the axis on which we flip the piece.
     */
    public boolean getFlipAxis() {
        return this.xFlip;
    }
}
