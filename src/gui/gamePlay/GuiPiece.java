package src.gui.gamePlay;

import src.geometry.IntCoordinates;
import src.config.GuiConfig;
import src.gui.GUIManager;
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
            return cellsCoords[cellCoords.getY()][cellCoords.getX()];
        }catch( Exception e){
            Debug.out("Error in getPixelCoordinates : " + e.getMessage());
            return new IntCoordinates(-100,0);
        }
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

        // using polygon to paint shape of the nextPiece
        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = this.piece.getLast().getOffset();
        IntCoordinates destOffSet = new IntCoordinates(0, 0);
        IntCoordinates destCoords;
        if (this.piece.getLast().getTarget() != null) {
            destOffSet = this.piece.getLast().getDestOffset();
        }
        IntCoordinates cellCoords;

        for (int i = 0 ; i < shapeMatrix.length; i++){ // height
            for(int j = 0 ; j < shapeMatrix[0].length; j++){ // width
                if (shapeMatrix[i][j]){
                    try{
                        cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.getX(), i + offSet.getY()));
                        g2d.setColor(this.colour);
                        // Debug.out("coords are: " + cellCoords.toString() +  " getX : " + j + " getY : " + i + "nextPiece positions : " + piece.getLast().getPos().toString() + " offset : " + offSet.toString());
                        g2d.fillRect(cellCoords.getX(), cellCoords.getY() + GUIManager.getInstance().getArenaPanelY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                        
                        g2d.setColor(new Color(255, 0, 0, 75));
                        if (this.piece.getLast().getTarget() != null) {
                            destCoords = getPixelCoordinates(new IntCoordinates(j + destOffSet.getX(), i + destOffSet.getY()));
                            g2d.fillRect(destCoords.getX(), destCoords.getY() + GUIManager.getInstance().getArenaPanelY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                        }
                        // paint the icon of the action that you can do with this part of the Piece
                        String action = piece.getActionType(j, i);
                        try{
                            if (action != ""){
                                img = ImageIO.read(new File("resources/icons/" + action + ".png"));
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                        if (img != null){
                            int imageWidth = (int) (img.getWidth() * imgScale);
                            int imageHeight = (int) (img.getHeight() * imgScale);
                            int rectCenterX = cellCoords.getX() + (GuiConfig.CELL_SIZE / 2);
                            int rectCenterY = cellCoords.getY() + (GuiConfig.CELL_SIZE / 2) + GUIManager.getInstance().getArenaPanelY();
                            g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2, imageWidth, imageHeight, null);
                            img = null;
                        }
                    }catch(Exception e){
                        Debug.out("Error in paintComponent : " + e.getMessage());
                    }
                    
                   }
                }
            }


        }
    }
    

