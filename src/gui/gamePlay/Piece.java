package src.gui.gamePlay;

import src.geometry.IntCoordinates;
import src.config.GuiConfig;
import src.gui.GUIManager;
import src.piece.Pieces;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Piece extends JPanel {
    //! assumes that the piece is in a valid place in the arena

    private Color colour = Color.black;
    private boolean[][] shapeMatrix = new boolean[4][];
    private IntCoordinates pos;

    private Pieces piece;


    public Piece(Pieces piece){
        this.piece = piece;
        this.colour = piece.getColor();
        this.shapeMatrix = piece.getShape();

    }


    private IntCoordinates getPixelCoordiantes(IntCoordinates cellCoords){
        Cell[][] cells = GUIManager.getInstance().getGamePanel().getArenaPanel().getCellArray();
        Cell cell = cells[piece.getPos().y()][piece.getPos().x()];
        return new IntCoordinates(cell.getX(), cell.getY());
    }



    private IntCoordinates getOffset(){
        // returns new int-coordinates with the offset from the piece added

        int pivotPointX = piece.getPivotPoint().x(); // distance to border of piece matrix
        int pivotPointY = piece.getPivotPoint().y(); // ''

        int posX = piece.getPos().x();
        int posY = piece.getPos().y();

        int xOffset = posX - pivotPointX; 
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset);

    }

    

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        // using polygon to paint shape of the piece
        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = getOffset();
        IntCoordinates cellCoords;

        for (int i = 0 ; i < shapeMatrix.length; i++){ // height
            for(int j = 0 ; j < shapeMatrix[0].length; j++){ // width
                cellCoords = getPixelCoordiantes(new IntCoordinates(j + offSet.x(), i + offSet.y()));
                
                g.setColor(piece.getColor());
                g.fillRect(cellCoords.x(), cellCoords.y(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                

            }

        }
    }
    
}
