package src.tests.java.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.java.geometry.IntCoordinates;
import com.java.logic.PieceLogic;
import com.java.piece.decorator.*;
import com.java.piece.*;

public class TestPieceLogic {


    @Test
    public void testAddPiece() {
        PieceLogic pieceLogic = new PieceLogic();
        PieceBrute piece = new ShapeFactory(); 
        Decorator dec = new Rotation(piece, new IntCoordinates(0, 0) );
        pieceLogic.addPiece(dec);
        assertTrue(pieceLogic.getPieces().contains(dec));
    }

    @Test
    public void testClearPieces() {
        PieceLogic pieceLogic = new PieceLogic();
        PieceBrute piece = new ShapeFactory(); 
        Decorator dec = new Rotation(piece, new IntCoordinates(0, 0) );
        pieceLogic.addPiece(dec);
        assertEquals(1, pieceLogic.getPieces().size());
        pieceLogic.clearPieces();
        assertEquals(0, pieceLogic.getPieces().size());
    }

}
