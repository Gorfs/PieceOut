package src.com.java.main;

import java.util.concurrent.*;
import src.com.java.logic.*;

import src.com.java.gui.*;

public class Main {
    /** 
     * @param args arguments passed in terminal
     */
    public static void main(String[] args){

        int level = 0;
        /*
          Init the logic of the game
         */
        // App.run();

        // init the GUI manager
        InstanceManager instanceManager = new InstanceManager(new PieceLogic());
        instanceManager.start(level);
        
    }
}
