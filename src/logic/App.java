package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.decorator.Rotation;


public class App {

    public static void run() {

        Arena arena = Arena.getInstance();
        PieceBrute testPiece = new ShapeFactory("JShape", Colours.PIECE_BLUE,
         new IntCoordinates(5, 2), new IntCoordinates(2, 3));
        Rotation testDecorator = new Rotation(testPiece, testPiece.getPos(), true);
        PieceLogic.addPiece(testDecorator);
        
    }

    
}
