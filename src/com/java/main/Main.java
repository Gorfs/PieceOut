package src.com.java.main;

import src.com.java.logic.*;

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
        int level = 0;
        Launcher launcher = new Launcher();
        launcher.launch();
    }
}
