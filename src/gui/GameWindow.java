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
        setSize(GuiConfig.width, GuiConfig.height);
        this.setBackground(Colours.BACKGROUND);
        // this.setResizable(false);
        this.setSize(new Dimension(GuiConfig.width, GuiConfig.height));
        this.setMaximumSize(new Dimension(GuiConfig.width, GuiConfig.height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
