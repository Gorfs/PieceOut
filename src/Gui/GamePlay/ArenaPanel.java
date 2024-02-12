package src.Gui.GamePlay;

import javax.swing.*;
import java.awt.*;

import src.Config.*;
import src.Logic.Arena;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that 
     * the game is played on
     */

    public ArenaPanel() {
        super();
        this.setBackground(Colours.BACKGROUND);
        int map_size = Arena.getInstance().getMatrix().length;
        boolean[][] matrix = Arena.getInstance().getMatrix();
        this.setLayout(new GridLayout(map_size, map_size));
        // this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Add thicker and blue border here
        

        // filling the map with cells
        for(int i = 0; i < map_size;i++){
            for(int j = 0 ; j < map_size; j++){
                this.add(new Cell(j, i));

            }
        }
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
