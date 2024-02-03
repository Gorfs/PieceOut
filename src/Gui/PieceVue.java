package src.Gui;

import java.awt.*;

public class PieceVue {
   boolean[][] pieceShape; // the shape of the piece is reperesented as:
   // 0 0 1 0
   // 0 0 1 0
   // 0 1 1 0
   // 0 0 0 0
   
   public PieceVue(boolean[][] pieceShape, Color color) {
      this.pieceShape = pieceShape;
   }
}
