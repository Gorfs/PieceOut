package src.Logic;

import java.util.ArrayList;

public class GameLoop {
    private static boolean running = false;

    public static void loop(){
        running = true;
        long lastTime = System.nanoTime();
        double amountOfFramePerSecond = 60.0;
        while(running){
            if(System.nanoTime() - lastTime >= 1000000000 / amountOfFramePerSecond){
                lastTime = System.nanoTime();
                update();
            }
        }
    }


    public static void update(){
        System.out.println("update");
    }
}