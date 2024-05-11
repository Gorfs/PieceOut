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

    /**
     * This method is called when the piece is clicked.
     * It checks if there is a decorator at the position (x, y).
     * If there is a decorator, it calls the personalAction() method of the decorator.
     * @param pieceLogic PieceLogic object that contains the pieces and targets of the game.
     * @param instanceManager InstanceManager object that contains the GUI of the game.
     * @param x local getX coordinates in a shape array
     * @param y local getY coordinates in a shape array
     * @param mousePos coordinates of the mouse
     */
    public abstract void action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y,
                                IntCoordinates mousePos);

    /**
     * This method is used to know the type of action.
     * @param x local getX coordinates in a shape array
     * @param y local getY coordinates in a shape array
     * @return Returns a String that represents the action type of the nextPiece.
     */
    public String getActionType(int x, int y) {
        return "";
    }
}