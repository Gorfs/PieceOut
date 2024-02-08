package src.Gui.GamePlay;

import javax.swing.*;
import java.awt.*;

import src.Config.*;
import src.Logic.App;
import src.Logic.Arena;
import src.utils.*;

public class ArenaPanel extends JPanel {
    /**
     * class that represents the panel that displays the 7x7 grid of cells that 
     * the game is played on
     */

    private class ArenaRow extends JPanel {
        /**
         * inner class that allows for a grid like layout without use of gridLayout.
         */
        public ArenaRow() {
            super();
            this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            this.setBackground(Colours.BACKGROUND);
            // this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));            
            this.setMinimumSize(new Dimension(7 * GuiConfig.CELL_SIZE, GuiConfig.CELL_SIZE));
        }
    }

    // 7x7 matrix that represents the cells on the map
    private static ArenaRow[] rows = new ArenaRow[7];

    public ArenaPanel() {
        super();
        this.setBackground(Colours.BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3)); // Add thicker and blue border here
        

        // filling the map with cells
        boolean matrix[][] = Arena.getInstance().getMatrix();
        for(int i = 0; i < 7; i++){
            rows[i] = new ArenaRow();
            for(int j = 0; j < 7; j++){
                rows[i].add(new Cell(j, i));
                // TODO change cell colour to background color if the cell is not a playable cell in the game.
                // Debug.out("added a new cell");
            }
        }
        for(ArenaRow row : rows){
            this.add(row);
        }
        
    }
    
}
