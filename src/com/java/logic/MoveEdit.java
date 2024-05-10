package src.com.java.logic;

import javax.swing.undo.AbstractUndoableEdit;


/**
 * The MoveEdit class represents an edit that can be done to a move.
 * It contains the move that will be edited.
 */
public class MoveEdit extends AbstractUndoableEdit {
    /**
     * The move to be edited.
     */
    private final Move m;

    /**
     * Creates a new MoveEdit object with the given move.
     * @param m the move to be edited
     */
    public MoveEdit(Move m){
        this.m = m;
    }

    /**
     * Undoes the move.
     */
    public void undo() {
        super.undo();
        m.undo();
    }

    /**
     * Redoes the move.
     */
    public void redo() {
        super.redo();
        m.doit();
    }
}
