package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.utils.Debug;

/**
 * Class that represent the decorator pattern for the clockwise rotation.
 */
public class RotateClock extends Decorator {

    /**
     * Constructor for the decorator RotateClock.
     * @param p Piece on which we add the decorator.
     */
    public RotateClock(PieceBrute p) {
        super(p,p.getFixturePoint());
    }

    /**
     * Action of rotating clockwise the nextPiece.
     */
    @Override
    public void personalAction() {
        boolean[][] initShape = this.getLast().getShape();
        IntCoordinates initPPoint = this.getLast().getFixturePoint();
        boolean[][] rotatedShape = new boolean[initShape.length][initShape.length]; // 4x4 matrix for the rotated shape
        // rotating matrix 90 degrees clockwise
        for(int i = 0 ; i < initShape.length; i++){
            for(int j = 0 ; j < initShape.length; j++){
                rotatedShape[initShape.length - j - 1][i] = initShape[i][j]; // rotating the shape

                // updating the fixture point to new point on array
                if (i == initPPoint.y()  && j == initPPoint.x() ) {
                    this.getLast().setFixturePoint(new src.geometry.IntCoordinates(i, initShape.length - j - 1));
                }
            }
        }
        // DeComment following lines for debugging informations on the matrix rotation and fixture point location.
        // Debug.printShapePieces(this.getLast());
        this.getLast().setShape(rotatedShape);
        // Debug.printShapePieces(this.getLast());

    }


    // DEPRECATED
    // USED fixture point as the pivot point from now on.
    // multiple pivot points will take too long for limited time frame.

    // /**
    //  * @return Returns the pivot point of the nextPiece.
    //  */
    // public IntCoordinates getPivotPoint() {
    //     return pivotPoint;
    // }

    // /**
    //  * Method that set the new pivotPoint of the nextPiece.
    //  * @param pivotPoint IntCoordinates of the pivot point.
    //  */
    // public void setPivotPoint(IntCoordinates pivotPoint) {
    //     this.pivotPoint = pivotPoint;
    // }
}
