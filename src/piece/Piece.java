package src.piece;

import src.geometry.IntCoordinates;


public abstract class Piece {

    /**
     * Method that check
     * @param x  local getX coordinates in a shape array
     * @param y  local getY coordinates in a shape array
     */
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

    /**
     * @param x local getX coordinates in shape array
     * @param y local getY coordinates in shape array
     * @return Returns a String that represent the action type of the nextPiece.
     */
    public String getActionType(int x, int y) {
        return "";
    }
}