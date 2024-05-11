package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;
import src.com.java.piece.decorator.Direction;
import src.com.java.piece.decorator.Translation;

import javax.swing.undo.AbstractUndoableEdit;

public class Move extends AbstractUndoableEdit {
    private final Decorator piece;
    Direction dir = Direction.NORTH;

    public Move(Decorator piece) {
        this.piece = piece;
        if (this.piece instanceof Translation) {
            this.dir = ((Translation) piece).getDir();
        }
    }

    public void redo(){
        super.redo();
        piece.personalAction();
    }

    public void undo(){
        super.undo();
        if (piece instanceof Translation) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalReverseAction();
    }
}
