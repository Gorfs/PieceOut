package src.piece;

import src.geometry.IntCoordinates;
import src.gui.GUIManager;
import src.logic.PieceLogic;


public abstract class Piece {

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
    public GUIManager guiManager;

    public abstract void action(PieceLogic pieceLogic, int x, int y);

    /**
     * @param x local getX coordinates in shape array
     * @param y local getY coordinates in shape array
     * @return Returns a String that represent the action type of the nextPiece.
     */
    public String getActionType(int x, int y) {
        return "";
    }
}