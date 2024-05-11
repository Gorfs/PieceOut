package src.com.java.gui.gamePlay;

import src.com.java.config.GuiConfig;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.piece.Target;
import src.com.java.utils.Debug;

import javax.swing.*;
import java.awt.*;

/**
 * This class is responsible for painting the target in the game
 */
public class GuiTarget extends JPanel {
    /**
     * Instance manager of the game
     */
    private final InstanceManager instanceManager;
    /**
     * Target to be painted
     */
    private final Target target;

    /**
     * Constructor of the GuiTarget class
     * @param guiManager Instance manager of the game
     * @param target Target to be painted
     */
    public GuiTarget(InstanceManager guiManager, Target target) {
        this.instanceManager = guiManager;
        this.target = target;
    }

    /**
     * returns the pixel coordinates of the cell relative to the top left of the window
     * @param cellCoords Coordinates of the cell int the game
     * @return IntCoordinates returns the pixel coordinates of the cell relative to the top left of the window
     */
    public IntCoordinates getPixelCoordinates(IntCoordinates cellCoords) {
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
        // get the graphics object
        Graphics2D g2d = (Graphics2D) g;
        // make the color less opaque
        Color color = target.getPiece().getPiece().getColor();
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);
        g2d.setColor(color);

        // get the shape of the target
        boolean[][] shape = target.getShape();
        IntCoordinates cellCoords;

        // draw the target
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    cellCoords = getPixelCoordinates(new IntCoordinates(target.getDest().getX() + x,
                            target.getDest().getY() + y));
                    g2d.fillRect(cellCoords.getX(), cellCoords.getY(), GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE);
                }
            }
        }
    }
}
