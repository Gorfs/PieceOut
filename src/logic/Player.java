package src.logic;

import java.util.List;

import src.config.LogicConstants;

public class Player {
    public static List<Integer> nbMovesPerLevel; //number of moves per level
    public static List<Integer> nbStarsPerLevel; //number of stars per level
    public static int nbMovesCurrent; //number of moves in the current level
    public static int currentLevel;

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


}
