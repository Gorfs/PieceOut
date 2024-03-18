package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.ShapeFactory;
import src.piece.Target;
import src.piece.decorator.Direction;

public class App {

    public static void run() {

        Arena arena = Arena.getInstance();
        Target jTarget = new Target(new IntCoordinates(3, 3), Direction.NORTH);
        PieceLogic.addPiece(new ShapeFactory("JShape", Colours.PIECE_BLUE, 
        new IntCoordinates(5, 3), new IntCoordinates(2, 3), jTarget));


        
    }

    
}
