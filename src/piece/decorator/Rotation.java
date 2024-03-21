package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.utils.*;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class Rotation extends Decorator {

    // rotation direction is -1 for clockwise and 1 for anti-clockwise
    private boolean rotationDirectionClockwise = true;

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
        this.actionPoint = pp;
    }

    @Override
    public void applyFlip() {
        // change the direction of the rotation
        this.flipDirection();
    }

    @Override
    public void applyRotation(boolean direction) {
        boolean[][] shape = this.getLast().getShape();
        boolean alreadyRotated = false;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape.length; j++) {
                if (i == this.actionPoint.y() && j == this.actionPoint.x() && !alreadyRotated) {
                    alreadyRotated = true;
                    if (this.rotationDirectionClockwise) {
                        this.setActionPoint(new IntCoordinates(shape.length - i - 1, j));

                    } else {
                        this.setActionPoint(new IntCoordinates(i, shape.length - j - 1));
                    }

                }
            }

        }

        if (this.getPrevPiece().isDecorated()) {
            ((Decorator) this.getPrevPiece()).applyRotation(direction);
        }
    }

    public Rotation(Piece p, IntCoordinates pp, boolean directionIsClockwise) {
        super(p, pp);
        this.rotationDirectionClockwise = directionIsClockwise;
    }

    public void setPivotPoint(IntCoordinates pp) {
        this.actionPoint = pp;
    }

    public IntCoordinates getPivotPoint() {
        return this.actionPoint;
    }

    /**
     * Action of rotating clockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        // action is to rotate the shape of the piece.getLast().
        boolean[][] initShape = this.getLast().getShape();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape.length]; // 4x4 matrix for the rotated shape

        // find the current offset between old fixuture point and new fixture point.
        int xOffset = this.getLast().getFixturePoint().x() - this.actionPoint.x();
        int yOffset = this.getLast().getFixturePoint().y() - this.actionPoint.y();

        // updating current position to allow the rotation of multiple pivot points
        IntCoordinates currentCoords = this.getLast().getPos();
        this.getLast().setPos(new IntCoordinates(currentCoords.x() - xOffset, currentCoords.y() - yOffset));

        // updaing the action points and actions for all other decortors part of this
        // piece
        Decorator piece = (Decorator) this.getFirst();
        piece.applyRotation(this.rotationDirectionClockwise);

        // rotating matrix 90 degrees clockwise
        for (int i = 0; i < initShape.length; i++) {
            for (int j = 0; j < initShape.length; j++) {
                if (!this.rotationDirectionClockwise) {
                    // rotating the shape anti-clockwise

                    rotatedShape[initShape.length - j - 1][i] = initShape[i][j]; // rotating the shape

                } else {
                    // the rotation is clockwise
                    rotatedShape[j][initShape.length - i - 1] = initShape[i][j]; // rotating the shape

                }
            }
        }
        // editing pivot point after rotation
        // DeComment following lines for debugging informations on the matrix rotation
        // and fixture point location.

        // Debug.printShapePieces(this.getLast());

        this.getLast().setShape(rotatedShape);
        this.getLast().setFixturePoint(this.actionPoint);

        // Debug.printShapePieces(this.getLast());

    }

    @Override
    public void personalReverseAction() {
        boolean[][] initShape = this.getLast().getShape();
        IntCoordinates initPPoint = this.getLast().getFixturePoint();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape.length]; // 4x4 matrix for the rotated shape
        // rotating matrix 90 degrees clockwise
        for (int i = 0; i < initShape.length; i++) {
            for (int j = 0; j < initShape.length; j++) {

                // checking if the rotation direction is anti-clockwise
                if (this.rotationDirectionClockwise) {

                    rotatedShape[initShape.length - j - 1][i] = initShape[i][j]; // rotating the shape

                    // updating the fixture point to new point on array
                    if (i == initPPoint.y() && j == initPPoint.x()) {
                        this.getLast().setFixturePoint(new src.geometry.IntCoordinates(i, initShape.length - j - 1));
                    }

                } else {
                    // the rotation is clockwise
                    rotatedShape[j][initShape.length - i - 1] = initShape[i][j]; // rotating the shape

                    // updating the fixture point to new point on array
                    if (i == initPPoint.y() && j == initPPoint.x()) {
                        this.getLast().setFixturePoint(new src.geometry.IntCoordinates(initShape.length - i - 1, j));
                    }

                }
            }
        }
        // DeComment following lines for debugging informations on the matrix rotation
        // and fixture point location.
        // Debug.printShapePieces(this.getLast());
        this.getLast().setShape(rotatedShape);
        // Debug.printShapePieces(this.getLast());

        // changing previous and succeding decorator to change direction according to
        // new rotation.

        Decorator piece = (Decorator) this.getFirst();

        // while the next current decorator is not the last
        while (piece.getPrevPiece().getClass() == Decorator.class) {
            piece.applyRotation(this.rotationDirectionClockwise);
            piece = (Decorator) piece.getPrevPiece();
        }
        // to apply the rotation to the last piece in the linked list
        piece.applyRotation(this.rotationDirectionClockwise);

    }

}
