package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.decorator.Rotation;
import src.piece.decorator.Translation;


public class App {

    public static void run() {

        Arena arena = Arena.getInstance();
        PieceBrute testPiece = new ShapeFactory("JShape", Colours.PIECE_BLUE,
            new IntCoordinates(3, 2), new IntCoordinates(2, 3));
        Rotation testDecorator = new Rotation(testPiece, new IntCoordinates(2, 3),true);
        testDecorator = new Rotation(testDecorator, new IntCoordinates(2, 2), true);
        PieceLogic.addPiece(testDecorator);
        
    }

    
}
