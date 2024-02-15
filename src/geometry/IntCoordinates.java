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
     * @param scale default should be 1
     * @return RealCoordinates of IntCoordinates
     */
    public RealCoordinates toRealCoordinates(double scale) {
        return new RealCoordinates(x * scale, y * scale);
    }
}
