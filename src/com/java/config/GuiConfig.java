package com.java.config;

import static java.lang.Math.min;


/**
 * Class that contains all the GUI configurations.
 */
public class GuiConfig {
    /**
     * Default value for the window width. By default, it's 800.
     */
    public static int WINDOW_WIDTH = 800;
    /**
     * Default value for the window height. By default, it's 800.
     */
    public static int WINDOW_HEIGHT = 800;

    /**
     * Value of the window width.
     */
    public int width = WINDOW_WIDTH;
    /**
     * Value of the window height.
     */
    public int height = WINDOW_HEIGHT;

    /**
     * Default scale of the arena. By default, it's 0.75.
     */
    public static final double initArenaScale = 0.60;
    /**
     * Scale of the arena.
     */
    public double arenaScale = initArenaScale;
    /**
     * Size of the cell. By default, it's arenaScale(0.75) times 100, so 75.
     */
    public int CELL_SIZE = (int) Math.floor(100 * arenaScale);

    /**
     * Path to the resources. By default, it's "src/com/resources/".
     */
    public static String resPath = "src/com/resources/";

    /**
     * Method to update the scale of the arena when we change the window's size.
     */
    public void updateArenaScale(){
        arenaScale = initArenaScale * min(((double) width /(double) WINDOW_WIDTH), ((double)height /(double) WINDOW_HEIGHT));
        CELL_SIZE = (int) Math.floor(100 * arenaScale);
    }
}
