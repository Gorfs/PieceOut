package src.logic;

import src.gui.InstanceManager;

/**
 * The Arena class represents a game arena with a specified width and height.
 * Max width and height are 7.
 */
public class Arena {
    private int width = 7, height = 7;
    private int level;
    private boolean[][] matrix;

    /**
     * Represents an arena in the game.
     * The arena is responsible for managing the dimensions and loading the arena from a file.
     *
     * @param binary is a string of 49 chars that represents the arena.
     */
    public Arena(InstanceManager guiManager){
        String[] data = LevelManager.loadLevelFromJson(guiManager.level);
        width = Integer.parseInt(data[1]);
        height = Integer.parseInt(data[2]);
        matrix = new boolean[height][width];
        loadArena(data[0]);
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
     * Loads the arena with the given binary string.
     * The binary string represents the state of each cell in the arena.
     * '0' represents an empty cell, while any other character represents a filled cell.
     * 
     * @param binary the binary string representing the arena state
     */
    public void loadArena(String binary) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = binary.charAt(i * width + j) != '0';
            }
        }
    }
}
