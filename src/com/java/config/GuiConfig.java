package src.com.java.config;

import static java.lang.Math.min;


public class GuiConfig {
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 800;

    public static int width = WINDOW_WIDTH;
    public static int height = WINDOW_HEIGHT - 30;
    
    public static double arenaScale = 0.75;
    public static int CELL_SIZE = (int) Math.floor(100 * arenaScale);
    public static String resourcePath = "src/com/resources/";

    public static void updateArenaScale(){
        arenaScale = 0.75 * min(((double) width /(double) WINDOW_WIDTH), ((double)height /(double) WINDOW_HEIGHT));
        CELL_SIZE = (int) Math.floor(100 * arenaScale);
    }
}
