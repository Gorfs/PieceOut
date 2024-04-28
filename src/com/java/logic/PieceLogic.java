package src.com.java.logic;

import java.util.ArrayList;

import src.com.java.config.GuiConfig;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.*;
import src.com.java.gui.InstanceManager;

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
}
