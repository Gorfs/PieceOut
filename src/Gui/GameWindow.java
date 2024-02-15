package src.Gui;

import javax.swing.JFrame;

import src.Config.*;


public class GameWindow extends JFrame{
    /**
     * Constructs a new GameWindow object.
     */
    public GameWindow(){
        super("Game");
        setSize(GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
        this.setBackground(Colours.BACKGROUND);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
