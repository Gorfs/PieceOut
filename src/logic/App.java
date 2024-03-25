package src.logic;


public class App {

    public static void run() {
        // Load the arena
        Arena arena = Arena.getInstance();
        // TODO: Choose the id of the level to load (same in Arena.java -> line 11)
        LevelManager.loadPiecesFromJson(4);
    }
}
