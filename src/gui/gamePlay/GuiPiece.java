package src.gui.gamePlay;

import src.geometry.IntCoordinates;
import src.config.GuiConfig;
import src.gui.GUIManager;
import src.gui.UI.TopPanel;
import src.logic.Arena;
import src.piece.PieceBrute;
import src.piece.decorator.Decorator;
import src.utils.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GuiPiece extends JPanel {
    //! assumes that the nextPiece is in a valid place in the arena

    private Color colour = Color.black;
    private boolean[][] shapeMatrix = new boolean[4][];
    private IntCoordinates pos;
    public boolean hasBeenPainted = false;

    private Decorator piece;


    public GuiPiece(Decorator piece){
        this.piece = piece;
        this.colour = piece.getLast().getColor();
        this.shapeMatrix = piece.getShape();

        

    }

    
    /** 
     * @param cellCoords
     * @return IntCoordinates
     */
    private IntCoordinates getPixelCoordinates(IntCoordinates cellCoords){
        IntCoordinates[][] cellsCoords = GUIManager.getInstance().getGamePanel().getArenaPanel().getCellCoordArray();
        try{
            return cellsCoords[cellCoords.y()][cellCoords.x()];
        }catch( Exception e){
            Debug.out("Error in getPixelCoordinates : " + e.getMessage());
            return new IntCoordinates(-100,0);
        }
    }



    
    /** 
     * @return IntCoordinates
     */
    private IntCoordinates getOffset(){
        // returns new int-coordinates with the offset from the nextPiece added

        int pivotPointX = piece.getLast().getFixturePoint().x(); // distance to border of nextPiece matrix
        int pivotPointY = piece.getLast().getFixturePoint().y(); // ''

        int posX = piece.getLast().getPos().x();
        int posY = piece.getLast().getPos().y();

        int xOffset = posX - pivotPointX;
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset); // TODO: Changed piece, getPivotPoint doesn't exist anymore.

    }

    

    
    /** 
     * @param g
     */

    @Override
    public void paintComponent(Graphics g){
        // super.paintComponent(g);
        this.hasBeenPainted = true;
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage img = null;
        double imgScale = 0.05;
        try{
            img = ImageIO.read(new File("resources/icons/rotate.png"));
        } catch (Exception e){
            e.printStackTrace();
        }

        // using polygon to paint shape of the nextPiece
        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = getOffset();
        IntCoordinates cellCoords;

        Debug.out("boolean array is : ");
        Debug.printBooleanArray(shapeMatrix);

        for (int i = 0 ; i < shapeMatrix.length; i++){ // height
            for(int j = 0 ; j < shapeMatrix[0].length; j++){ // width
                if (shapeMatrix[i][j]){
                    Debug.out("shapeMatrix coords are x : " + j + offSet.x() + " y : " + i + offSet.y() + " x1 : " + i + " y1  :" + j );
                    cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.x(), i + offSet.y()));
                    g2d.setColor(piece.getLast().getColor());
                    // Debug.out("coords are : " + cellCoords.toString() +  " x : " + j + " y : " + i + "nextPiece positions : " + piece.getLast().getPos().toString() + " offset : " + offSet.toString());
                    g2d.fillRect(cellCoords.x(), cellCoords.y() + GUIManager.getInstance().getArenaPanelY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                }
                

            }

        }
        if (img != null){
            int imageWidth = (int) (img.getWidth() * imgScale);
            int imageHeight = (int) (img.getHeight() * imgScale);
            cellCoords = getPixelCoordinates(new IntCoordinates(piece.getLast().getFixturePoint().x() + offSet.x(), piece.getLast().getFixturePoint().y() + offSet.y()));
            int rectCenterX = cellCoords.x() + (GuiConfig.CELL_SIZE / 2);
            int rectCenterY = cellCoords.y() + (GuiConfig.CELL_SIZE / 2) + GUIManager.getInstance().getArenaPanelY();
            g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2, imageWidth, imageHeight, null);
            

        }
    }
    
}
