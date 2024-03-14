package src.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import src.config.*;



public class GameWindow extends JFrame{
    /**
     * Constructs a new GameWindow object.
     */
    public GameWindow(){
        super("Game");
        setSize(GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
        this.setBackground(Colours.BACKGROUND);
        // this.setResizable(false);
        this.setSize(new Dimension(src.config.GuiConfig.WINDOW_WIDTH, src.config.GuiConfig.WINDOW_HEIGHT));
        this.setMaximumSize(new Dimension(src.config.GuiConfig.WINDOW_WIDTH, src.config.GuiConfig.WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
