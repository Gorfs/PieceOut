package src.gui;

import java.awt.*;

/**
 * Represents a view of a piece in the game.
 */
public class PieceVue {
   boolean[][] pieceShape; // the shape of the piece is reperesented as:
   // 0 0 1 0
   // 0 0 1 0
   // 0 1 1 0
   // 0 0 0 0
   
   /**
    * Constructs a PieceVue object with the given piece shape and color.
    * 
    * @param pieceShape the shape of the piece
    * @param color the color of the piece
    */
   public PieceVue(boolean[][] pieceShape, Color color) {
      this.pieceShape = pieceShape;
   }
}
