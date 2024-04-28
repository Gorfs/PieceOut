package src.com.java.piece;

import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;


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
    public InstanceManager guiManager;

    public abstract void action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos);

    /**
     * @param x local getX coordinates in shape array
     * @param y local getY coordinates in shape array
     * @return Returns a String that represent the action type of the nextPiece.
     */
    public String getActionType(int x, int y) {
        return "";
    }
}