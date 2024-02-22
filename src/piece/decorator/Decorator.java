package src.piece.decorator;

import src.geometry.IntCoordinates;
import src.piece.Pieces;

public abstract class Decorator extends Pieces {

    // piece on which we add the decorator.
    protected Pieces piece;
    protected int a, b; // l'endroit sur la pièce où se situe la décoration
    Decorator (Pieces p, int x, int y){piece=p; a=x; b=y;}
    // TODO: We may need to remove this later and make specific method for each decorator
    //       because we might need to return different things later.
    public  void action(int x,int y) {
        if (x==a && y==b) personnalAction();
        else piece.action(x,y);
    }
    public void abstract personnalAction();
    /**
     * Method that returns the piece it decorates.
     * @return Returns the piece it decorates.
     */
    public Pieces getPiece() {
        return piece;
    }

    public abstract boolean[][] getShape();

    public abstract IntCoordinates getPivotPoint();
}
