package com.java.logic;

import java.util.List;

import com.java.config.LogicConstants;

/**
 * The Player class represents the player of the game.
 * It contains the number of moves and stars per level.
 */
public class Player {
    /**
     * The number of moves per level.
     */
    private List<Integer> nbMovesPerLevel;
    /**
     * The number of stars per level.
     */
    private List<Integer> nbStarsPerLevel;
    /**
     * The number of moves for the current game.
     */
    private int currentNbMoves;
    /**
     * The current level.
     */
    private int currentLevel;

    /**
     * Creates a new Player object.
     * It initializes the number of moves and stars per level.
     * The number of moves for the current game is set to 0.
     */
    public Player(){
        for (int i = 0; i < LogicConstants.NUMBER_OF_LEVELS; i++){
            nbMovesPerLevel.add(-1);
            nbStarsPerLevel.add(-1);
        }
        currentNbMoves = 0;
        currentLevel = -1;
    }

    /**
     * Update the score of the player after the end of a level
     */
    public void endOfTheGameCheck(){
        if (currentNbMoves < nbMovesPerLevel.get(currentLevel)){
            nbMovesPerLevel.set(currentLevel, currentNbMoves);
        }
        //todo: get the number of stars required to unlock the next level
        //todo: set the number of stars for the current level based on the number of moves
    }
    
    /**
     * If the player has moved in the current level, increment the number of moves
     */
    public void hasMadeAMove(){
        currentNbMoves++;
    }

    /**
     * If the player has undone an action in the current level, decrement the number of moves
     */
    public void hasUndoAnAction(){
        currentNbMoves--;
    }
    
    /**
     * Retrieves the list of integers representing the number of moves per level.
     * @return  the list of integers representing the number of moves per level
     */
    public List<Integer> getNbMovesPerLevel() {
        return nbMovesPerLevel;
    }

    /**
     * Retrieves the list of integers representing the number of stars per level.
     * @return  the list of integers representing the number of stars per level
     */
    public List<Integer> getNbStarsPerLevel() {
        return nbStarsPerLevel;
    }

    /**
     * Retrieves the number of moves for the current game.
     * @return the number of moves for the current game
     */
    public int getCurrentNbMoves() {
        return currentNbMoves;
    }

    /**
     * Get the current level.
     * @return the current level
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Set the number of moves per level for the player.
     * @param nbMovesPerLevel the list of integers representing the number of moves per level
     */
    public void setNbMovesPerLevel(List<Integer> nbMovesPerLevel) {
        this.nbMovesPerLevel = nbMovesPerLevel;
    }

    /**
     * Set the number of stars per level for the player.
     * @param nbStarsPerLevel the list of integers representing the number of stars per level
     */
    public void setNbStarsPerLevel(List<Integer> nbStarsPerLevel) {
        this.nbStarsPerLevel = nbStarsPerLevel;
    }

    /**
     * Set the value of currentNbMoves.
     * @param currentNbMoves the new value for currentNbMoves
     */
    public void setCurrentNbMoves(int currentNbMoves) {
        this.currentNbMoves = currentNbMoves;
    }

    /**
     * Set the current level for the Player.
     * @param currentLevel the new level to set for the Player
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
