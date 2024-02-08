package src.main;

import javax.swing.*;

// TODO precise imports to reduce import cost
import src.Gui.GamePlay.*;
import java.awt.*;
import src.Logic.*;

import src.Gui.*;

public class Main {
    /** 
     * @param args arguments passed in terminal
     */
    public static void main(String[] args){


        /**
         * Init the logic of the game 
         */
        App.run();

        /**
         * init the GUI manager
         */
        GUIManager.INSTANCE.start(); 


    }
}
