package com.java.gui.gamePlay;

import javax.swing.*;
import java.awt.*;

import com.java.controllers.PlayerController;
import com.java.gui.InstanceManager;
import com.java.config.Colours;
import com.java.config.*;
import com.java.utils.Debug;
import com.java.geometry.IntCoordinates;
import com.java.logic.Arena;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that
     * the game is played on
     */

    // the conainer panel that holds all the cells
    private final InstanceManager instanceManager;

    public ArenaPanel(InstanceManager instanceManager) {
        super();
        this.setBackground(Colours.BACKGROUND_GREYED);
        this.instanceManager = instanceManager;
        int map_size = instanceManager.arena.getMatrix().length;

        this.setLayout(new GridLayout(map_size, map_size));
        this.setAlignmentY(CENTER_ALIGNMENT);

        // filling the map with cells
        for (int i = 0; i < map_size; i++) {
            for (int j = 0; j < map_size; j++) {
                this.add(new Cell(instanceManager, j, i));
            }
        }
    }

    public Cell[][] getCells() {
        // returns a 2d array of all the gui.gameplay.cell components drawn on the map
        boolean[][] matrix = instanceManager.arena.getMatrix();
        int width = matrix.length;
        int height = matrix[0].length;
        int x = 0;
        int y = 0;
        Component[] component = this.getComponents();
        Cell[][] result = new Cell[height][width];

        for (int i = 0; i < component.length; i++) {
            result[y][x] = (Cell) component[i];
            x++;
            if (x == width) {
                x = 0;
                y++;
            }
        }
        return result;
    }

    public void updateArena() {
        /*
         * updates the cells to a new matrix state.
         */
        boolean[][] matrix = instanceManager.arena.getMatrix();
        for (int i = 0; i < Math.pow(matrix.length, 2); i++) {
            Cell cell = (Cell) this.getComponent(i);
            cell.updatePlayableCell();
        }
    }

}
