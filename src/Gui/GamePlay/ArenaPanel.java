package src.Gui.GamePlay;

import javax.swing.*;
import java.awt.*;

import src.Config.*;
import src.utils.*;

public class ArenaPanel extends JPanel {

    private class ArenaRow extends JPanel {
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
        
        for(int i = 0; i < 7; i++){
            rows[i] = new ArenaRow();
            for(int j = 0; j < 7; j++){
                rows[i].add(new Cell(j, i));
                Debug.out("added a new cell");
            }
        }
        for(ArenaRow row : rows){
            this.add(row);
        }
        
    }
    
}
