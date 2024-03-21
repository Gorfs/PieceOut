package src.logic;

import java.util.ArrayList;

import src.piece.decorator.*;

import javax.swing.undo.UndoableEditSupport;

public class PieceLogic {

    private static ArrayList<Decorator> pieces = new ArrayList<Decorator>();

    
    /** 
     * @return ArrayList<PieceBrute>
     */
    public static ArrayList<Decorator> getPieces(){
        return pieces;
    }

    
    /** 
     * @param p
     */
    public static void addPiece(Decorator p){
        pieces.add(p);
    }

    public static void clearPieces(){
        pieces.clear();
    }

    // public static boolean piecesInPlace(){
    //     for (Pieces p : pieces) {
    //         if (p.getPos() != p.getDestination()) return false;
    //     }
    //     return true;
    // }







    
}
