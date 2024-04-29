package src.com.java.piece.decorator;

import src.com.java.utils.Debug;
import src.com.java.piece.Piece;
import src.com.java.geometry.IntCoordinates;

/**
 * Class that represent the decorator pattern for the getX-axis flip.
 */
public class Flip extends Decorator {

    // the axis on which we flip the piece
    public boolean xFlip = true;

    public boolean getFlipAxis() {
        return this.xFlip;
    }

    /**
     * Constructor for the decorator FlipX.
     * 
     * @param p Piece on which we add the decorator.
     */
    public Flip(Piece p, IntCoordinates local) {
        super(p, local);
    }

    public Flip(Piece p, IntCoordinates local, boolean xFlip) {
        super(p, local);
        this.xFlip = xFlip;
    }

    @Override
    public void applyFlip(boolean xAxis) {
        // updating the decorator's action points for a flip depending on axis of flip
        this.flipActionPoint(xAxis);
        // recursively apply flip to rest of the list
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
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
     * Action of flipping the nextPiece in the getX-axis.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getLast().getShape();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape[0].length];

        // changing the position on the map to offset change of fixturePoint
        this.offsetPosition(this.getActionPoint());

        // flipping the action point for all the decorators in the list
        Decorator first = (Decorator) this.getFirst();
        first.applyFlip(this.xFlip);

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
        this.getLast().setShape(rotatedShape);
        this.getLast().setFixturePoint(this.getActionPoint());

    }

    @Override
    public void personalReverseAction() {
        this.personalAction();

    }
}
