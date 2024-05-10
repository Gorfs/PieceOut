package src.com.java.config;

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
    public static int width = WINDOW_WIDTH;
    /**
     * Value of the window height.
     */
    public static int height = WINDOW_HEIGHT - 30;

    /**
     * Scale of the arena. By default, it's 0.75
     */
    public static double arenaScale = 0.75;
    /**
     * Size of the cell. By default, it's 75.
     */
    public static int CELL_SIZE = (int) Math.floor(100 * arenaScale);

    /**
     * Path to the resources. By default, it's "src/com/resources/"
     */
    public static String resPath = "src/com/resources/";

    /**
     * Method to update the scale of the arena when we change the window's size.
     */
    public static void updateArenaScale(){
        arenaScale = 0.75 * min(((double) width /(double) WINDOW_WIDTH), ((double)height /(double) WINDOW_HEIGHT));
        CELL_SIZE = (int) Math.floor(100 * arenaScale);
    }
}
