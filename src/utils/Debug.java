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
            System.out.println("DEBUG > " + message);
        }
    }
    
}
