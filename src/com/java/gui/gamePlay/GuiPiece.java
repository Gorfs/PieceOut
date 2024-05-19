package com.java.gui.gamePlay;

import com.java.config.GuiConfig;
import com.java.config.GuiPieceImages;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Translation;
import com.java.utils.Debug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class is responsible for painting the pieces in the game
 */
public class GuiPiece extends JPanel {
    // ! assumes that the nextPiece is in a valid place in the arena
    private final InstanceManager instanceManager;
    private final Decorator piece;
    private final Color colour;
    private ImageIcon shapeIcon;
    // private ImageIcon[] iconList = new ImageIcon[4];


    public GuiPiece(InstanceManager instanceManager, Decorator piece) {
        this.instanceManager = instanceManager;
        this.piece = piece;
        this.colour = piece.getPiece().getColor();
        shapeIcon = switch(piece.getPiece().type){
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
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        GuiConfig guiConfig = this.instanceManager.getGuiConfig();
        BufferedImage img = null;
        double imgScale = 0.05;

        boolean[][] shapeMatrix = this.piece.getShape();
        System.out.println(shapeMatrix[0][0]);
        IntCoordinates offSet = this.piece.getPiece().getOffset();
        IntCoordinates cellCoords;
        IntCoordinates pieceCoords;
        // determine the type of image to use

        if (shapeIcon != null) {
            // preloading all the rotated icon shapes
            for( int i = 0 ; i < piece.getPiece().rotations; i++){
                // this should only execute a maximum of 3 times
                shapeIcon = GuiPieceImages.rotateImage(shapeIcon);
            }
            if(piece.getPiece().xFlipped){
                shapeIcon = GuiPieceImages.flipImage(shapeIcon, true);
            }
            if(piece.getPiece().yFlipped){
                shapeIcon = GuiPieceImages.flipImage(shapeIcon, false);
            }
            pieceCoords = this.instanceManager.getPixelCoordinates(new IntCoordinates(offSet.getX() ,offSet.getY()));
            GuiPieceImages.changeColour(shapeIcon, this.colour);
            int imageWidth = shapeIcon.getImage().getWidth(null) / 120 * guiConfig.CELL_SIZE;
            int imageHeight =shapeIcon.getImage().getHeight(null) /120 * guiConfig.CELL_SIZE;
            g2d.setColor(this.colour);
            g2d.drawImage(shapeIcon.getImage(), pieceCoords.getX(), pieceCoords.getY(), imageWidth, imageHeight, null);


            // Debugging
            g2d.setColor(Color.black);
            g2d.drawString("x:" + pieceCoords.getX() + " y: " + pieceCoords.getY(),pieceCoords.getX() ,pieceCoords.getY() );
            int circleRadius = 5;
            g2d.setColor(Color.red);
            g2d.drawOval(pieceCoords.getX(), pieceCoords.getY(), circleRadius * 2, circleRadius * 2);
            g2d.drawRect(pieceCoords.getX(), pieceCoords.getY(), imageWidth, imageHeight);
        }

        for (int i = 0; i < shapeMatrix.length; i++) { // height
            for (int j = 0; j < shapeMatrix[0].length; j++) { // width
                if (shapeMatrix[i][j]) {
                    try {
                        cellCoords = new IntCoordinates(j + offSet.getX(), i + offSet.getY());
                        cellCoords = this.instanceManager.getPixelCoordinates(cellCoords);
                        g2d.setColor(this.colour);
                        // load the icon for decorator into memory
                        String action = piece.getActionType(j, i);
                        try{
                            if (!Objects.equals(action, "") && !Objects.equals(action, "Translation")){
                                img = ImageIO.read(new File(GuiConfig.resPath + "icons/" + action + ".png"));
                            }
                        } catch (Exception e) {
                            System.out.println("Error in paintComponent : " + e.getMessage());
                        }

                        // paint the icon of the decorator
                        if (Objects.equals(action, "Translation")) {
                            paintDirectional(g2d, cellCoords, (Translation) piece, i, j);
                        } else if (img != null) {
                            int imageWidth = (int) (img.getWidth() * imgScale);
                            int imageHeight = (int) (img.getHeight() * imgScale);
                            int rectCenterX = cellCoords.getX() + (guiConfig.CELL_SIZE / 2);
                            int rectCenterY = cellCoords.getY() + (guiConfig.CELL_SIZE / 2);
                            g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2,
                                    imageWidth, imageHeight, null);
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
        GuiConfig guiConfig = this.instanceManager.getGuiConfig();
        int x = cellCoords.getX();
        int y = cellCoords.getY();
        int cellSize = guiConfig.CELL_SIZE;

        Image[] images = new Image[4];
        try {
            images[0] = ImageIO.read(new File(GuiConfig.resPath + "icons/up.png"));
            images[1] = ImageIO.read(new File(GuiConfig.resPath + "icons/right.png"));
            images[2] = ImageIO.read(new File(GuiConfig.resPath + "icons/down.png"));
            images[3] = ImageIO.read(new File(GuiConfig.resPath + "icons/left.png"));
        } catch (IOException e) {
            System.out.println("Error in paintDirectional : " + e.getMessage());
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
                    case 0 -> g2d.drawImage(images[0], x, y, cellSize, cellSize, null);
                    case 1 -> g2d.drawImage(images[1], x, y, cellSize, cellSize, null);
                    case 2 -> g2d.drawImage(images[2], x, y, cellSize, cellSize, null);
                    case 3 -> g2d.drawImage(images[3], x, y, cellSize, cellSize, null);
                }
            }
        }
    }
}
