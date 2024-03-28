package src.main;

import src.logic.*;

import src.gui.*;

public class Main {
    /** 
     * @param args arguments passed in terminal
     */
    public static void main(String[] args){

        int level = 4;
        /*
          Init the logic of the game
         */
        // App.run();

        // init the GUI manager
        GUIManager guiManager = new GUIManager(new PieceLogic());
        guiManager.start(level);
    }
}
