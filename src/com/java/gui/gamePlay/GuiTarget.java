package com.java.gui.gamePlay;

import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.piece.Target;

import javax.swing.*;
import java.awt.*;


/**
 * This class is responsible for painting the target in the game.
 */
public class GuiTarget extends JPanel {
    /**
     * Instance manager of the game.
     */
    private final InstanceManager instanceManager;
    /**
     * Target to be painted.
     */
    private final Target target;

    /**
     * Constructor of the GuiTarget class.
     * @param iManager Instance manager of the game
     * @param target Target to be painted
     */
    public GuiTarget(InstanceManager iManager, Target target) {
        this.instanceManager = iManager;
        this.target = target;
    }

    @Override
    public void paintComponent(Graphics g) {
        // get the graphics object
        Graphics2D g2d = (Graphics2D) g;
        GuiConfig guiConfig = this.instanceManager.getGuiConfig();

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
                    cellCoords = new IntCoordinates(target.getDest().getX() + x, target.getDest().getY() + y);
                    cellCoords = this.instanceManager.getPixelCoordinates(cellCoords);
                    g2d.fillRect(cellCoords.getX(), cellCoords.getY(), guiConfig.CELL_SIZE, guiConfig.CELL_SIZE);
                }
            }
        }
    }
}
