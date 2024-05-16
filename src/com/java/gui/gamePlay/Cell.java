package src.com.java.gui.gamePlay;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * class for the gui representation of the playable cells in the game.
 */
public class Cell extends JPanel{

    /**
     * Instance that manages the game
     */
    private final InstanceManager instanceManager;

    // map_x and map_y are the coordinates of the cell in the matrix
    private final int map_x;
    private final int map_y;


    // separate border for shadow/3d effect
    private final Border topShadow = BorderFactory.createMatteBorder(5, 0, 0, 0, Colours.SHADOW);
    private final Border leftShadow = BorderFactory.createMatteBorder(0, 5, 0, 0, Colours.SHADOW);
    private final Border topLeftShadow = BorderFactory.createMatteBorder(5, 5, 0, 0, Colours.SHADOW);

    /**
     * Constructor for the Cell class
     * @param instanceManager instance that manages the game
     * @param x x coordinate of the cell in the matrix
     * @param y y coordinate of the cell in the matrix
     */
    public Cell(InstanceManager instanceManager , int x, int y){
        super();
        this.instanceManager = instanceManager;
        this.setMinimumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setPreferredSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setMaximumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));

        // pixel coordinates are relative to the top left of the arena
        this.map_x = x;
        this.map_y = y;

        // inits the background of the cell
        updatePlayableCell();
    }

    /**
     * Method that update the coordinates and the size of the cell.
     */
    public void updateCell(){
        this.setSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setMinimumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setPreferredSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setMaximumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        updatePlayableCell();
    }

    /**
     * updates the background of the cell to the correct color given by maze state
     */
    public void updatePlayableCell(){
        boolean[][] matrix = instanceManager.arena.getMatrix();
        if (matrix[map_y][map_x]){
            this.setBackground(Colours.CELL_COLOUR);
            applyShadow();
                        
        }else{
            this.setBackground(Colours.BACKGROUND);
        }
    }

    /**
     * applies a shadow to the cell if there is not a playable cell to the top or to the
     * left of the cell.
     * this helps to create the illusion of light coming from the top left corner.
     */
    public void applyShadow(){
        boolean[][] matrix = instanceManager.arena.getMatrix();
        if(map_x == 0 && map_y == 0){
            this.setBorder(topLeftShadow);
        }else if(map_x == 0){
            if(!matrix[map_y-1][map_x]){
                this.setBorder(topShadow);
            }else{
                this.setBorder(topLeftShadow);
            }
        }else if (map_y == 0){
            if(matrix[map_y][map_x-1]){
                this.setBorder(leftShadow);
            }else{
                this.setBorder(topLeftShadow);
            }
            
        }else{
            if(!matrix[map_y-1][map_x] && !matrix[map_y][map_x-1]){
                this.setBorder(topLeftShadow);
            }else if(!matrix[map_y-1][map_x]){
                this.setBorder(topShadow);
            }else if(!matrix[map_y][map_x-1]){
                this.setBorder(leftShadow);
            }
        }
    }
}
