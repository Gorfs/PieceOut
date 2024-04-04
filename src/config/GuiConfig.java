package src.config;

import src.utils.Debug;

import static java.lang.Math.min;


public class GuiConfig {
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 800;

    public static int width = WINDOW_WIDTH;
    public static int height = WINDOW_HEIGHT;
    
    public static double arenaScale = 0.75;
    public static int CELL_SIZE = (int) Math.floor(100 * arenaScale);
    public static String iconPath = "resources/icons/";

    public static void updateArenaScale(){
        arenaScale = 0.75 * min(((double) width /(double) WINDOW_WIDTH), ((double)height /(double) WINDOW_HEIGHT));
        CELL_SIZE = (int) Math.floor(100 * arenaScale);
    }
}
