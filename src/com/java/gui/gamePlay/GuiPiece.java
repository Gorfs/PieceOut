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
 * This class is responsible for painting the pieces in the game.
 */
public class GuiPiece extends JPanel {
    /**
     * Instance manager that manages the game.
     */
    private final InstanceManager instanceManager;
    /**
     * !Assume that the piece is a decorator because a piece that cannot move is similar to a wall.
     * Piece that we want to paint.
     */
    private final Decorator piece;
    /**
     * Color of the piece.
     */
    private final Color color;
    /**
     * Icon of the piece.
     */
    private ImageIcon shapeIcon;

    /**
     * Constructor for the GuiPiece class.
     * It initializes the attributes and sets the icon of the piece.
     * @param instanceManager instanceManager that will manage this piece.
     * @param piece piece that we want to paint
     */
    public GuiPiece(InstanceManager instanceManager, Decorator piece) {
        this.instanceManager = instanceManager;
        this.piece = piece;
        this.color = piece.getPiece().getColor();
        shapeIcon = switch(piece.getPiece().getType()){
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
        // Get the 2D graphics object
        Graphics2D g2d = (Graphics2D) g;
        // Get the gui configuration for the size of the cells
        GuiConfig guiConfig = this.instanceManager.getGuiConfig();
        // Initialize the image
        BufferedImage img = null;
        double imgScale = 0.05;
        // Get the offset of the piece for the position in the window
        IntCoordinates offSet = this.piece.getPiece().getOffset();
        // Initialize the coordinates of the cell and the piece
        IntCoordinates cellCoords;
        IntCoordinates pieceCoords;
        // determine the type of image to use
        if (shapeIcon != null) {
            // preloading all the rotated icon shapes
            for(int i = 0; i < piece.getPiece().getRotations(); i++){
                // this should only execute a maximum of 3 times
                shapeIcon = GuiPieceImages.rotateImage(shapeIcon);
            }
            // flip the image if necessary in the x and y direction
            if(piece.getPiece().isxFlipped()) shapeIcon = GuiPieceImages.flipImage(shapeIcon, true);
            if(piece.getPiece().isyFlipped()) shapeIcon = GuiPieceImages.flipImage(shapeIcon, false);
            // get the pixel coordinates of the piece
            pieceCoords = this.instanceManager.getPixelCoordinates(new IntCoordinates(offSet.getX() ,offSet.getY()));
            // change the color of the image
            GuiPieceImages.changeColour(shapeIcon, this.color);
            // scale the image to the correct size
            int imageWidth = shapeIcon.getImage().getWidth(null) / 120 * guiConfig.CELL_SIZE;
            int imageHeight = shapeIcon.getImage().getHeight(null) /120 * guiConfig.CELL_SIZE;
            g2d.setColor(this.color);
            g2d.drawImage(shapeIcon.getImage(), pieceCoords.getX(), pieceCoords.getY(), imageWidth, imageHeight,
                    null);

            // Debugging purposes
            if (Debug.isDebugging()) {
                // Write the coordinates of the piece on the piece
                g2d.setColor(Color.black);
                g2d.drawString("x:" + pieceCoords.getX() + " y: " + pieceCoords.getY(), pieceCoords.getX(), pieceCoords.getY());
                // Draw a red circle on the pivot point of the piece
                int circleRadius = 5;
                g2d.setColor(Color.red);
                g2d.drawOval(pieceCoords.getX(), pieceCoords.getY(), circleRadius * 2, circleRadius * 2);
                // Draw a rectangle around the piece
                g2d.drawRect(pieceCoords.getX(), pieceCoords.getY(), imageWidth, imageHeight);
            }
        }
        // Paint the decorators of the piece
        for (IntCoordinates pos : piece.getPiece().getPosShape()) {
            // Get the coordinates of the cell
            int i = pos.getY();
            int j = pos.getX();
            // Get the pixel coordinates of the cell
            cellCoords = new IntCoordinates(j + offSet.getX(), i + offSet.getY());
            cellCoords = this.instanceManager.getPixelCoordinates(cellCoords);
            g2d.setColor(this.color);
            // load the icon for decorator into memory
            String action = piece.getActionType(j, i);
            try{
                if (!Objects.equals(action, "") && !Objects.equals(action, "Translation")){
                    img = ImageIO.read(new File(GuiConfig.resPath + "icons/" + action + ".png"));
                }
            } catch (Exception e) {
                System.out.println("File not found : " + GuiConfig.resPath + "icons/" + action + ".png");
                System.out.println("Error in paintComponent : " + e.getMessage());
            }
            // paint the icon of the decorator
            if (Objects.equals(action, "Translation")) {
                // paint the directional arrows of the translation
                paintDirectional(g2d, cellCoords, (Translation) piece, i, j);
            } else if (img != null) {
                // Paint the image of the other decorator
                int imageWidth = (int) (img.getWidth() * imgScale);
                int imageHeight = (int) (img.getHeight() * imgScale);
                int rectCenterX = cellCoords.getX() + (guiConfig.CELL_SIZE / 2);
                int rectCenterY = cellCoords.getY() + (guiConfig.CELL_SIZE / 2);
                g2d.drawImage(img, rectCenterX - imageWidth / 2, rectCenterY - imageHeight / 2,
                        imageWidth, imageHeight, null);
                img = null;
            }
        }
    }

    /**
     * Method that paints the directional arrows of a Translation decorator as rectangles.
     * @param g2d 2D Graphics object
     * @param cellCoords Coordinates of the cell in the game
     * @param piece Translation we want to draw
     */
    private void paintDirectional(Graphics2D g2d, IntCoordinates cellCoords, Translation piece, int a, int b) {
        // Get the gui configuration for the size of the cells
        GuiConfig guiConfig = this.instanceManager.getGuiConfig();
        int cellSize = guiConfig.CELL_SIZE;
        int x = cellCoords.getX();
        int y = cellCoords.getY();
        // Load the images of the directional arrows
        Image[] images = new Image[4];
        try {
            images[0] = ImageIO.read(new File(GuiConfig.resPath + "icons/up.png"));
            images[1] = ImageIO.read(new File(GuiConfig.resPath + "icons/right.png"));
            images[2] = ImageIO.read(new File(GuiConfig.resPath + "icons/down.png"));
            images[3] = ImageIO.read(new File(GuiConfig.resPath + "icons/left.png"));
        } catch (IOException e) {
            System.out.println("File not found : " + GuiConfig.resPath + "icons/" + "[up/right/down/left].png");
            System.out.println("Error in paintDirectional : " + e.getMessage());
            return;
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
        // Draw the directional arrows
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
