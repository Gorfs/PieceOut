package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;
import src.utils.Debug;

/**
 * Class that represent the decorator pattern for the x-axis flip.
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
        boolean[][] shape = this.getLast().getShape();
        if (!xAxis) {
            this.actionPoint = new IntCoordinates(shape.length - this.actionPoint.x() - 1, this.actionPoint.y());
        } else {
            this.actionPoint = new IntCoordinates(this.actionPoint.x(), shape.length - this.actionPoint.y() - 1);
        }
        // recursively apply flip to rest of the list
        if (this.getPrevPiece().isDecorated()) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    @Override
    public void applyRotation(boolean clockwise) {
        this.xFlip = !this.xFlip;
        this.rotateActionPoint(clockwise);
        if (this.getPrevPiece().isDecorated()) {
            ((Decorator) this.getPrevPiece()).applyRotation(clockwise);
        }
    }

    /**
     * Action of flipping the nextPiece in the x-axis.
     */
    @Override
    public void personalAction() {
        // Debug.out("FLIPPING");
        boolean[][] shape = this.getLast().getShape();
        boolean[][] rotatedShape = new boolean[shape.length][shape.length];

        this.offsetPosition(actionPoint);
        Decorator first = (Decorator) this.getFirst();
        first.applyFlip(this.xFlip);

        // updating current position to allow the rotation of multiple pivot points
        IntCoordinates currentCoords = this.getLast().getPos();
        // Debug.out("Current Coords: " + currentCoords.x() + " " + currentCoords.y() +
        // " " + xOffset + " " + yOffset);

        // this.getLast().setPos(new IntCoordinates(currentCoords.x() - xOffset + 1,
        // currentCoords.y() - yOffset));
        // Debug.out("New Coords: " + this.getLast().getPos().x() + " " +
        // this.getLast().getPos().y());

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape.length; j++) {
                if (!this.xFlip) {
                    rotatedShape[i][j] = shape[i][shape.length - j - 1];
                } else {
                    rotatedShape[i][j] = shape[shape.length - i - 1][j];
                }
            }
        }
        this.getLast().setShape(rotatedShape);
        this.getLast().setFixturePoint(actionPoint);
        Debug.printDecorator(this);

    }

    @Override
    public void personalReverseAction() {
        this.personalAction();

    }
}
