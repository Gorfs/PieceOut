package src.utils;
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
