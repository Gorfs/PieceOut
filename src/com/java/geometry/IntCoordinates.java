package com.java.geometry;

/**
 * Class IntCoordinates that represents the coordinates of the piece.
 */
public class IntCoordinates {
    /**
     * Attributes of IntCoordinates
     * x: coordinates in X-axis
     * y: coordinates on Y-axis
     */
    private int x;
    private int y;

    /**
     * Constructor of IntCoordinates
     * @param x coordinates in X-axis
     * @param y coordinates on Y-axis
     */
    public IntCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * @param pos IntCoordinates to compare with
     * @return Returns true or false, whether equal or not.
     */
    public boolean equals(IntCoordinates pos) {
        return this.getX() == pos.getX() && this.getY() == pos.getY();
    }

    /**
     * @return Returns the coordinates of the piece.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Returns the coordinates of the piece.
     */
    public int getY() {
        return y;
    }

    /**
     * @param x coordinates in X-axis
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @param y coordinates on Y-axis
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return coordinates of IntCoordinates
     */
    public String toString(){
        return ("x:" + x + "\ty: " + y);
    }
}
