package src.piece.shape;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.Pieces;


/**
 * Class used to crate a piece with the O-shape:
 * 0 0 0 0
 * 0 1 1 0
 * 0 1 1 0
 * 0 0 0 0
 */
public class OShape extends Pieces {
    /**
     * Constructor of the class oShape.
     * @param color Color of the piece.
     * @param pivotPoint Position where the piece will rotate around if it can rotate.
     */
    public OShape(Colours color, IntCoordinates pivotPoint) {
        this.color = color;
        this.setPivotPoint(pivotPoint);
        this.setShape(new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}});
    }
}
