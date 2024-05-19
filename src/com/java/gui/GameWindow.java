package com.java.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import com.java.config.*;


/**
 * This class is responsible for the main window of the game.
 * It is a JFrame that contains the game and represents the main window of the game.
 */
public class GameWindow extends JFrame{
    /**
     * Constructs a new GameWindow object.
     */
    public GameWindow(){
        super("Game");
        this.setBackground(Colours.BACKGROUND);
        this.setSize(new Dimension(GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
