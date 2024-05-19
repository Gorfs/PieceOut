package com.java.piece.decorator;

import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.Move;
import com.java.logic.PieceLogic;
import com.java.piece.Piece;
import com.java.piece.PieceBrute;
import com.java.utils.Debug;


/**
 * Abstract class for the decorators. It extends 'Piece' because it uses the
 * method of 'decorator design pattern'. It is the base for creating decorators.
 * @see Piece
 */
public abstract class Decorator extends Piece {
    /**
     * Previous piece in the decorator pattern.
     */
    protected Piece prevPiece;
    /**
     * Next piece in the decorator pattern.
     */
    protected Decorator nextPiece;
    /**
     * Local position of the decorator.
     */
    protected IntCoordinates localPos;
    /**
     * the point which we apply the action too.
     */
    private IntCoordinates actionPoint;

    /**
     * Constructor for the decorators.
     * @param p Piece on which we add the decorator
     */
    public Decorator(Piece p, IntCoordinates pos) {
        // if the piece is a decorator, setting the current piece as the next piece for p.
        try {
            Decorator p1 = (Decorator) p;
            p1.setNextPiece(this);
        } catch (Exception e) {
            System.out.println("The given piece is not a decorator. It is a base piece.");
            System.out.println("Nothing else to do here.");
        }
        this.prevPiece = p;
        this.localPos = pos;
        this.actionPoint = pos;
        this.nextPiece = null;
    }

    @Override
    public boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // If it is the right action we pressed on, then do the action.
        if (this.actionPoint.getX() == x && this.actionPoint.getY() == y) {
            // Do the action
            personalAction();
            Debug.printDecorator(this);
            if (checkCollision(pieceLogic, instanceManager)) {
                // If the piece is colliding with the walls, then we reverse the action.
                Debug.out("Collision detected");
                personalReverseAction();
                instanceManager.getSoundManager().playCollisionSound();
                return false;
            } else {
                // If not, then we add the move to the undo manager.
                Debug.out("No collision detected");
                instanceManager.getPlayerController().getUm().addEdit(new Move(this));
                instanceManager.incMoves();
                instanceManager.getSoundManager().playMoveSound();
                return true;
            }
        } else {
            // If not, then we check for the next decorator.
            return this.prevPiece.action(pieceLogic, instanceManager, x, y, mousePos);
        }
    }

    /**
     * Abstract method that used to do each action for each decorator pattern.
     */
    public abstract void personalAction();

    /**
     * Abstract method that used to reverse each action for each decorator pattern.
     */
    public abstract void personalReverseAction();

    /**
     * Apply rotation action to all the decorators, so they flip with the piece.
     * @param clockwise boolean that true when we rotate clockwise
     */
    public void applyRotation(boolean clockwise) {
        rotateActionPoint(clockwise);
    }

    /**
     * Rotates the action point of the piece.
     * @param clockwise boolean that true when we rotate clockwise
     */
    public void rotateActionPoint(boolean clockwise) {
        // Get the shape of the piece
        boolean[][] shape = this.getPiece().getShape();
        // Variable to store if the action point of the piece has already been rotated
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (i == this.actionPoint.getY() && j == this.actionPoint.getX()) {
                    if (clockwise) this.setActionPoint(new IntCoordinates(shape.length - i - 1, j));
                    else this.setActionPoint(new IntCoordinates(i, shape[0].length - j - 1));
                    return;
                }
            }
        }
    }

    /**
     * Apply flip action to all the decorators, so they flip with the piece.
     * @param xAxis boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis.
     */
    public void applyFlip(boolean xAxis) {
        // default applyFlip
        // updating the decorator's action points for a flip depending on the axis of flip
        this.flipActionPoint(xAxis);
        // recursively apply flip to the rest of the list
        if (this.getPrevPiece() instanceof Decorator) {
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    /**
     * Flips the action point of the piece.
     * @param xAxis boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis.
     */
    public void flipActionPoint(boolean xAxis) {
        boolean[][] shape = this.getPiece().getShape();
        if (!xAxis) {
            this.actionPoint = new IntCoordinates(shape[0].length - this.actionPoint.getX() - 1,
                    this.actionPoint.getY());
        } else {
            this.actionPoint = new IntCoordinates(this.actionPoint.getX(),
                    shape.length - this.actionPoint.getY() - 1);
        }
    }

    /**
     * Method that offsets the position of the piece.
     * Offsets the position of the piece to compensate for change of fixture Point.
     * @param actionArea the point where the action is applied
     */
    public void offsetPosition(IntCoordinates actionArea) {
        // Get the offset of the piece
        int xOffset = this.getPiece().getFixturePoint().getX() - actionArea.getX();
        int yOffset = this.getPiece().getFixturePoint().getY() - actionArea.getY();
        // Get the position of the piece
        IntCoordinates pos = this.getPiece().getPos();
        // Update the position of the piece with the offset to add
        this.getPiece().setPos(new IntCoordinates(pos.getX() - xOffset, pos.getY() - yOffset));
    }


    /**
     * @return the prevPiece it decorates.
     */
    public boolean checkCollision(PieceLogic pieceLogic, InstanceManager iManager) {
        // Get the position of the piece
        IntCoordinates piecePos = this.getPiece().getOffset();
        boolean[][] matrix = iManager.getArena().getMatrix();
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
                        last = piece.getPiece();
                        if (this.getPiece() != last) {
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
     * Method that returns the action type of the correct Piece.
     * @param x local getX coordinates in a shape array
     * @param y local getY coordinates in a shape array
     * @return a String that represents the action of the decorator
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

    /**
     * @return the base of the piece, where are stocked the position and the shape of the piece
     */
    public PieceBrute getPiece() {
        Piece temp = prevPiece;
        while (!(temp instanceof PieceBrute))
            temp = ((Decorator) temp).getPrevPiece();
        return ((PieceBrute) temp);
    }

    /**
     * @return the last decorator in the chain added to the piece
     */
    public Decorator getLast() {
        Decorator temp = this;
        while (!(temp.getNextPiece() == null))
            temp = (Decorator) temp.getNextPiece();
        return temp;
    }

    public Piece getPrevPiece() {
        return prevPiece;
    }

    /**
     * Method that returns the nextPiece it decorates.
     * @return the nextPiece it decorates
     */
    public Piece getNextPiece() {
        return nextPiece;
    }

    /**
     * Method that sets the nextPiece it decorates.
     * @param p Piece that we want to set as the nextPiece
     */
    public void setNextPiece(Decorator p) {
        this.nextPiece = p;
    }

    /**
     * @return Returns the local position of the decorator
     */
    public IntCoordinates getActionPoint() {return this.actionPoint;}

    /**
     * Method that sets the action point of the piece.
     * @param p the point which we apply the action too
     */
    public void setActionPoint(IntCoordinates p) {
        this.actionPoint = p;
    }

    /**
     * @return the shape of the piece.
     */
    public boolean[][] getShape() {
        return this.getPiece().getShape();
    }
}
