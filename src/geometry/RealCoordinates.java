package src.geometry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @param x coordinates in x-axis
 * @param y coordinates on y-axis
 */
public record RealCoordinates(double x, double y) {

    public static final RealCoordinates ZERO = new RealCoordinates(0, 0);
    public static final RealCoordinates NORTH_UNIT = new RealCoordinates(0, -1);
    public static final RealCoordinates EAST_UNIT = new RealCoordinates(1, 0);
    public static final RealCoordinates SOUTH_UNIT = new RealCoordinates(0, 1);
    public static final RealCoordinates WEST_UNIT = new RealCoordinates(-1, 0);


    /**
     * Method used to add to the current RealCoordinates the RealCoordinates 'other'.
     * @param other object type RealCoordinates
     * @return new RealCoordinates with the new values.
     */
    public RealCoordinates plus(RealCoordinates other) {
        return new RealCoordinates(x + other.x, y + other.y);
    }

    /**
     * Method used to multiply to the current RealCoordinates double 'multiplier'.
     * @param multiplier object type double
     * @return new RealCoordinates with the new values.
     */
    public RealCoordinates times(double multiplier) {
        return new RealCoordinates(x * multiplier, y * multiplier);
    }

    /**
     * @return the coordinates of all integer squares that a unit square with current coordinates would intersect
     */
    public Set<IntCoordinates> intNeighbours() {
        return new HashSet<>(List.of(
                new IntCoordinates((int) Math.floor(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.floor(x), (int) Math.ceil(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.floor(y)),
                new IntCoordinates((int) Math.ceil(x), (int) Math.ceil(y))
        ));
    }


    /**
     * @return IntCoordinates of the current RealCoordinates
     */
    public IntCoordinates round() {
        return new IntCoordinates((int) Math.round(x), (int) Math.round(y));
    }

    /**
     * @return flatten x coordinates of the current RealCoordinates
     */
    public RealCoordinates floorX() {
        return new RealCoordinates((int) Math.floor(x), y);
    }

    /**
     * @return flatten y coordinates of the current RealCoordinates
     */
    public RealCoordinates floorY() {
        return new RealCoordinates(x, (int) Math.floor(y));
    }

    /**
     * @return go to the next int x coordinates of the current RealCoordinates
     */
    public RealCoordinates ceilX() {
        return new RealCoordinates((int) Math.ceil(x), y);
    }

    /**
     * @return go to the next int y coordinates of the current RealCoordinates
     */
    public RealCoordinates ceilY() {
        return new RealCoordinates(x, (int) Math.ceil(y));
    }

    /**
     * Method that return the new position of the current RealCoordinates
     * @param width to add to x-axis
     * @param height to add to y-axis
     * @return new RealCoordinates with the new values
     */
    public RealCoordinates warp(int width, int height) {
        var rx = x;
        var ry = y;
        while (Math.round(rx) < 0)
            rx += width;
        while (Math.round(ry) < 0)
            ry += height;
        while (Math.round(rx) >= width)
            rx -= width;
        while (Math.round(rx) >= height)
            ry -= height;
        return new RealCoordinates(rx, ry);
    }
}
