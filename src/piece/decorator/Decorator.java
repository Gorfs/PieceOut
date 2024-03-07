package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

/**
 * Abstract class for the decorators. It extends 'Pieces' because it uses the method of 'decorator design pattern'.
 */
public abstract class Decorator extends Pieces {

    /**
     * piece on which we add the decorator.
     */
    protected Pieces piece;

    public IntCoordinates pivotPoint;


    /**
     * Constructor for the decorators.
     * @param p Piece on which we add the decorator.
     */
    public Decorator(Pieces p) {
        this.piece = p;
        this.decorated = true;
    }

    @Override
    public void action(IntCoordinates pos) {
        // If it is the right action we pressed on, then do the action.
        if (this.getPos().equals(pos)) personalAction();
        // If not, then we check for the next decorator.
        else this.piece.action(pos);
    }


    /**
     * Abstract class that used to do each action for each decorator pattern.
     */
    public abstract void personalAction();

    /**
     * Method that returns the piece it decorates.
     * @return Returns the piece it decorates.
     */
    public Pieces getPiece() {
        return piece;
    }


}
