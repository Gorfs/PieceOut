package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;
import src.com.java.piece.decorator.Direction;
import src.com.java.piece.decorator.Translation;

public class Move {
    private final Decorator piece;
    private Direction dir = Direction.NORTH;

    public Move(Decorator piece) {
        this.piece = piece;
        if (this.piece instanceof Translation) {
            this.dir = ((Translation) piece).getDir();
        }
    }

    public void doit(){
        piece.personalAction();
    }

    public void undo(){
        if (piece instanceof Translation) {
            ((Translation) piece).setDir(dir);
        }
        piece.personalReverseAction();
    }
}
