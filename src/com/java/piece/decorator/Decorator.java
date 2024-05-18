package src.com.java.piece.decorator;

import src.com.java.utils.Debug;
import src.com.java.piece.Piece;
import src.com.java.piece.PieceBrute;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.Move;
import src.com.java.logic.PieceLogic;

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
    private IntCoordinates actionPoint;

    /**
     * Constructor for the decorators.
     * 
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
        this.nextPiece = null;
    }

    public IntCoordinates getActionPoint() {return this.actionPoint;}

    public void setActionPoint(IntCoordinates p) {
        this.actionPoint = p;
    }

    /**
     * applys the necessary actions for the movement to be adjusted to a new
     * rotation
     */
    public void applyRotation(boolean clockwise) {
        rotateActionPoint(clockwise);
    }

    /**
     * rotates the action point of the piece
     * @param clockwise
     */
    public void rotateActionPoint(boolean clockwise) {
        // rotate the action point of the piece
        // used in the applyRotation() methode

        boolean[][] shape = this.getLast().getShape();
        boolean alreadyRotated = false;
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (i == this.actionPoint.getY() && j == this.actionPoint.getX() && !alreadyRotated) {
                    alreadyRotated = true;
                    if (clockwise) {
                        this.setActionPoint(new IntCoordinates(shape.length - i - 1, j));

                    } else {
                        this.setActionPoint(new IntCoordinates(i, shape[0].length - j - 1));
                    }

                }
            }

        }
    }

    public void flipActionPoint(boolean xAxis) {
        boolean[][] shape = this.getLast().getShape();
        if (!xAxis) {
            this.actionPoint = new IntCoordinates(shape[0].length - this.actionPoint.getX() - 1, this.actionPoint.getY());
        } else {
            this.actionPoint = new IntCoordinates(this.actionPoint.getX(), shape.length - this.actionPoint.getY() - 1);
        }
    }

    /**
     * same as for the applyRotation() methode but for the flip action
     */
    public void applyFlip(boolean xAxis) {
        // default applyFlip
        // updating the decorator's action points for a flip depending on axis of flip
        this.flipActionPoint(xAxis);
        // recursively apply flip to rest of the list
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    public void offsetPosition(IntCoordinates actionArea) {
        // offset the position of the piece to compensate for change of fixture Point
        // change

        int xOffset = this.getLast().getFixturePoint().getX() - actionArea.getX();
        int yOffset = this.getLast().getFixturePoint().getY() - actionArea.getY();

        IntCoordinates pos = this.getLast().getPos();

        this.getLast().setPos(new IntCoordinates(pos.getX() - xOffset, pos.getY() - yOffset));
    }

    public boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // If it is the right action we pressed on, then do the action.
        // (deal with offset)
        if (this.actionPoint.getX() == x && this.actionPoint.getY() == y) {
            personalAction();
            Debug.printDecorator(this);
            if (checkCollision(pieceLogic)) {
                Debug.out("Collision detected");
                personalReverseAction();
                instanceManager.getSound().playCollisionSound();
                return false;
            } else {
                Debug.out("No collision detected");
                instanceManager.playerController.um.addEdit(new Move(this));
                instanceManager.incMoves();
                instanceManager.getSound().playMoveSound();
                return true;
            }
            // If not, then we check for the next decorator.
        } else {
            return this.prevPiece.action(pieceLogic, instanceManager, x, y, mousePos);
        }

    }

    /**
     * Method that returns the action type of the correct Piece.
     * 
     * @param x local getX coordinates in shape array
     * @param y local getY coordinates in shape array
     * @return Returns a String of the action type of the Piece.
     */
    public String getActionType(int x, int y) {
        if (this.actionPoint.getX() == x && this.actionPoint.getY() == y) {
            switch (this.getClass().getSimpleName()) {
                case "Translation":
                    return "Translation";
                case "Rotation":
                    if (((Rotation) this).getRotationDirection()) {
                        return "RotationR";
                    } else {
                        return "RotationL";
                    }
                case "Flip":
                    if (((Flip) this).getFlipAxis()) {
                        return "FlipX";
                    } else {
                        return "FlipY";
                    }
                default:
                    return "";
            }
        } else {
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
     * 
     * @return true if the piece is colliding with the walls
     */
    public boolean checkCollision(PieceLogic pieceLogic) {
        // Get the position of the piece
        IntCoordinates piecePos = this.getLast().getOffset();
        boolean[][] matrix = this.getLast().guiManager.arena.getMatrix();
        // Variable to store the last piece
        PieceBrute last;
        for (int i = 0; i < this.getShape().length; i++) {
            for (int j = 0; j < this.getShape()[i].length; j++) {
                if (this.getShape()[i][j]) {
                    // Check if the piece is colliding with the walls
                    if (piecePos.getX() + j < 0 || piecePos.getX() + j >= matrix[0].length || piecePos.getY() + i < 0
                            || piecePos.getY() + i >= matrix.length) {
                        return true;
                    }
                    if (!matrix[piecePos.getY() + i][piecePos.getX() + j]) {
                        return true;
                    }
                    // Check if the piece is colliding with another piece
                    for (var piece : pieceLogic.getPieces()) {
                        last = piece.getLast();
                        if (this.getLast() != last) {
                            for (int k = 0; k < last.getShape().length; k++) {
                                for (int l = 0; l < last.getShape()[k].length; l++) {
                                    if (last.getShape()[k][l]) {
                                        if (piecePos.getX() + j == last.getOffset().getX() + l
                                                && piecePos.getY() + i == last.getOffset().getY() + k) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
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
     * 
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
