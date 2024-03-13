package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.PieceBrute;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class RotateClock extends Decorator {
    /**
     * Pivot point of the nextPiece.
     */
    private IntCoordinates pivotPoint;

    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator.
     */
    public RotateClock(PieceBrute p, IntCoordinates pp) {
        super(p, pp);
        this.setPivotPoint(pp);
    }

    /**
     * Action of rotating clockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getLast().getShape();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape[0].length]; // 4x4 matrix for the rotated shape
        for(int i = 0 ; i < initShape.length; i++){
            for(int j = 0 ; j < initShape.length; j++){
                rotatedShape[i][j] = initShape[initShape.length - j][i]; // rotating the shape
            }
        }
        this.getLast().setShape(rotatedShape);
        // this.getLast().setFixturePoint();

    }

    /**
     * @return Returns the pivot point of the nextPiece.
     */
    public IntCoordinates getPivotPoint() {
        return pivotPoint;
    }

    /**
     * Method that set the new pivotPoint of the nextPiece.
     * @param pivotPoint IntCoordinates of the pivot point.
     */
    public void setPivotPoint(IntCoordinates pivotPoint) {
        this.pivotPoint = pivotPoint;
    }
}
