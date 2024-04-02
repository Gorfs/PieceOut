package src.com.java.logic;

public class GameLoop {
    /**
     * Indicates whether the game loop is currently running.
     */
    private static boolean running = false;

    /**
     * The main game loop that runs continuously until the game is stopped.
     */
    public static void loop() {
        running = true;
        long lastTime = System.nanoTime();
        double amountOfFramePerSecond = 60.0;
        while (running) {
            if (System.nanoTime() - lastTime >= 1000000000 / amountOfFramePerSecond) {
                lastTime = System.nanoTime();
                update();
            }
        }
    }

    /**
        calls all the updates in their respective classes.
     */
    public static void update() {
        System.out.println("update");
    }
}