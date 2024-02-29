package src.logic;

/**
 * The Arena class represents a game arena with a specified width and height.
 * It contains methods to set the dimensions of the arena and load the arena from a file.
 */
public class Arena {
    private final int width = 7, height = 7;
    private int level;
    private boolean[][] matrix = new boolean[height][width];
    private static final Arena INSTANCE = new Arena(LevelManager.loadLevelFromJson(0));

    /**
     * Represents an arena in the game.
     * The arena is responsible for managing the dimensions and loading the arena from a file.
     *
     * @param binary the path to the file containing the arena data
     */
    private Arena(String binary){
        loadArena(binary);
    }

    public static Arena getInstance(){
        return INSTANCE;
    }

    public void loadNewArena(String binary){
        loadArena(binary);
    }

    public int getLevel(){
        return level;
    }

    
    /** 
     * @return boolean[][]
     * returns the map of the playable area.
     */
    public boolean[][] getMatrix(){
        return matrix;
    }

    /**
     * Prints the current state of the arena.
     * The empty space is where pieces can slide.
     */
    public void print(){
        for(int j=0; j< height; j++){
            for(int i=0; i< width; i++) if (!matrix[j][i]) System.out.print("## "); else System.out.print("   ");
            System.out.println();
        }
    }


    /**
     * Loads the arena from the specified file path.
     * Sets the dimensions of the arena based on the contents of the file.
     * Initializes the matrix with the dimensions of the arena.
     * Reads the contents of the file and populates the matrix accordingly.
     * Each character in the file represents a cell in the matrix.
     * '0' represents a false value (not a maps cell) and any other character represents a true value (map cell).
     *
     * @param binary the path of the file containing the arena data
     */
    public void loadArena(String binary) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = binary.charAt(i * width + j) != '0';
            }
        }
    }
}
