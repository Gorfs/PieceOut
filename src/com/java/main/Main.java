package com.java.main;

import com.java.logic.*;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import com.java.config.GuiConfig;
import com.java.gui.*;
import com.java.gui.UI.Launcher;

public class Main {
    /** 
     * @param args arguments passed in the terminal
     */
    public static void main(String[] args){
        /*
          Init the logic of the game
         */
        // App.run();

        // init the GUI manager
        //InstanceManager instanceManager = new InstanceManager(new PieceLogic());
        //instanceManager.start(level);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        
        Point location = new Point((int) width / 2 - GuiConfig.WINDOW_WIDTH / 2, (int) height / 2 - GuiConfig.WINDOW_HEIGHT / 2);
        Launcher launcher = new Launcher(location);
        launcher.launch();
        
    }
}
