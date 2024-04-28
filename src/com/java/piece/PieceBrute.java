package src.com.java.piece;


import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;

import java.awt.Color;


/**
 * Abstract class used to create pieces for the game.
 */
public abstract class PieceBrute extends Piece {
    
    /**
     * Color of the nextPiece.
     */
    protected Color color;
    /**
     * Position of the nextPiece.
     */
    private IntCoordinates pos;
    /**
     * Pivot point of the piece.
     */
    private IntCoordinates fixturePoint;// coordinates of the point where the piece is attached to pos.
    /**
     * Shape of the piece.
     */
    private boolean[][] shape;


    private Target target;

    /**
     * Nothing to do here, we went through all the decorator pattern.
     */
    public void action(int x , int y) {}

    /**
     * @return Returns the color of the nextPiece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return Returns the target of the piece.
     */

    public Target getTarget() {
        return target;
    }

    /**
     * @param target Target of the piece.
     */

    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * @return Returns the pivot point of the piece.
     */
    public IntCoordinates getFixturePoint(){
        return fixturePoint;
    }


    /**
     * @return Returns the current shape of the piece.
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * Method that set a new shape to the nextPiece.
     * @param shape New shape we want to give to our nextPiece.
     */
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }

    /**
     * @return Returns the position of the nextPiece.
     */
    public IntCoordinates getPos() {
        return pos;
    }

    /**
     * Manually change the position of the nextPiece.
     * @param pos New position of the nextPiece.
     */
    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (boolean[] line: shape) {
            for (boolean tile: line) {
                if (tile) text.append("1 ");
                else text.append("0 ");
            }
            text.append("\n");
        }
        return text.toString();
    }


    /**
     * @return IntCoordinates
     */
    public IntCoordinates getOffset(){
        // returns new int-coordinates with the offset from the nextPiece added

        int pivotPointX = this.getFixturePoint().getX(); // distance to border of nextPiece matrix
        int pivotPointY = this.getFixturePoint().getY(); // ''

        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int xOffset = posX - pivotPointX;
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset); 

    }


    public IntCoordinates getDestOffset(){
        // returns new int-coordinates with the offset from the piece added

        int pivotPointX = getFixturePoint().getX(); // distance to border of piece matrix
        int pivotPointY = getFixturePoint().getY(); // ''

        int posX = getTarget().getDest().getX();
        int posY = getTarget().getDest().getY();

        int xOffset = posX - pivotPointX; 
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset);
    }

    /**
     * sets the which point of the shape matrix to fix to the pos on the main arena grid
     * @param fixturePoint
     */
    public void setFixturePoint(IntCoordinates fixturePoint) {
        this.fixturePoint = fixturePoint;
    }
}

