package src.main;

import src.logic.*;

import src.gui.*;

public class Main {
    /** 
     * @param args arguments passed in terminal
     */
    public static void main(String[] args){


        /*
          Init the logic of the game
         */
        App.run();

        /*
          init the GUI manager
         */
        GUIManager.INSTANCE.start(); 



    }
}
