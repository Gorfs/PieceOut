package src.logic;

import java.util.ArrayList;

import src.config.GuiConfig;
import src.gui.GUIManager;
import src.piece.decorator.*;

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
