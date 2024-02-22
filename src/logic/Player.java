package src.logic;

import java.util.List;

import src.config.LogicConstants;

public class Player {
    private static List<Integer> nbMovesPerLevel; //number of moves per level
    private static List<Integer> nbStarsPerLevel; //number of stars per level
    private static int nbMovesCurrent; //number of moves in the current level
    private static int currentLevel;

    public Player(){
        for (int i = 0; i < LogicConstants.NUMBER_OF_LEVELS; i++){
            nbMovesPerLevel.add(-1);
            nbStarsPerLevel.add(-1);
        }
        nbMovesCurrent = 0;
        currentLevel = -1;
    }

    /**
     * Update the score of the player after the end of a level
     */

    public void endOfTheGameCheck(){
        if (nbMovesCurrent < nbMovesPerLevel.get(currentLevel)){
            nbMovesPerLevel.set(currentLevel, nbMovesCurrent);
        }
        //todo: get the number of stars required to unlock the next level
        //todo: set the number of stars for the current level based on the number of moves
    }
    
    /**
     * If the player has moved in the current level, increment the number of moves
     */

    public void hasMadeAMove(){
        nbMovesCurrent++;
    }

    /**
     * If the player has undone an action in the current level, decrement the number of moves
     */

    public void hasUndoAnAction(){
        nbMovesCurrent--;
    }

    
    /**
     * Retrieves the list of integers representing the number of moves per level.
     *
     * @return  the list of integers representing the number of moves per level
     */
    public static List<Integer> getNbMovesPerLevel() {
        return nbMovesPerLevel;
    }
    

    /**
     * Retrieves the list of integers representing the number of stars per level.
     *
     * @return  the list of integers representing the number of stars per level
     */
    public static List<Integer> getNbStarsPerLevel() {
        return nbStarsPerLevel;
    }


    /**
     * Retrieves the number of moves for the current game.
     *
     * @return the number of moves for the current game
     */
    public static int getNbMovesCurrent() {
        return nbMovesCurrent;
    }

    
    /**
     * Get the current level.
     *
     * @return the current level
     */
    public static int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Set the number of moves per level for the player.
     *
     * @param  nbMovesPerLevel  the list of integers representing the number of moves per level
     */
    public static void setNbMovesPerLevel(List<Integer> nbMovesPerLevel) {
        Player.nbMovesPerLevel = nbMovesPerLevel;
    }

    /**
     * Set the number of stars per level for the player.
     *
     * @param  nbStarsPerLevel  the list of integers representing the number of stars per level
     */
    public static void setNbStarsPerLevel(List<Integer> nbStarsPerLevel) {
        Player.nbStarsPerLevel = nbStarsPerLevel;
    }

    /**
     * Set the value of nbMovesCurrent.
     *
     * @param  nbMovesCurrent the new value for nbMovesCurrent
     */
    public static void setNbMovesCurrent(int nbMovesCurrent) {
        Player.nbMovesCurrent = nbMovesCurrent;
    }

    /**
     * Set the current level for the Player.
     *
     * @param  currentLevel   the new level to set for the Player
     */
    public static void setCurrentLevel(int currentLevel) {
        Player.currentLevel = currentLevel;
    }


}
