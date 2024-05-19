package com.java.gui.gamePlay;

import com.java.config.Colours;
import com.java.gui.InstanceManager;

import javax.swing.*;
import java.awt.*;


/**
 * class that represents the panel that displays the 7x7 grid of cells that the game is played on.
 */
public class ArenaPanel extends JPanel {
    /**
     * Instance that manages the game.
     */
    private final InstanceManager instanceManager;

    /**
     * Constructor for the ArenaPanel class. It initializes the panel and fills it with cells.
     * @param instanceManager instance that manages the game
     */
    public ArenaPanel(InstanceManager instanceManager) {
        super();
        // setting the background color
        this.setBackground(Colours.BACKGROUND_GREYED);
        // initializing the instance manager
        this.instanceManager = instanceManager;
        int map_size = instanceManager.getArena().getMatrix().length;

        this.setLayout(new GridLayout(map_size, map_size));
        this.setAlignmentY(CENTER_ALIGNMENT);

        // filling the map with cells
        for (int i = 0; i < map_size; i++) {
            for (int j = 0; j < map_size; j++) {
                this.add(new Cell(instanceManager, j, i));
            }
        }
    }

    /**
     * @return the 2d array of cells that are displayed on the map
     */
    public Cell[][] getCells() {
        // returns a 2d array of all the gui.gameplay.cell components drawn on the map
        boolean[][] matrix = instanceManager.getArena().getMatrix();
        int width = matrix.length;
        int height = matrix[0].length;
        int x = 0;
        int y = 0;
        Component[] component = this.getComponents();
        Cell[][] result = new Cell[height][width];

        for (Component value : component) {
            result[y][x] = (Cell) value;
            x++;
            if (x == width) {
                x = 0;
                y++;
            }
        }
        return result;
    }
}
