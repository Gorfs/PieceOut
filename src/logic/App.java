package src.logic;

import src.config.Colours;
import src.geometry.IntCoordinates;
import src.piece.PieceBrute;
import src.piece.ShapeFactory;
import src.piece.decorator.Rotation;

import src.piece.Target;
import src.piece.decorator.Direction;
import src.piece.decorator.Flip;


public class App {

    public static void run() {
        // Load the arena
        Arena arena = Arena.getInstance();
        // TODO: Choose the id of the level to load (same in Arena.java -> line 11)
        LevelManager.loadPiecesFromJson(4);
    }
}
