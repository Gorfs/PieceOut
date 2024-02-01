package src.Gui;

import javax.swing.JFrame;

import src.Config.*;

public class GameWindow extends JFrame{

    public GameWindow(){
        super("Game");
        setSize(800, 600);
        // TODO, set size to constants in config rather than hardcoded
        this.setBackground(Colours.BACKGROUND);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
