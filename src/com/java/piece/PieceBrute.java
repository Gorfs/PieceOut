package com.java.piece;

import com.java.geometry.IntCoordinates;

import java.awt.*;


/**
 * Abstract class used to create pieces for the game.
 * It is the base for creating piece.
 * @see Piece
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
     * Array of IntCoordinates(x,y) that contains the coordinates where shape[y][x] is true.
     */
    private IntCoordinates[] posShape;

    /**
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the pivot point of the piece
     */
    public IntCoordinates getFixturePoint(){
        return fixturePoint;
    }

    /**
     * @return the current shape of the piece
     */
    public boolean[][] getShape() {
        return shape;
    }

    /**
     * Method that set a new shape to the piece.
     * @param shape New shape we want to give to our piece
     */
    public void setShape(boolean[][] shape) {
        this.shape = shape;
        this.posShape = generatePosShape();
    }

    /**
     * @return the position of the piece
     */
    public IntCoordinates getPos() {
        return pos;
    }

    /**
     * Manually change the position of the piece.
     * @param pos New position of the piece
     */
    public void setPos(IntCoordinates pos) {
        this.pos = pos;
    }

    /**
     * @return IntCoordinates of the offset from the piece to the main arena grid
     */
    public IntCoordinates getOffset(){
        // returns new int-coordinates with the offset from the piece added

        int pivotPointX = this.getFixturePoint().getX(); // distance to a border of piece matrix
        int pivotPointY = this.getFixturePoint().getY(); // ''

        int posX = this.getPos().getX();
        int posY = this.getPos().getY();

        int xOffset = posX - pivotPointX;
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset); 

    }

    /**
     * Method that generates the coordinates of the shape matrix where shape[y][x] is true.
     * @return an array of IntCoordinates(x,y) that contains the coordinates where shape[y][x] is true
     */
    public IntCoordinates[] generatePosShape() {
        if (this.getShape() == null) {
            return null;
        }
        IntCoordinates[] pos = new IntCoordinates[this.getShape().length * this.getShape()[0].length];
        int n = 0;
        for (int i = 0; i < this.getShape().length; i++) {
            for (int j = 0; j < this.getShape()[0].length; j++) {
                if (this.getShape()[i][j]) {
                    pos[n] = new IntCoordinates(j, i);
                    n++;
                }
            }
        }
        IntCoordinates[] pos2 = new IntCoordinates[n];
        System.arraycopy(pos, 0, pos2, 0, n);
        return pos2;
    }

    /**
     * Sets the which point of the shape matrix to fix to the pos on the main arena grid
     * @param fixturePoint IntCoordinates of the fixture point of the piece.
     */
    public void setFixturePoint(IntCoordinates fixturePoint) {
        this.fixturePoint = fixturePoint;
    }

    /**
     * @return an array of IntCoordinates(x,y) that contains the coordinates where shape[y][x] is true
     */
    public IntCoordinates[] getPosShape() {
        return posShape;
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

