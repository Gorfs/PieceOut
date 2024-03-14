package src.utils;

import src.geometry.IntCoordinates;
import src.piece.PieceBrute;

/** Utils packages for debugging
*/

public class Debug {

    //TODO switch to false when prototype is finished.
    private static boolean debugging = true;

    
    /** 
     * @return boolean true if the program is in debugging mode
     */
    public static boolean isDeubgging(){
        return debugging;
    }

    public static void printBooleanArray(boolean[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void printShapePieces(PieceBrute p){
        IntCoordinates fixturePoint = p.getFixturePoint();
        System.out.println("Fixture point is : " + fixturePoint.x() + " " + fixturePoint.y());
        boolean[][] shape = p.getShape();
        for (int i = 0; i < shape.length; i++){
            for (int j = 0; j < shape[i].length; j++){
                // System.out.println(" i = " + i + " j = " + j + " fixturePoint.x() = " + fixturePoint.x() + " fixturePoint.y() = " + fixturePoint.y());
                if (fixturePoint.x() == (j) && fixturePoint.y() == (i)){
                    System.out.print("X");
                } else {
                    System.out.print(shape[i][j] == true ? "O" : ".");
                }
            }
            System.out.println();
        }
    }

    private static int debugMessages = 0;
    
    /**
     * Toggles the debugging mode
     */
    public static void toggleDebugging(){
        debugging = !debugging;
    }
    
    /** 
     * @param message the message to print to the terminal
     */
    public static void out(String message){
        if(debugging){
            System.out.println("DEBUG > " + debugMessages + " : " + message);
            debugMessages++;
        }
    }
    
}
