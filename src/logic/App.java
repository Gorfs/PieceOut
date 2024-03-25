package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.Target;
import src.piece.decorator.Direction;
import src.piece.decorator.Rotation;
import src.piece.decorator.Translation;


public class App {

    public static void run() {

        Arena arena = Arena.getInstance();

        Target target1 = new Target(new IntCoordinates(4, 1), Direction.NORTH, false, false);

        // TODO: this is for testing purposes, remove it later
        // Piece number one
        PieceBrute piece1 = new ShapeFactory("LShape", Colours.PIECE_BLUE,
                new IntCoordinates(5, 5), new IntCoordinates(2, 3));
        // Add its rotation
        Rotation rota1 = new Rotation(piece1, new IntCoordinates(1, 2), true);
        rota1.personalAction();
        rota1.personalAction();
        rota1.personalAction();
        // Add the piece to the game
        PieceLogic.addPiece(rota1);

        // Piece number two
        PieceBrute piece2 = new ShapeFactory("SingleShape", Colours.PIECE_RED,
                new IntCoordinates(1, 5), new IntCoordinates(1, 1), target1);
        Translation trans2 = new Translation(piece2, Direction.EAST, new IntCoordinates(1, 1));
        PieceLogic.addPiece(trans2);
    }

    
}