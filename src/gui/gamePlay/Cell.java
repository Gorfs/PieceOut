package src.gui.gamePlay;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;


import src.config.*;
import src.geometry.IntCoordinates;
import src.logic.Arena;

public class Cell extends JPanel{
    /**
     * class for the gui representation of the playable cells in the game.
     */

    private static final double scale = 1;

    
    // pixel_x and pixel_y are the coordinates of the cell in pixels RELATIVE to the CONTAINER (ARENA PANEL)
    private int pixel_x;
    private int pixel_y;

    // map_x and map_y are the coordinates of the cell in the matrix
    private final int map_x;
    private final int map_y;


    // seperate border for shadow/3d effect
    private final Border topShadow = BorderFactory.createMatteBorder(5, 0, 0, 0, Colours.SHADOW);
    private final Border leftShadow = BorderFactory.createMatteBorder(0, 5, 0, 0, Colours.SHADOW);
    private final Border topLeftShadow = BorderFactory.createMatteBorder(5, 5, 0, 0, Colours.SHADOW);


    
    public Cell(int x_coord, int y_coord){
        super();

        // hard-fixing cell size
        this.setMinimumSize(new Dimension((int) (GuiConfig.CELL_SIZE * scale), (int) (GuiConfig.CELL_SIZE * scale)));
        this.setPreferredSize(new Dimension((int) (GuiConfig.CELL_SIZE * scale), (int) (GuiConfig.CELL_SIZE * scale)));
        this.setMaximumSize(new Dimension((int) (GuiConfig.CELL_SIZE * scale), (int) (GuiConfig.CELL_SIZE * scale)));

        this.pixel_x = x_coord * GuiConfig.CELL_SIZE; 
        this.pixel_y = y_coord * GuiConfig.CELL_SIZE;
        this.map_x = x_coord;
        this.map_y = y_coord;


        updatePlayableCell(); // inits the background of the cell
        // this.add(new JLabel("x: " + pixel_x + " y: " + pixel_y));
    }

    public void updatePlayableCell(){
        /**
         * updates the background of the cell to the correct colour given by maze state
         */
        boolean matrix[][] = Arena.getInstance().getMatrix();
        if (matrix[map_y][map_x]){
            this.setBackground(Colours.CELLCOLOUR);
            applyShadow();
                        
        }else{
            this.setBackground(Colours.BACKGROUND);
        }
    }

    public void applyShadow(){
        /**
         * applies a shadow to the cell if there is not a playable cell to the top or to the 
         * left of the cell.
         * this helps to create the illusion of light coming from the top left corner.
         */
        boolean matrix[][] = Arena.getInstance().getMatrix();
        if(map_y == 0 && map_x == 0 ||
        map_y == 0 && !matrix[map_y][map_x-1] || 
        map_x == 0 && !matrix[map_y-1][map_x] ||
        !matrix[map_y-1][map_x] && !matrix[map_y][map_x-1]){
            this.setBorder(topLeftShadow);
        }else if(map_y == 0 || !matrix[map_y-1][map_x]){
            this.setBorder(topShadow);
        }else if(map_x == 0 || !matrix[map_y][map_x-1]){
            this.setBorder(leftShadow);
        }
    }

    
    /** 
     * @return int
     * RELATIVE TO THE TOP LEFT OF THE CONTAINER
     * (Arena Panel)
     */
    public int getPixel_x() {
        return pixel_x;
    }
    
    /** 
     * @return int
     * RELATIVE TO THE TOP LEFT OF THE CONTAINER
     * (Arena panel)
     */
    public int getPixel_y() {
        return pixel_y;
    }

    
    /** 
     * @return IntCoordinates
     */
    public IntCoordinates getIntCoordinates() {
        return new IntCoordinates(map_x, map_y);
    }
}
    

