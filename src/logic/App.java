package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.Target;
import src.piece.decorator.Direction;
import src.piece.decorator.Flip;
import src.piece.decorator.Rotation;
import src.piece.decorator.Translation;


public class App {

    public static void run() {

        Arena arena = Arena.getInstance();

        Target target1 = new Target(new IntCoordinates(3, 3), Direction.NORTH, false, false);

        // TODO: this is for testing purposes, remove it later
        // Piece number one
        PieceBrute piece1 = new ShapeFactory("JShape", Colours.PIECE_BLUE,
                new IntCoordinates(3, 5), new IntCoordinates(2, 3));
        // Add its rotation
        Rotation rota1 = new Rotation(piece1, new IntCoordinates(2, 3), true);
        Flip flip1 = new Flip(rota1, new IntCoordinates(2, 2), false);
        Translation trans1 = new Translation(flip1, Direction.EAST, new IntCoordinates(2, 1));
        // Add the piece to the game
        PieceLogic.addPiece(trans1);
    }

    
}