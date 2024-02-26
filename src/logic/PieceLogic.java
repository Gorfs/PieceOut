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
    public void addPiece(Pieces p){
        pieces.add(p);
    }

    public void clearPieces(){
        pieces.clear();
    }







    
}
