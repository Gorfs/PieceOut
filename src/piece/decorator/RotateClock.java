package src.piece.decorator;

import src.piece.PieceBrute;

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
        boolean[][] rotatedShape = new boolean[initShape.length][initShape.length]; // 4x4 matrix for the rotated shape
        for(int i = 0 ; i < initShape.length; i++){
            for(int j = 0 ; j < initShape.length; j++){
                rotatedShape[i][j] = initShape[initShape.length - 1 - j][i]; // rotating the shape

                // updating the fixture point to new point on array
                if (i == this.getLast().getFixturePoint().x() && j == this.getLast().getFixturePoint().y()) {
                    this.getLast().setFixturePoint(new src.geometry.IntCoordinates(initShape.length - j, i));
                }
            }
        }
        this.getLast().setShape(rotatedShape);
        // this.getLast().setFixturePoint();

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
