package src.Gui.GamePlay;

import javax.swing.*;
import java.awt.*;

import src.Config.*;
import src.Logic.Arena;

public class Cell extends JPanel{
    /**
     * class for the gui representatoin of the playable cells in the game.
     */

    
    // pixel_x and pixel_y are the coordinates of the cell in pixels RELATIVE to the CONTAINER (ARENA PANEL)
    private int pixel_x;
    private int pixel_y;

    
    public Cell(int x_coord, int y_coord){
        super();

        // hard-fixing cell size
        this.setMinimumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        this.setMaximumSize(new Dimension(GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));

        if(Arena.getInstance().getMatrix()[y_coord][x_coord] == false){
            this.setBackground(Colours.BACKGROUND);
        }else{
            this.setBackground(Colours.CELLCOLOUR);
            // this.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border
        }

        this.pixel_x = x_coord * GuiConfig.CELL_SIZE; 
        this.pixel_y = y_coord * GuiConfig.CELL_SIZE;

        // this.add(new JLabel("x: " + pixel_x + " y: " + pixel_y));
    }

    public int getPixel_x() {
        return pixel_x;
    }
    public int getPixel_y() {
        return pixel_y;
    }

    //! Cell extends JPanel, so x and y are already inherited, do not overwrite x y with int coordinates
    // public int getX() {
    //     return x;
    // }
    // public int getY() {
    //     return y;
    // }
}
    

