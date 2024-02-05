package src.utils;
/** Utils packages for debugging
*/

public class Debug {

    //TODO switch to false when prototype is finished.
    private static boolean debugging = true;

    
    /** 
     * @return boolean
     */
    public static boolean isDeubgging(){
        return debugging;
    }

    
    /** 
     * @param value 
     */
    public static void setDebuggin(boolean value){
        debugging = value;
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
