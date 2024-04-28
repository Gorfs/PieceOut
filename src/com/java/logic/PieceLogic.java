package src.com.java.logic;

import java.util.ArrayList;

import src.com.java.config.GuiConfig;
import src.com.java.piece.decorator.*;
import src.com.java.gui.InstanceManager;

import javax.swing.undo.UndoableEditSupport;

public class PieceLogic {
    private final ArrayList<Decorator> pieces;

    public PieceLogic() {
        this.pieces = new ArrayList<Decorator>();
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
}
