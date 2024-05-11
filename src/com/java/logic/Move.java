package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;
import javax.swing.undo.AbstractUndoableEdit;

public class Move extends AbstractUndoableEdit {
    private final Decorator piece;


    public Move(Decorator piece) {
        this.piece = piece;
    }

    public void redo(){
        super.redo();
        piece.personalAction();
    }

    public void undo(){
        super.undo();
        piece.personalReverseAction();
    }
}
