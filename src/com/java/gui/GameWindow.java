package src.com.java.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import src.com.java.config.*;



public class GameWindow extends JFrame{
    /**
     * Constructs a new GameWindow object.
     */
    public GameWindow(){
        super("Game");
        this.setBackground(Colours.BACKGROUND);
        // this.setResizable(false);
        this.setSize(new Dimension(GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT));
        // this.setMaximumSize(new Dimension(GuiConfig.width, GuiConfig.height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
