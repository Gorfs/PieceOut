package com.java.piece;

import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.PieceLogic;
import com.java.utils.Debug;


public abstract class Piece {

    /**
     * @return Returns the current shape of the nextPiece.
     */
    public abstract boolean[][] getShape();

    // this represents the number of times it has been rotated
    // for a clockwise rotation add 1
    // for an anticlockwise rotation add 3
    // always mod 4 it.
    public short rotations = 0;

    public boolean xFlipped = false;
    public boolean yFlipped = false;

    public void addRotation(boolean clockwise){
        Debug.out("ADDING ROTATION TO PIECE WITH ADD ROTATION "+ rotations +  " ");
        if (clockwise) rotations++;
        else rotations += 3;
        rotations %= 4;
    }

    public void addFlip(boolean xAxis){
        if (xAxis) xFlipped = !xFlipped;
        else yFlipped = !yFlipped;
    }


    public String type;
    public void setType(String str){
        type = str;
    }

    /**
     * @return Returns a String that represents the nextPiece.
     */
    public String toString() {
        return "";
    }
    public InstanceManager instanceManager;

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
    public abstract boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y,
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