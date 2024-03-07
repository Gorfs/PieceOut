package src.logic;

import java.util.ArrayList;

import src.piece.Pieces;

public class PieceLogic {

    private static ArrayList<Pieces> pieces = new ArrayList<Pieces>();

    
    /** 
     * @return ArrayList<Pieces>
     */
    public static ArrayList<Pieces> getPieces(){
        return pieces;
    }

    
    /** 
     * @param p
     */
    public static void addPiece(Pieces p){
        pieces.add(p);
    }

    public static void clearPieces(){
        pieces.clear();
    }

    public static boolean piecesInPlace(){
        for (Pieces p : pieces) {
            if (p.getPos() != p.getDestination()) return false;
        }
        return true;
    }







    
}
