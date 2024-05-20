package com.java.piece;

import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.PieceLogic;
import com.java.utils.Debug;


/**
 * Abstract class that represents the pieces of the game.
 * It is the base of the decorator pattern.
 */
public abstract class Piece {
    /**
     * This represents the number of times it has been rotated.
     */
    private short rotations = 0;
    /**
     * This represents if the piece has been flipped on the x-axis.
     */
    private boolean xFlipped = false;
    /**
     * This represents if the piece has been flipped on the y-axis.
     */
    private boolean yFlipped = false;
    /**
     * This represents the type of the piece.
     */
    private String type;

    /**
     * This method is used to add a rotation to the piece.
     * @param clockwise boolean that represents the direction of the rotation
     */
    public void addRotation(boolean clockwise){
        Debug.out("ADDING ROTATION TO PIECE WITH ADD ROTATION "+ rotations +  " ");
        if (clockwise) rotations++;
        else rotations += 3;
        rotations %= 4;
    }

    /**
     * This method is used to add a flip to the piece.
     * @param xAxis boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis
     */
    public void addFlip(boolean xAxis){
        if (xAxis) xFlipped = !xFlipped;
        else yFlipped = !yFlipped;
    }

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
     * @return a String that represents the action type of the piece.
     */
    public String getActionType(int x, int y) {
        return "";
    }

    /**
     * @return the current shape of the piece
     */
    public abstract boolean[][] getShape();

    /**
     * @return the amount of time the piece has rotated
     */
    public int getRotations(){
        return rotations;
    }

    /**
     * @return the type of the piece
     */
    public String getType(){
        return type;
    }

    /**
     * Set the type of the piece.
     * @param str the type of the piece
     */
    public void setType(String str){
        type = str;
    }

    /**
     * @return the true if the piece has been flipped in the x-axis
     */
    public boolean isxFlipped() {
        return xFlipped;
    }

    /**
     * @return the true if the piece has been flipped in the y-axis
     */
    public boolean isyFlipped() {
        return yFlipped;
    }

    /**
     * @return Returns a String that represents the piece.
     */
    public String toString() {
        return "";
    }
}