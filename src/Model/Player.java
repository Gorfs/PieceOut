package src.Model;

import src.Config.LogicConstants;

public class Player {
    int[] nbMovesPerLevel; //number of moves per level
    int[] nbStarsPerLevel; //number of stars per level
    int nbMovesCurrent; //number of moves in the current level
    int currentLevel;

    public Player(){
        nbMovesPerLevel = new int[LogicConstants.NUMBER_OF_LEVELS];
        nbStarsPerLevel = new int[LogicConstants.NUMBER_OF_LEVELS];
        nbMovesCurrent = 0;
        currentLevel = -1;
    }

    /**
     * Update the score of the player after the end of a level
     */

    public void endOfTheGameCheck(){
        if (nbMovesCurrent < nbMovesPerLevel[currentLevel]){
            nbMovesPerLevel[currentLevel] = nbMovesCurrent;
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
