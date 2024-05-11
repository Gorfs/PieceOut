package src.com.java.gui.gamePlay;

import src.com.java.geometry.IntCoordinates;
import src.com.java.config.*;
import src.com.java.piece.decorator.Translation;
import src.com.java.utils.Debug;
import src.com.java.piece.decorator.Decorator;
import src.com.java.gui.InstanceManager;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GuiPiece extends JPanel {
    private final InstanceManager instanceManager;

    private final Color colour;
    public boolean hasBeenPainted = false;

    private final Decorator piece;

    public GuiPiece(InstanceManager instanceManager, Decorator piece) {
        this.instanceManager = instanceManager;
        this.piece = piece;
        this.colour = piece.getLast().getColor();
    }

    /**
     * returns the pixel coordinates of the cell relative to the top left of the
     * WINDOW
     * 
     * @param cellCoords Coordinates of the cell int the game
     * @return IntCoordinates
     */
    private IntCoordinates getPixelCoordinates(IntCoordinates cellCoords) {
        ArenaPanel arenaPanel = this.instanceManager.getGamePanel().getArenaPanel();
        try {
            Cell[][] cells = arenaPanel.getCells();
            Cell cell = cells[cellCoords.getY()][cellCoords.getX()];
            int xOffSet = arenaPanel.getX();
            int yOffSet = instanceManager.getArenaPanelY();
            return new IntCoordinates(cell.getX() + xOffSet, cell.getY() + yOffSet);
        } catch (Exception e) {
            Debug.out("Error in getPixelCoordinates : " + e.getMessage());
            return new IntCoordinates(-100, 0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        this.hasBeenPainted = true;
        Graphics2D g2d = (Graphics2D) g;

        BufferedImage img = null;
        double imgScale = 0.05;

        boolean[][] shapeMatrix = piece.getShape();
        IntCoordinates offSet = this.piece.getLast().getOffset();
        IntCoordinates destOffSet = new IntCoordinates(0, 0);
        IntCoordinates destCoords;
        IntCoordinates cellCoords;

        // determine the type of image to use
        ImageIcon shapeIcon = switch(piece.getLast().type){
            case "OShape" -> GuiPieceImages.OImage;
            case "HoShape" -> GuiPieceImages.HoImage;
            case "IShape" -> GuiPieceImages.IImage;
            case "JShape" -> GuiPieceImages.JImage;
            case "LShape" -> GuiPieceImages.LImage;
            case "SingleShape" -> GuiPieceImages.SingleImage;
            case "TShape" -> GuiPieceImages.TImage;
            case "VeShape" -> GuiPieceImages.VeImage;
            default -> null;
        };

        if (shapeIcon != null) {



            GuiPieceImages.changeColour(shapeIcon, this.colour);
            for ( int i = 0 ; i < piece.getLast().rotations; i++){
                shapeIcon = GuiPieceImages.rotateImage(shapeIcon);
            }
            int imageHeight = (int) (Math.floor(shapeIcon.getImage().getWidth(null) / 120 * GuiConfig.CELL_SIZE));
            int imageWidth = (int) (Math.floor(shapeIcon.getImage().getHeight(null) /120 *  GuiConfig.CELL_SIZE));
            // g.setColor(this.colour);
            g2d.setColor(this.colour);
            Debug.out("WIDTH : " + imageWidth + " HEIGHT : " + imageHeight);
            IntCoordinates PieceCoords = getPixelCoordinates(new IntCoordinates(offSet.getX() ,offSet.getY())); 
            if (piece.rotations % 2 != 0){
                g2d.drawImage(shapeIcon.getImage(), PieceCoords.getX(), PieceCoords.getY(), imageWidth, imageHeight, null);
            }else{
                g2d.drawImage(shapeIcon.getImage(), PieceCoords.getX(), PieceCoords.getY(), imageHeight, imageWidth, null);
            }
        }
        

        for (int i = 0; i < shapeMatrix.length; i++) { // height
            for (int j = 0; j < shapeMatrix[0].length; j++) { // width
                if (shapeMatrix[i][j]) {
                    try {
                        cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.getX(), i + offSet.getY()));
                        g2d.setColor(this.colour);

                        // g2d.fillRect(cellCoords.getX(), cellCoords.getY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);


                        // load the icon for decorator into memory
                        String action = piece.getActionType(j, i);
                        try{
                            if (!Objects.equals(action, "") && !Objects.equals(action, "Translation")){
                                img = ImageIO.read(new File(GuiConfig.resourcePath + "icons/" + action + ".png"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // paint the icon of the decorator
                        if (Objects.equals(action, "Translation")) {
                            paintDirectional(g2d, cellCoords, (Translation) piece, i, j);
                        } else if (img != null) {
                            int imageWidth = (int) (img.getWidth() * imgScale);
                            int imageHeight = (int) (img.getHeight() * imgScale);
                            int rectCenterX = cellCoords.getX() + (GuiConfig.CELL_SIZE / 2);
                            int rectCenterY = cellCoords.getY() + (GuiConfig.CELL_SIZE / 2);
                            g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2, imageWidth,
                                    imageHeight, null);
                            img = null;
                        }
                    } catch (Exception e) {
                        Debug.out("Error in paintComponent : " + e.getMessage());
                    }

                }
            }
        }
    }


    /**
     * Method that paints the directional arrows of a Translation decorator as rectangles
     * @param g2d 2D Graphics object
     * @param cellCoords Coordinates of the cell in the game
     * @param piece Translation we want to draw
     */
    private void paintDirectional(Graphics2D g2d, IntCoordinates cellCoords, Translation piece, int a, int b) {
        int x = cellCoords.getX();
        int y = cellCoords.getY();
        int cellSize = GuiConfig.CELL_SIZE;

        g2d.setColor(Color.WHITE);

        Image[] images = new Image[4];
        try {
            images[0] = ImageIO.read(new File(GuiConfig.resourcePath + "icons/up.png"));
            images[1] = ImageIO.read(new File(GuiConfig.resourcePath + "icons/right.png"));
            images[2] = ImageIO.read(new File(GuiConfig.resourcePath + "icons/down.png"));
            images[3] = ImageIO.read(new File(GuiConfig.resourcePath + "icons/left.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Find the decorator to draw here
        Decorator tmp = piece;
        while (!piece.getActionPoint().equals(new IntCoordinates(b, a))) {
            // If the previous piece is a decorator, we need to find the translation
            if (piece.getPrevPiece() instanceof Decorator) {
                while (tmp.getPrevPiece() != null) {
                    // If the previous piece is a translation, we found the translation
                    if (tmp.getPrevPiece() instanceof Translation) {
                        piece = (Translation) tmp.getPrevPiece();
                        break;
                    }
                    // Check the next decorator
                    tmp = (Decorator) tmp.getPrevPiece();
                }
            } else {
                break;
            }
        }

        for (int i = 0; i < piece.getDirections().length; i++) {
            if (piece.getDirections()[i]) {
                switch (i) {
                    case 0 -> {
                        g2d.drawImage(images[0], x, y, cellSize,cellSize, null);
                    }
                    case 1 -> {
                        g2d.drawImage(images[1], x, y,cellSize,cellSize, null);
                    }
                    case 2 -> {
                        g2d.drawImage(images[2], x, y,cellSize,cellSize, null);
                    }
                    case 3 -> {
                        g2d.drawImage(images[3], x, y,cellSize,cellSize, null);
                    }
                }
            }
        }
    }
}
