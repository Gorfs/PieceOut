package com.java.logic;

import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Direction;
import com.java.piece.decorator.Translation;

import javax.swing.undo.AbstractUndoableEdit;


/**
 * The Move class represents a move that can be done by a piece.
 * It contains the piece that will be moved and the direction of the move.
 */
public class Move extends AbstractUndoableEdit {
    /**
     * The piece that will be moved.
     */
    private final Decorator piece;
    /**
     * The direction of the move.
     */
    private final Direction dir;

    /**
     * Creates a new Move object with the given piece.
     * @param piece the piece that will be moved
     */
    public Move(Decorator piece) {
        // Initialize the piece
        this.piece = piece;
        // If the piece is a Translation, get the direction
        if (piece instanceof Translation) {
            this.dir = ((Translation) piece).getDir();
        } else {
            this.dir = null;
        }
    }

    /**
     * Executes the move.
     */
    public void redo(){
        super.redo();
        if (dir != null) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalAction();
    }

    /**
     * Undoes the move.
     */
    public void undo(){
        super.undo();
        if (dir != null) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalReverseAction();
    }
}
