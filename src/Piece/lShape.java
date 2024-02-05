package src.Piece;

import src.Config.Colours;


/**
 * Class used to crate a piece with the J-shape:
 * 0 0 0 0
 * 0 0 1 0
 * 0 0 1 0
 * 0 1 1 0
 */
public final class lShape implements pieces {
    // Color of the piece
    private final Colours color;
    // Shape of the piece
    private boolean[][] shape = new boolean[][]{
            {false, false, false, false},
            {false, false, true, false},
            {false, false, true, false},
            {false, false, true, true}};
    // If the piece can turn
    private final boolean canTurn;
    // If the piece can move in the x-axis
    private boolean xAxis;
    // If the piece can move in the y-axis
    private boolean yAxis;
    // If the piece can be mirrored
    private final boolean canFlip;


    public lShape(Colours color, boolean canTurn, boolean xAxis, boolean yAxis, boolean canFlip) {
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
        // Save the one shape just in case
        boolean[][] oldShape = shape.clone();
        // Rotate the new shape
        // Rotate the J angle
        if (shape[1][1]) {
            shape[1][1] = false;
            shape[1][3] = true;
        } else if (shape[1][3]) {
            shape[1][3] = false;
            shape[3][3] = true;
        } else if (shape[3][3]) {
            shape[3][3] = false;
            shape[3][1] = true;
        } else if (shape[3][1]) {
            shape[3][1] = false;
            shape[1][1] = true;
        }
        // Rotate the J bar
        if (shape[1][2]) {
            shape[1][2] = false;
            shape[3][2] = false;
            shape[2][1] = true;
            shape[2][3] = true;
        } else {
            shape[1][2] = true;
            shape[3][2] = true;
            shape[2][1] = false;
            shape[2][3] = false;
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
}
