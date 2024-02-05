package src.Piece;

import src.Config.Colours;


/**
 * Class used to crate a piece with the O-shape:
 * 0 0 0 0
 * 0 1 1 0
 * 0 1 1 0
 * 0 0 0 0
 */
public final class oShape implements pieces {
    // Color of the piece
    private final Colours color;
    // Shape of the piece
    private boolean[][] shape = new boolean[][]{
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}};
    // If the piece can turn
    private final boolean canTurn;
    // If the piece can move in the x-axis
    private boolean xAxis;
    // If the piece can move in the y-axis
    private boolean yAxis;
    // If the piece can be mirrored
    private final boolean canFlip;


    public oShape(Colours color, boolean canTurn, boolean xAxis, boolean yAxis, boolean canFlip) {
        this.color = color;
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
        // There is no rotation in the O-shape, so nothing to do
        // No changes but still needs to return something
        return shape.clone();
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
}
