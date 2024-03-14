package src.gui.gamePlay;

import src.geometry.IntCoordinates;
import src.config.GuiConfig;
import src.gui.GUIManager;
import src.piece.PieceBrute;
import src.utils.Debug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GuiPiece {
    //! assumes that the nextPiece is in a valid place in the arena

    private Color colour = Color.black;
    private boolean[][] shapeMatrix = new boolean[4][];
    private IntCoordinates pos;

    private PieceBrute piece;


    public GuiPiece(PieceBrute piece){
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
        // returns new int-coordinates with the offset from the nextPiece added

        int pivotPointX = piece.getFixturePoint().x(); // distance to border of piece matrix
        int pivotPointY = piece.getFixturePoint().y(); // ''

        int posX = piece.getPos().x();
        int posY = piece.getPos().y();

        int xOffset = posX - pivotPointX;
        int yOffset = posY - pivotPointY;

        return new IntCoordinates(xOffset, yOffset); // TODO: Changed piece, getPivotPoint doesn't exist anymore.

    }

    

    
    /** 
     * @param g
     */
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage img = null;
        double imgScale = 0.5;
        try{
            img = ImageIO.read(new File("resources/icons/rotate.png"));
        } catch (Exception e){
            e.printStackTrace();
        }

        // using polygon to paint shape of the nextPiece
        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = getOffset();
        IntCoordinates cellCoords;

        for (int i = 0 ; i < shapeMatrix.length; i++){ // height
            for(int j = 0 ; j < shapeMatrix[0].length; j++){ // width
                if (shapeMatrix[i][j]){
                    Debug.out("shapeMatrix coords are x : " + j + offSet.x() + " y : " + i + offSet.y() + " x1 : " + i + " y1  :" + j );
                    cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.x(), i + offSet.y()));
                    g2d.setColor(piece.getColor());
                    Debug.out("coords are : " + cellCoords.toString() +  " x : " + j + " y : " + i + "piece positions : " + piece.getPos().toString() + " offset : " + offSet.toString());
                    //TODO set the cellCoords.y + to not be a constant but instead be calculated, currently 6.
                    g2d.fillRect(cellCoords.x(), cellCoords.y() +6, ((int) Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE)),((int)Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE)));
                }
                

            }

        }
        if (img != null){
            int imageWidth = ((int) Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE * imgScale));
            int imageHeight = ((int) Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE * imgScale));

            cellCoords = getPixelCoordinates(new IntCoordinates(piece.getFixturePoint().x() + offSet.x(), piece.getFixturePoint().y() + offSet.y()));
            int rectCenterX = cellCoords.x() + ((int) Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE)) / 2;
            int rectCenterY = cellCoords.y() + 6 + ((int)Math.round(GuiConfig.CELL_SIZE * GuiConfig.ARENA_SCALE)) / 2;
            g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2, imageWidth, imageHeight, null);
            

        }
    }
    
}
