package src.logic;

import javax.swing.undo.AbstractUndoableEdit;

public class MoveEdit extends AbstractUndoableEdit {
    private final Move m;

    public MoveEdit(Move m){
        this.m = m;
        m.doit();
    }

    public void undo() {
        super.undo();
        m.undo();
    }

    public void redo() {
        super.redo();
        m.doit();
    }
}
