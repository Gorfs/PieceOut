package src.gui.gamePlay;

import javax.swing.*;
import java.awt.*;

import src.config.*;
import src.logic.Arena;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that 
     * the game is played on
     */

    public ArenaPanel() {
        super();
        this.setLayout(new GridBagLayout());
        JPanel containerPanel = new JPanel();
        this.setBackground(Colours.BACKGROUND);
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
    
}
