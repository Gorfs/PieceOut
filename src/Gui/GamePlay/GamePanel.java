package src.Gui.GamePlay;

import javax.swing.*;

import src.Gui.UI.*;
import src.Config.*;

import java.awt.*;

public class GamePanel extends JPanel {
    /**
     * This Panel is the main panel that is the player sees when completing the puzzle
     * Contains the arena, the pieces, the UI,etc..
     */
    JPanel centerArenaPanel = new JPanel(); // panel used to center the arena in the middle of the panel

    public GamePanel(){
        super();
        this.setBackground(Colours.BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new TopPanel());
        centerArenaPanel.add(new ArenaPanel());
//        centerArenaPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED));
        centerArenaPanel.setOpaque(false);
        this.add(centerArenaPanel);


    }






}
