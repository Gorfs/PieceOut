package src.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Arena class represents a game arena with a specified width and height.
 * It contains methods to set the dimensions of the arena and load the arena from a file.
 */
public class Arena {
    private int width, height;
    private boolean[][] matrix;
    private static final Arena INSTANCE = new Arena("resources/maps/lv1.txt");

    /**
     * Represents an arena in the game.
     * The arena is responsible for managing the dimensions and loading the arena from a file.
     *
     * @param filePath the path to the file containing the arena data
     */
    private Arena(String filePath){
        setDimensions(filePath);
        loadArena(filePath);
    }

    public static Arena getInstance(){
        return INSTANCE;
    }

    public void loadNewArena(String filePath){
        setDimensions(filePath);
        loadArena(filePath);
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
     * Helper method to fill the map with values from a 2D array.
     * Please leave a space between each value in the same line in the txt file.
     * Sets the dimensions of the arena based on the contents of the file specified by the given file path.
     * The width is determined by the length of the first line divided by 2 plus 1.
     * The height is determined by the number of lines in the file.
     *
     * @param filePath the path of the file containing the arena dimensions
     */
    private void setDimensions(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                if (lineCount == 0) width = line.length()/2 + 1;
                lineCount++;
            }
            height = lineCount;
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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
     * @param filePath the path of the file containing the arena data
     */
    public void loadArena(String filePath) {
        setDimensions(filePath);
        matrix = new boolean[height][width];
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int row = 0;
            while ((line = bufferedReader.readLine()) != null) {
                for (int col = 0; col < line.length(); col += 2) { // increment by 2 to skip spaces
                    matrix[row][col / 2] = Character.getNumericValue(line.charAt(col)) == 0 ? false : true; // divide by 2 to adjust for spaces
                }
                row++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
