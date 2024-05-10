package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;
import src.com.java.piece.decorator.Direction;
import src.com.java.piece.decorator.Translation;

/**
 * The Move class represents a move that can be done by a piece.
 * It contains the piece that will be moved and the direction of the move.
 */
public class Move {
    /**
     * The piece that will be moved.
     */
    private final Decorator piece;
    /**
     * The direction of the move.
     */
    private Direction dir = Direction.NORTH;

    /**
     * Creates a new Move object with the given piece.
     * @param piece the piece that will be moved
     */
    public Move(Decorator piece) {
        // Initialize the piece
        this.piece = piece;
        // If the piece is a Translation, get the direction
        if (this.piece instanceof Translation) {
            this.dir = ((Translation) piece).getDir();
        }
    }

    /**
     * Executes the move.
     */
    public void doit(){
        piece.personalAction();
    }

    /**
     * Undoes the move.
     */
    public void undo(){
        if (piece instanceof Translation) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalReverseAction();
    }
}
