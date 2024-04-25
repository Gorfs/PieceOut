package src.com.java.gui.gamePlay;

import src.com.java.geometry.IntCoordinates;
import src.com.java.config.GuiConfig;
import src.com.java.piece.decorator.Translation;
import src.com.java.utils.Debug;
import src.com.java.piece.decorator.Decorator;
import src.com.java.gui.InstanceManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import javax.swing.JPanel;

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
        if (this.piece.getLast().getTarget() != null) {
            destOffSet = this.piece.getLast().getDestOffset();
        }
        IntCoordinates cellCoords;

        for (int i = 0; i < shapeMatrix.length; i++) { // height
            for (int j = 0; j < shapeMatrix[0].length; j++) { // width
                if (shapeMatrix[i][j]) {
                    try {
                        cellCoords = getPixelCoordinates(new IntCoordinates(j + offSet.getX(), i + offSet.getY()));
                        g2d.setColor(this.colour);
                        // Debug.out("coords are: " + cellCoords.toString() + " x : " + j + " y : " + i
                        // + "nextPiece positions : " + piece.getLast().getPos().toString() + " offset :
                        // " + offSet.toString());
                        g2d.fillRect(cellCoords.getX(), cellCoords.getY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);

                        g2d.setColor(new Color(255, 0, 0, 75));
                        if (this.piece.getLast().getTarget() != null) {
                            destCoords = getPixelCoordinates(
                                    new IntCoordinates(j + destOffSet.getX(), i + destOffSet.getY()));
                            g2d.fillRect(destCoords.getX(), destCoords.getY(), GuiConfig.CELL_SIZE,
                                    GuiConfig.CELL_SIZE);
                        }

                        // load the icon for decorator into memory
                        String action = piece.getActionType(j, i);
                        try{
                            if (!Objects.equals(action, "")){
                                img = ImageIO.read(new File(GuiConfig.resourcePath + "icons/" + action + ".png"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // paint the icon of the decorator
                        if (Objects.equals(action, "Translation")) {
                            paintDirectional(g2d, cellCoords, (Translation) piece);
                        }
                        else if (img != null) {
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
    private void paintDirectional(Graphics2D g2d, IntCoordinates cellCoords, Translation piece) {
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
