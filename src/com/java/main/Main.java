package src.com.java.main;

import src.com.java.logic.*;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import src.com.java.config.GuiConfig;
import src.com.java.gui.*;
import src.com.java.gui.UI.Launcher;

/**
 * The Main class is the entry point of the game. This is where we start and launch the game.
 */
public class Main {
    /** 
     * @param args arguments passed in the terminal
     */
    public static void main(String[] args){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        Point location = new Point((int) width / 2 - GuiConfig.WINDOW_WIDTH / 2, (int) height / 2 - GuiConfig.WINDOW_HEIGHT / 2);
        Launcher launcher = new Launcher(location);
        launcher.launch();
    }
}
