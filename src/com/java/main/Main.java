package src.com.java.main;

import src.com.java.logic.*;

import src.com.java.gui.*;
import src.com.java.gui.UI.Launcher;

public class Main {
    /** 
     * @param args arguments passed in the terminal
     */
    public static void main(String[] args){

        int level = 0;
        /*
          Init the logic of the game
         */
        // App.run();

        // init the GUI manager
        //InstanceManager instanceManager = new InstanceManager(new PieceLogic());
        //instanceManager.start(level);

        Launcher launcher = new Launcher();
        launcher.launch();
        
    }
}
