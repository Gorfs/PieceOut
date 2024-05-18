package src.com.java.piece;

import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;
import src.com.java.utils.Debug;


public abstract class Piece {

    /**
     * @return Returns the current shape of the nextPiece.
     */
    public abstract boolean[][] getShape();

    // this represents the amoutn of times it has been rotated
    // for a clockwise rotation add 1
    // for an anticlockwise rotation add 3
    // always mod 4 it.
    public short rotations = 0;


    public boolean xflip = false;
    public boolean yflip = false;

    public void addRotation(boolean clockwise){
        Debug.out("ADDING ROTATION TO PIECE WITH ADDROTATION "+ rotations +  " ");
        if (clockwise) rotations++;
        else rotations += 3;
        rotations %= 4;
    }

    public void addFlip(boolean xAxis){
        if (xAxis) xflip = !xflip;
        else yflip = !yflip;
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
    public InstanceManager guiManager;

    public abstract boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos);

    /**
     * @param x local getX coordinates in shape array
     * @param y local getY coordinates in shape array
     * @return Returns a String that represent the action type of the nextPiece.
     */
    public String getActionType(int x, int y) {
        return "";
    }
}