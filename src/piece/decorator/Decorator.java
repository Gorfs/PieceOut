package src.piece.decorator;

import src.controllers.PlayerController;
import src.geometry.IntCoordinates;
import src.logic.Arena;
import src.logic.Move;
import src.logic.MoveEdit;
import src.piece.Piece;
import src.piece.PieceBrute;
import src.utils.Debug;

/**
 * Abstract class for the decorators. It extends 'Piece' because it uses the
 * method of 'decorator design pattern'.
 */
public abstract class Decorator extends Piece {

    /**
     * nextPiece on which we add the decorator.
     */
    protected Piece nextPiece;
    protected Piece prevPiece;
    protected IntCoordinates localCoord;

    // the point which we apply the action too
    public IntCoordinates actionPoint;

    /**
     * Constructor for the decorators.
     * @param p Piece on which we add the decorator.
     */
    public Decorator(Piece p, IntCoordinates local) {
        // if the piece is a decorator, setting the next piece to allow for getting of
        // the first element of the list
        try {
            Decorator p1 = (Decorator) p;
            p1.setNextPiece(this);
        } catch (Exception e) {
            // TODO handle this exception to make it look prettier
        }
        this.prevPiece = p;
        this.localCoord = local;
        this.actionPoint = local;
        this.decorated = true;
        this.nextPiece = null;
    }

    public void setActionPoint(IntCoordinates p) {
        this.actionPoint = p;
    }

    /**
     * applys the necessary actions for the movement to be adjusted to a new
     * rotation
     */
    public void applyRotation(boolean clockwise) {
        return;
    }

    /**
     * same as for the applyRotation() methode but for the flip action
     */
    public void applyFlip(boolean xAxis) {}

    public void offsetPosition(IntCoordinates actionArea) {
        // move the position of a piece so that it is in the same place on the board but with a new fixture point

        int xOffset = this.getLast().getFixturePoint().x() - actionArea.x();
        int yOffset = this.getLast().getFixturePoint().y() - actionArea.y();

        IntCoordinates pos = this.getLast().getPos();
        // Debug.out("Current Coords: " + pos.x() + " " + pos.y() + " " + xOffset + " " + yOffset);
        // Debug.out("Action Coords: " + actionArea.x() + " " + actionArea.y());
        // Debug.out("Fixture Coords: " + this.getLast().getFixturePoint().x() + " " + this.getLast().getFixturePoint().y());


        this.getLast().setPos(new IntCoordinates(pos.x()  -  xOffset, pos.y() - yOffset));

        // Debug.out("New Coords: " + this.getLast().getPos().x() + " " + this.getLast().getPos().y());


        // last step, apply the new fixture Point
        // this.getLast().setFixturePoint(actionArea);
        // Debug.printShapePieces(this.getLast());

    }

    @Override
    public void action(int x, int y) {
        // If it is the right action we pressed on, then do the action.
        // (deal with offset)
        if (this.actionPoint.x() == x && this.actionPoint.y() == y){
            personalAction();
            if (checkCollision()) {
                Debug.out("Collision detected");
                personalReverseAction();
            } else {
                Debug.out("No collision detected");
                PlayerController.um.addEdit(new MoveEdit(new Move(this)));
                Debug.out(PlayerController.um.canUndo() ? "can Undo" : "cannot undo");
                Debug.out(PlayerController.um.canRedo() ? "can Redo" : "cannot redo");
            }
            // If not, then we check for the next decorator.
        }else{
            this.prevPiece.action(x, y);
        }

    }

    /**
     * Method that returns the action type of the correct Piece.
     * @param x local x coordinates in shape array
     * @param y local y coordinates in shape array
     * @return Returns a String of the action type of the Piece.
     */
    public String getActionType(int x, int y){
        if (this.actionPoint.x() == x && this.actionPoint.y() == y){
            switch(this.getClass().getSimpleName()){
                case "Translation":
                    return "Translation";
                case "Rotation":
                    return "Rotation";
                case "Flip":
                    return "Flip";
                default:
                    return "";
            }
        }
        else{
            return this.prevPiece.getActionType(x, y);
        }
    }

    // returns the piece brute at the very front of the chained list
    public PieceBrute getLast() {
        Piece temp = prevPiece;
        while (!(temp instanceof PieceBrute))
            temp = ((Decorator) temp).getPrevPiece();
        return ((PieceBrute) temp);
    }

    // returns the last decorator in the chain
    public Piece getFirst() {
        Decorator temp = this;
        while (!(temp.getNextPiece() == null))
            temp = (Decorator) temp.getNextPiece();
        return temp;
    }

    /**
     * Method that checks collision for the piece.
     * @return true if the piece is colliding with the walls
     */
    protected boolean checkCollision() {
        // Get the position of the piece
        IntCoordinates piecePos = this.getLast().getOffset();
        // Get the matrix of the arena
        boolean[][] matrix = Arena.getInstance().getMatrix();
        // For each cell in the piece
        for (int i = 0; i < this.getShape().length; i++) {
            for (int j = 0; j < this.getShape()[i].length; j++) {
                if (this.getShape()[i][j]) {
                    // Check if the piece is colliding with the walls
                    if (piecePos.x() + j < 0 || piecePos.x() + j >= matrix[0].length ||
                            piecePos.y() + i < 0 || piecePos.y() + i >= matrix.length) {
                        return true;
                    } if (!matrix[piecePos.y() + i][piecePos.x() + j]) {
                        return true;
                    } // Might need to swap the x and y here
                }
            }
        }
        return false;
    }

    /**
     * Abstract class that used to do each action for each decorator pattern.
     */
    public abstract void personalAction();

    public abstract void personalReverseAction();

    /**
     * Method that returns the nextPiece it decorates.
     * 
     * @return Returns the nextPiece it decorates.
     */
    public Piece getNextPiece() {
        return nextPiece;
    }

    /**
     * Method that sets the nextPiece it decorates.
     * @param p
     */
    public void setNextPiece(Piece p) {
        this.nextPiece = p;
    }

    /**
     * Method that returns the prevPiece it decorates.
     * 
     * @return Returns the prevPiece it decorates.
     */
    public Piece getPrevPiece() {
        return prevPiece;
    }

    public boolean[][] getShape() {
        return this.getLast().getShape();
    }
}
