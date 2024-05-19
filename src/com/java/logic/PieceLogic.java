package com.java.logic;

import com.java.geometry.IntCoordinates;
import com.java.piece.Target;
import com.java.piece.decorator.Decorator;

import java.util.ArrayList;


/**
 * The PieceLogic class represents the logic of the game.
 * It contains the pieces and targets of the game.
 */
public class PieceLogic {
    /**
     * Arrays of Decorator that represents the pieces of the game.
     */
    private final ArrayList<Decorator> pieces;
    /**
     * Arrays of Target that represents the targets of the game.
     */
    private final ArrayList<Target> targets;

    /**
     * Creates a new PieceLogic object.
     * It initializes the pieces and targets arrays.
     */
    public PieceLogic() {
        this.pieces = new ArrayList<>();
        this.targets = new ArrayList<>();
    }

    /**
     * Checks if the game is won.
     * @return boolean true if the game is won, false otherwise
     */
    public boolean checkWin(){
        for (Target target : targets) {
            // check position
            IntCoordinates dest = target.getDest();
            IntCoordinates piecePos = target.getPiece().getPiece().getOffset();

            if (!dest.equals(piecePos)) return false;

            // check shape
            boolean[][] targetShape = target.getShape();
            boolean[][] pieceShape = target.getPiece().getPiece().getShape();

            if (targetShape.length != pieceShape.length) return false;
            if (targetShape[0].length != pieceShape[0].length) return false;

            for (int i = 0; i < targetShape.length; i++) {
                for (int j = 0; j < targetShape[0].length; j++) {
                    if (targetShape[i][j] != pieceShape[i][j]) return false;
                }
            }
        }
        return true;
    }

    /**
     * Add a piece to the game.
     * @param p the piece to add
     */
    public void addPiece(Decorator p){
        pieces.add(p);
    }

    /**
     * Remove all the pieces from the game.
     */
    public void clearPieces(){
        pieces.clear();
    }

    /**
     * Add a target to the game.
     * @param t the target to add
     */
    public void addTarget(Target t){
        targets.add(t);
    }

    /**
     * Remove all the targets from the game.
     */
    public void clearTargets() {
        targets.clear();
    }

    /**
     * @return ArrayList<PieceBrute> that represents the pieces of the game
     */
    public ArrayList<Decorator> getPieces(){
        return pieces;
    }

    /**
     * @return ArrayList<Target> that represents the targets of the game
     */
    public ArrayList<Target> getTargets() {
        return targets;
    }
}
