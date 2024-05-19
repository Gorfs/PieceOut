package com.java.logic;

import com.java.gui.InstanceManager;


/**
 * The Arena class represents a game arena with a specified width and height.
 * Max width and height are 7.
 */
public class Arena {
    /**
     * The width of the arena.
     */
    private final int width;
    /**
     * The height of the arena.
     */
    private final int height;
    /**
     * The matrix representing the arena.
     */
    private final boolean[][] matrix;

    /**
     * Represents an arena in the game.
     * The arena is responsible for managing the dimensions and loading the arena from a file.
     * @param iManager the instance manager that contains the level data
     */
    public Arena(InstanceManager iManager){
        String[] data = LevelManager.loadLevelFromJson(iManager.level);
        assert data != null;
        width = Integer.parseInt(data[1]);
        height = Integer.parseInt(data[2]);
        matrix = new boolean[height][width];
        loadArena(data[0]);
    }
   
    /**
     * Loads the arena with the given binary string.
     * The binary string represents the state of each cell in the arena.
     * '0' represents an empty cell, while any other character represents a filled cell.
     * @param binary the binary string representing the arena state
     */
    public void loadArena(String binary) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = binary.charAt(i * width + j) != '0';
            }
        }
    }

    /**
     * @return boolean[][] that represents the map of the playable area
     */
    public boolean[][] getMatrix(){
        return matrix;
    }
}
