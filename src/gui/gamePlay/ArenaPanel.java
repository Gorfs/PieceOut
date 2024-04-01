package src.gui.gamePlay;

import javax.swing.*;
import java.awt.*;

import src.controllers.PlayerController;
import src.gui.InstanceManager;
import src.utils.Debug;
import src.config.*;
import src.geometry.IntCoordinates;
import src.logic.Arena;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that
     * the game is played on
     */

    // the conainer panel that holds all the cells
    private final InstanceManager instanceManager;

    public ArenaPanel(InstanceManager instanceManager) {
        super();
        // this.setBackground(Colours.BACKGROUND);
        this.setBackground(Colours.BACKGROUND_GREYED);
        this.instanceManager = instanceManager;
        int map_size = instanceManager.arena.getMatrix().length;

        this.setLayout(new GridLayout(map_size, map_size));
        this.setAlignmentY(CENTER_ALIGNMENT);
        // this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Add thicker
        // and blue border here

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

    // DEPRECATED

    // public IntCoordinates[][] getCellCoordArray(){
    // // returns an array of all the gui.gameplay.cell components drawn on the map
    // boolean[][] matrix = instanceManager.arena.getMatrix();
    // int width = matrix.length;
    // int height = matrix[0].length;
    // int x = 0;
    // int y = 0;
    // int xOffset = this.getX();
    // int yOffset = this.getY();
    // Component[] component = this.getComponents();
    // IntCoordinates[][] result = new IntCoordinates[height][width];

    // for(int i = 0; i < component.length; i++){
    // Cell cell = (Cell) component[i];
    // result[y][x] = new IntCoordinates(cell.getPixel_x() + xOffset,
    // cell.getPixel_y() + yOffset);
    // // Debug.out(result[y][x].toString());
    // x++;
    // if (x == width){
    // x = 0;
    // y++;
    // }

    // }

    // return result;
    // }

}
