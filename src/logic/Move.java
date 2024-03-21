package src.logic;

import src.piece.decorator.Decorator;

public class Move {
    private final Decorator piece;

    public Move(Decorator piece) {
        this.piece = piece;
    }

    public void doit(){
        piece.personalAction();
    }

    public void undo(){
        piece.personalReverseAction();
    }
}
