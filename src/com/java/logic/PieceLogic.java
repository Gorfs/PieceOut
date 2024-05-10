package src.com.java.logic;

import java.util.ArrayList;

import src.com.java.config.GuiConfig;
import src.com.java.geometry.IntCoordinates;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.*;
import src.com.java.gui.InstanceManager;
import src.com.java.utils.Debug;

import javax.swing.undo.UndoableEditSupport;

public class PieceLogic {
    private final ArrayList<Decorator> pieces;
    private final ArrayList<Target> targets;

    public PieceLogic() {
        this.pieces = new ArrayList<>();
        this.targets = new ArrayList<>();
    }

    /**
     * @return ArrayList<PieceBrute>
     */
    public ArrayList<Decorator> getPieces(){
        return pieces;
    }

    /**
     * @param p
     */
    public void addPiece(Decorator p){
        pieces.add(p);
    }

    public void clearPieces(){
        pieces.clear();
    }

    /**
     * @return ArrayList<Target>
     */
    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void addTarget(Target t){
        targets.add(t);
    }

    public boolean checkWin(){
        for (Target target : targets) {
            // check position
            IntCoordinates dest = target.getDest();
            IntCoordinates piecePos = target.getPiece().getLast().getOffset();
//            Debug.out("dest: " + dest + " piecePos: " + piecePos);
            if (!dest.equals(piecePos)) return false;

            // check shape
            boolean[][] targetShape = target.getShape();
            boolean[][] pieceShape = target.getPiece().getLast().getShape();

//            Debug.printShapePieces(target.getPiece().getLast());
//            Debug.printBooleanArray(targetShape);

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


    public void clearTargets() {
        targets.clear();
    }
}
