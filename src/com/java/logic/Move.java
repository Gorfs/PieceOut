package src.com.java.logic;

import src.com.java.piece.decorator.Decorator;

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
