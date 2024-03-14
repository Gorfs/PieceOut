package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.ShapeFactory;

public class App {

    public static void run() {

        Arena arena = Arena.getInstance();
        PieceLogic.addPiece(new ShapeFactory("JShape", Colours.PIECE_BLUE, 
        new IntCoordinates(5, 3), new IntCoordinates(2, 3)));


        
    }

    
}
