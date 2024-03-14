package src.gui.gamePlay;

import src.geometry.IntCoordinates;
import src.config.GuiConfig;
import src.gui.GUIManager;
import src.gui.UI.TopPanel;
import src.piece.Pieces;
import src.utils.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GuiPiece {
    //! assumes that the piece is in a valid place in the arena

    private Color colour = Color.black;
    private boolean[][] shapeMatrix = new boolean[4][];
    private IntCoordinates pos;

    private Pieces piece;


    public GuiPiece(Pieces piece){
        this.piece = piece;
        this.colour = piece.getColor();
        this.shapeMatrix = piece.getShape();
        

    }

    
    /** 
     * @param cellCoords
     * @return IntCoordinates
     */
    private IntCoordinates getPixelCoordinates(IntCoordinates cellCoords){
        IntCoordinates[][] cellsCoords = GUIManager.getInstance().getGamePanel().getArenaPanel().getCellCoordArray();
        return cellsCoords[cellCoords.y()][cellCoords.x()];
    }



    
    /** 
     * @return IntCoordinates
     */
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

    private IntCoordinates getDestOffset(){
        // returns new int-coordinates with the offset from the piece added

        int pivotPointX = piece.getPivotPoint().x(); // distance to border of piece matrix
        int pivotPointY = piece.getPivotPoint().y(); // ''

        int posX = piece.getDestination().x();
        int posY = piece.getDestination().y();

        int xOffset = posX - pivotPointX; 
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset);
    }

    

    
    /** 
     * @param g
     */
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        // using polygon to paint shape of the piece
        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = getOffset();
        IntCoordinates destOffSet = getDestOffset();
        IntCoordinates cellCoords;
        IntCoordinates destCoords;

        for (int i = 0 ; i < shapeMatrix.length; i++){ // height
            for(int j = 0 ; j < shapeMatrix[0].length; j++){ // width
                if (shapeMatrix[i][j]){
                    cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.x(), i + offSet.y()));
                    g2d.setColor(piece.getColor());
                    Debug.out("coords are : " + cellCoords.toString() +  " x : " + j + " y : " + i + "piece positions : " + piece.getPos().toString() + " offset : " + offSet.toString());
                    g2d.fillRect(cellCoords.x(), cellCoords.y() + 30, GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                    
                    g2d.setColor(new Color(255, 0, 0, 75));
                    destCoords = getPixelCoordinates(new IntCoordinates(j + destOffSet.x(), i + destOffSet.y()));
                    g2d.fillRect(destCoords.x(), destCoords.y() + 30, GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                }
                

            }

        }


    }
    
}
