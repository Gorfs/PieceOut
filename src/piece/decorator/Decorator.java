package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

public abstract class Decorator extends Pieces {

    // piece on which we add the decorator.
    protected Pieces piece;

    // TODO: We may need to remove this later and make specific method for each decorator
    //       because we might need to return different things later.
    public abstract void action();

    /**
     * Method that returns the piece it decorates.
     * @return Returns the piece it decorates.
     */
    public Pieces getPiece() {
        return piece;
    }

    public abstract boolean[][] getShape();

    public abstract IntCoordinates getPivotPoint();
}
