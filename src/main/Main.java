package src.main;

import javax.swing.*;

// TODO precise imports to reduce import cost
import src.Gui.GamePlay.*;
import java.awt.*;

import src.Gui.*;

public class Main {

    
    private static RootPanel rootPanel = new RootPanel();
    /** 
     * @param args arguments passed in terminal
     */
    public static void main(String[] args){


        GameWindow root = new GameWindow();
        
        root.add(rootPanel);


        root.setVisible(true);
    }
}
