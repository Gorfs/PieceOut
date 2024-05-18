package com.java.gui.gamePlay;

import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.piece.Target;
import com.java.utils.Debug;

import javax.swing.*;
import java.awt.*;

public class GuiTarget extends JPanel {
    private final InstanceManager instanceManager;
    private final Target target;
    private final Color color;

    public GuiTarget(InstanceManager instanceManager, Target target) {
        this.instanceManager = instanceManager;
        this.target = target;
        // make the color less opaque
        Color color = target.getPiece().getLast().getColor();
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);
    }

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
        GuiConfig guiConfig = this.instanceManager.getGuiConfig();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        boolean[][] shape = target.getShape();
        IntCoordinates cellCoords;

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    cellCoords = getPixelCoordinates( new IntCoordinates(target.getDest().getX() + x, target.getDest().getY() + y));
                    g2d.fillRect(cellCoords.getX(), cellCoords.getY(), guiConfig.CELL_SIZE, guiConfig.CELL_SIZE);
                }
            }
        }

    }
}
