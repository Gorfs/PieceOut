package src.Piece;

import src.Config.Colours;
import src.Geometry.IntCoordinates;


/**
 * Class used to crate a piece with the T-shape:
 * 0 0 0 0
 * 0 0 0 0
 * 0 1 1 1
 * 0 0 1 0
 */
public final class tShape implements pieces {
    // Color of the piece
    private final Colours color;
    // Pivot point of the piece
    private IntCoordinates pivotPoint;
    // Shape of the piece
    private boolean[][] shape = new boolean[][]{
            {false, false, false, false},
            {false, false, false, false},
            {false, true, true, true},
            {false, false, true, false}};
    // If the piece can turn
    private final boolean canTurn;
    // If the piece can move in the x-axis
    private boolean xAxis;
    // If the piece can move in the y-axis
    private boolean yAxis;
    // If the piece can be mirrored
    private final boolean canFlip;


    public tShape(Colours color, IntCoordinates pivotPoint, boolean canTurn, boolean xAxis, boolean yAxis, boolean canFlip) {
        this.color = color;
        this.pivotPoint = pivotPoint;
        this.canTurn = canTurn;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.canFlip = canFlip;
    }

    @Override
    public boolean[][] rotateShape() {
        // Rotate the translation axis
        if (xAxis || yAxis) {
            xAxis = !this.xAxis;
            yAxis = !this.yAxis;
        }

        // Save the one shape just in case
        boolean[][] oldShape = shape.clone();

        // Rotate the new shape
        this.pivotPoint = new IntCoordinates(pivotPoint.y(), shape[pivotPoint.x()].length - pivotPoint.x() - 1);
        for (int x = 0; x < shape.length; x++) {
            for (int y = 0; y < shape[x].length; y++) {
                shape[x][y] = oldShape[y][shape[x].length - x - 1];
            }
        }
        return oldShape;
    }

    @Override
    public Colours getColor() {
        return color;
    }

    @Override
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }

    @Override
    public boolean[][] getShape() {
        return this.shape;
    }

    @Override
    public boolean getCanTurn() {
        return canTurn;
    }

    @Override
    public boolean getXAxis() {
        return xAxis;
    }

    @Override
    public boolean getYAxis() {
        return yAxis;
    }

    @Override
    public boolean getCanFlip() {
        return canFlip;
    }

    @Override
    public IntCoordinates getPivotPoint() {
        return pivotPoint;
    }
}
