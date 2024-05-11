package src.com.java.piece;


import src.com.java.geometry.IntCoordinates;

import java.awt.*;


/**
 * Abstract class used to create pieces for the game.
 */
public abstract class PieceBrute extends Piece {
    /**
     * Color of the piece.
     */
    protected Color color;
    /**
     * Position of the piece.
     */
    private IntCoordinates pos;
    /**
     * Coordinates of the point where the piece is attached to.
     */
    private IntCoordinates fixturePoint;
    /**
     * Shape of the piece.
     */
    private boolean[][] shape;

    /**
     * @return the color of the piece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the pivot point of the piece.
     */
    public IntCoordinates getFixturePoint(){
        return fixturePoint;
    }

    /**
     * @return the current shape of the piece.
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * Method that set a new shape to the piece.
     * @param shape New shape we want to give to our piece.
     */
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }

    /**
     * @return Returns the position of the piece.
     */
    public IntCoordinates getPos() {
        return pos;
    }

    /**
     * Manually change the position of the piece.
     * @param pos New position of the piece.
     */
    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }

    /**
     * @return IntCoordinates
     */
    public IntCoordinates getOffset(){
        // returns new int-coordinates with the offset from the piece added

        int pivotPointX = this.getFixturePoint().getX(); // distance to border of piece matrix
        int pivotPointY = this.getFixturePoint().getY(); // ''

        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int xOffset = posX - pivotPointX;
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset); 

    }

    /**
     * sets the which point of the shape matrix to fix to the pos on the main arena grid
     * @param fixturePoint IntCoordinates of the fixture point of the piece.
     */
    public void setFixturePoint(IntCoordinates fixturePoint) {
        this.fixturePoint = fixturePoint;
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
}

