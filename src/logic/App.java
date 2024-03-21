package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.decorator.Rotation;
import src.piece.decorator.Translation;

import src.piece.Target;
import src.piece.decorator.Direction;
import src.piece.decorator.Flip;


public class App {

    public static void run() {



        Arena arena = Arena.getInstance();
        Target jTarget = new Target(new IntCoordinates(3, 3), Direction.NORTH, false, false);
        PieceBrute testPiece = new ShapeFactory("JShape", Colours.PIECE_BLUE,
            new IntCoordinates(3, 4), new IntCoordinates(2, 3), jTarget);



        Rotation testDecorator = new Rotation(testPiece, new IntCoordinates(2, 3),true);
        testDecorator = new Rotation(testDecorator, new IntCoordinates(2, 2), true);
        Flip testDecorator2 = new Flip(testDecorator, new IntCoordinates(2, 1), false);
        PieceLogic.addPiece(testDecorator2);
        
    }

    
}
