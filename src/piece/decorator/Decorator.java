package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Piece;
import src.piece.PieceBrute;

/**
 * Abstract class for the decorators. It extends 'Piece' because it uses the
 * method of 'decorator design pattern'.
 */
public abstract class Decorator extends Piece {

    /**
     * nextPiece on which we add the decorator.
     */
    protected Piece nextPiece;
    protected Piece prevPiece;
    protected IntCoordinates localCoord;

    public IntCoordinates pivotPoint;

    /**
     * applys the necessary actions for the movement to be adjusted to a new
     * rotation
     */
    public void applyRotation(boolean clockwise) {
        return;
    }

    /**
     * same as for the applyRotation() methode but for the flip action
     */
    public void applyFlip() {
        return;
    }

    /**
     * Constructor for the decorators.
     * 
     * @param p Piece on which we add the decorator.
     */
    public Decorator(Piece p, IntCoordinates local) {
        this.prevPiece = p;
        this.localCoord = local;
        this.nextPiece = null;
    }

    @Override
    public void action(IntCoordinates pos) {
        // If it is the right action we pressed on, then do the action.
        if (this.localCoord.equals(pos))
            personalAction();
        // If not, then we check for the next decorator.
        else
            this.prevPiece.action(pos);
    }

    // returns the piece brute at the very front of the chained list
    public PieceBrute getLast() {
        Piece temp = prevPiece;
        while (!(temp instanceof PieceBrute))
            temp = ((Decorator) temp).getPrevPiece();
        return ((PieceBrute) temp);
    }

    // returns the last decorator in the chain
    public Piece getFirst() {
        Piece temp = this;
        while (!(((Decorator) temp).getNextPiece() == null))
            temp = ((Decorator) temp).getNextPiece();
        return temp;
    }

    /**
     * Abstract class that used to do each action for each decorator pattern.
     */
    public abstract void personalAction();

    /**
     * Method that returns the nextPiece it decorates.
     * 
     * @return Returns the nextPiece it decorates.
     */
    public Piece getNextPiece() {
        return nextPiece;
    }

    /**
     * Method that returns the prevPiece it decorates.
     * 
     * @return Returns the prevPiece it decorates.
     */
    public Piece getPrevPiece() {
        return prevPiece;
    }

    public boolean[][] getShape() {
        return this.getLast().getShape();
    }
}
