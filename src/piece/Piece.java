package src.piece;

import src.geometry.IntCoordinates;


public abstract class Piece {
    /**
     * Method that check
     * @param x  local x coordinates in shape array
     * @param y  local y coordinates in shape array
     */
    public boolean decorated = false;
    public abstract void action(int x, int y);

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


    public boolean isDecorated() {
        return this.decorated;
    }
}
