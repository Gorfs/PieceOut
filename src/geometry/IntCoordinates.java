package src.geometry;

/**
 * @param x coordinates in x-axis
 * @param y coordinates on y-axis
 */
public record IntCoordinates(int x, int y) {

    /**
     * @return coordinates of IntCoordinates
     */
    public String toString(){
        return ("x:" + x + "\ty: " + y);
    }

    /**
     * @param pos IntCoordinates to compare with
     * @return Returns true or false, whether equal or not.
     */
    public boolean equals(IntCoordinates pos) {
        return this.x() == pos.x() && this.y() == pos.y();
    }
}
