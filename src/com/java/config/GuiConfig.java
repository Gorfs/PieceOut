package src.com.java.config;

import static java.lang.Math.min;


public class GuiConfig {
    public static int WINDOW_WIDTH = 800;
    public static int WINDOW_HEIGHT = 800;

    public int width = WINDOW_WIDTH;
    public int height = WINDOW_HEIGHT;
    
    public static final double initArenaScale = 0.60;
    public double arenaScale = initArenaScale;
    public int CELL_SIZE = (int) Math.floor(100 * arenaScale);
    public static String resourcePath = "src/com/resources/";

    public void updateArenaScale(){
        arenaScale = initArenaScale * min(((double) width /(double) WINDOW_WIDTH), ((double)height /(double) WINDOW_HEIGHT));
        CELL_SIZE = (int) Math.floor(100 * arenaScale);
    }
}
