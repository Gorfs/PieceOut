package src.piece;

import src.geometry.IntCoordinates;


public abstract class Piece {
    /**
     * Method that check
     */
    public abstract void action(IntCoordinates pos);

    /**
     * @return Returns the current shape of the nextPiece.
     */
    public abstract boolean[][] getShape();

    /**
     * @return Returns a String that represents the nextPiece.
     */
    public String toString() {
        return "";
    }
}
