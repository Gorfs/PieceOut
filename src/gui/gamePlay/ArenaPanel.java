package src.gui.gamePlay;

import javax.swing.*;
import java.awt.*;

import src.controllers.PlayerController;
import src.utils.Debug;
import src.config.*;
import src.geometry.IntCoordinates;
import src.logic.Arena;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that 
     * the game is played on
     */

     private JPanel containerPanel = new JPanel();

    public ArenaPanel() {
        super();
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.GREEN);


        int map_size = Arena.getInstance().getMatrix().length;
        containerPanel.setLayout(new GridLayout(map_size, map_size));
        // this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Add thicker and blue border here


        // filling the map with cells
        for(int i = 0; i < map_size;i++){
            for(int j = 0 ; j < map_size; j++){
                containerPanel.add(new Cell(j, i));

            }
        }
        this.add(containerPanel);
    }

    public IntCoordinates[][] getCellCoordArray(){
        // returns an array of all the gui.gameplay.cell components drawn on the map
        int width = Arena.getInstance().getMatrix().length;
        int height = Arena.getInstance().getMatrix()[0].length;
        int x = 0; 
        int y = 0;
        int xOffset = containerPanel.getX();
        int yOffset = containerPanel.getY();
        Component[] component = containerPanel.getComponents();
        IntCoordinates[][] result = new IntCoordinates[height][width];

        for(int i = 0;  i < component.length; i++){
            Cell cell = (Cell) component[i];
            result[y][x] = new IntCoordinates(cell.getPixel_x() + xOffset, cell.getPixel_y() + yOffset); 
            // Debug.out(result[y][x].toString());
            x++;
            if (x == width){
                x = 0;
                y++;
            }
        
        }
    
        return result;
    }
    

    public void updateArena(){
        /*
          updates the cells to a new matrix state.
         */
        boolean[][] matrix = Arena.getInstance().getMatrix();
        for(int i = 0; i < Math.pow(matrix.length , 2); i++){
            Cell cell = (Cell) this.getComponent(i);
            cell.updatePlayableCell();
        }
    }

    public JPanel getContainerPanel(){
        return containerPanel;
    }
}
