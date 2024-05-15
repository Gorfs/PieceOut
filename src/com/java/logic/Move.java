package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;
import src.com.java.piece.decorator.Direction;
import src.com.java.piece.decorator.Translation;

import javax.swing.undo.AbstractUndoableEdit;

public class Move extends AbstractUndoableEdit {
    private final Decorator piece;

    private final Direction dir;

    public Move(Decorator piece) {
        this.piece = piece;
        if (piece instanceof Translation) {
            this.dir = ((Translation) piece).getDir();
        } else {
            this.dir = null;
        }
    }

    public void redo(){
        super.redo();
        if (dir != null) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalAction();
    }

    public void undo(){
        super.undo();
        if (dir != null) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalReverseAction();
    }
}
