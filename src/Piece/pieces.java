package src.Piece;

import src.Config.Colours;
import src.Geometry.IntCoordinates;


/**
 * Interface used to create pieces for the game.
 */
public sealed interface pieces permits jShape, lShape, oShape, tShape {

    /**
     * Method that rotate the shape of the piece by -90°(or 270°) clockwise, by 90° counterclockwise.
     * @return Returns the old shape in cas we need to cancel any actions.
     */
    boolean[][] rotateShape();

    /**
     * @return Returns the color of the piece.
     */
    Colours getColor();

    /**
     * Method that set a new shape to the piece.
     * @param shape New shape we want to give to our piece.
     */
    void setShape(boolean[][] shape);

    /**
     * @return Returns the current shape of the piece.
     */
    boolean[][] getShape();


    /**
     * @return Returns true if the piece can be turned, false otherwise.
     */
    boolean getCanTurn();

    /**
     * @return Returns true if the piece can be moved in the x-axis, false otherwise.
     */
    boolean getXAxis();

    /**
     * @return Returns true if the piece can be moved in the y-axis, false otherwise.
     */
    boolean getYAxis();

    /**
     * @return Returns true if the piece can be mirrored, false otherwise.
     */
    boolean getCanFlip();

    /**
     * @return Returns the pivot point of the piece.
     */
    IntCoordinates getPivotPoint();
}
