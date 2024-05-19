package com.java.gui.gamePlay;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.utils.Debug;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * class for the gui representation of the playable cells in the game.
 */
public class Cell extends JPanel{
    /**
     * Instance that manages the game.
     */
    private final InstanceManager instanceManager;
    /**
     * Coordinates of the cell in the matrix.
     */
    private final IntCoordinates coords;
    /**
     * Border that imitates shadow from the top.
     */
    private final Border topShadow = BorderFactory.createMatteBorder(5, 0, 0, 0, Colours.SHADOW);
    /**
     * Border that imitates shadow from the left.
     */
    private final Border leftShadow = BorderFactory.createMatteBorder(0, 5, 0, 0, Colours.SHADOW);
    /**
     * Border that imitates shadow from the top left.
     */
    private final Border topLeftShadow = BorderFactory.createMatteBorder(5, 5, 0, 0, Colours.SHADOW);

    /**
     * Constructor for the Cell class. It initializes the panel and sets the background color.
     * @param instanceManager instance that manages the game
     * @param x x coordinate of the cell in the matrix
     * @param y y coordinate of the cell in the matrix
     */
    public Cell(InstanceManager instanceManager , int x, int y){
        super();
        this.instanceManager = instanceManager;
        GuiConfig guiConfig = instanceManager.getGuiConfig();
        // setting the preferred size of the cell
        this.setMinimumSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        this.setPreferredSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        this.setMaximumSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        // pixel coordinates are relative to the top left of the arena
        this.coords = new IntCoordinates(x, y);
        // inits the background of the cell
        updatePlayableCell();

        if (Debug.isDebugging()){
            this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        }
    }

    /**
     * Method that update the coordinates and the size of the cell.
     */
    public void updateCell(){
        this.removeAll();
        GuiConfig guiConfig = instanceManager.getGuiConfig();

        this.setSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        this.setMinimumSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        this.setPreferredSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        this.setMaximumSize(new Dimension(guiConfig.CELL_SIZE, guiConfig.CELL_SIZE));
        if (Debug.isDebugging()){
            this.add(new JLabel(this.coords.toString())).setFont(new Font("Arial", Font.PLAIN, 7));
        }
        updatePlayableCell();
    }

    /**
     * Updates the background of the cell to the correct color given by maze state.
     */
    public void updatePlayableCell(){
        boolean[][] matrix = instanceManager.getArena().getMatrix();
        if (matrix[this.coords.getY()][this.coords.getX()]){
            this.setBackground(Colours.CELL_COLOUR);
            applyShadow();
        }else{
            this.setBackground(Colours.BACKGROUND);
        }
    }

    /**
     * applies a shadow to the cell if there is not a playable cell to the top or to the left of the cell.
     * this helps to create the illusion of light coming from the top left corner.
     */
    public void applyShadow(){
        // get the coordinates of the cell in the matrix
        int map_x = this.coords.getX();
        int map_y = this.coords.getY();
        // get the matrix of the arena
        boolean[][] matrix = instanceManager.getArena().getMatrix();
        if(map_x == 0 && map_y == 0){
            // If the cell is in the top left corner of the matrix
            this.setBorder(topLeftShadow);
        }else if(map_x == 0){
            // If the cell is in the leftmost column of the matrix
            if(!matrix[map_y-1][map_x]){
                // If the cell above is not a playable cell
                this.setBorder(topLeftShadow);
            }else{
                // If the cell above is a playable cell
                this.setBorder(leftShadow);
            }
        }else if (map_y == 0){
            // If the cell is in the top row of the matrix
            if(matrix[map_y][map_x-1]){
                // If the cell to the left is a playable cell
                this.setBorder(topShadow);
            }else{
                // If the cell to the left is not a playable cell
                this.setBorder(topLeftShadow);
            }
        }else{
            // If the cell is not in the top row or leftmost column of the matrix (x > 0 and y > 0).
            if (!matrix[map_y-1][map_x] && !matrix[map_y][map_x-1]) {
                // If the cell above and to the left are not playable cells
                this.setBorder(topLeftShadow);
            }else if(!matrix[map_y-1][map_x]){ // we know here that either [y-1][x] is true or [y][x-1] is true
                // If the cell above is not a playable cell
                this.setBorder(topShadow);
            }else if(!matrix[map_y][map_x-1]){
                // If the cell to the left is not a playable cell
                this.setBorder(leftShadow);
            }
        }
    }
}
