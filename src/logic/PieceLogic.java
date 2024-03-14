package src.logic;

import java.util.ArrayList;

import src.piece.PieceBrute;

public class PieceLogic {

    private static ArrayList<PieceBrute> pieces = new ArrayList<PieceBrute>();

    
    /** 
     * @return ArrayList<PieceBrute>
     */
    public static ArrayList<PieceBrute> getPieces(){
        return pieces;
    }

    
    /** 
     * @param p
     */
    public static void addPiece(PieceBrute p){
        pieces.add(p);
    }

    public static void clearPieces(){
        pieces.clear();
    }







    
}
