package src.com.java.piece.decorator;

import src.com.java.config.GuiConfig;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.Move;
import src.com.java.logic.PieceLogic;
import src.com.java.piece.Piece;
import src.com.java.utils.Debug;

/**
 * Class that represent the decorator pattern for the translation.
 * (North, East, South, West, according to the Direction.java enumeration class).
 */
public class Translation extends Decorator {
    /**
     * Current direction of the translation.
     */
    private Direction dir = Direction.NORTH;
    /**
     * Array of boolean that represents the possible direction of the translation.
     * (North, East, South, West).
     */
    private final boolean[] directions;

    /**
     * Constructor for the decorator Translation.
     * @param p Piece on which we want to create the decorator
     * @param d Direction of the translation
     */
    public Translation(Piece p, boolean[] d, IntCoordinates local) {
        super(p, local);
        this.directions = d;
    }

    @Override
    public void action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // Update the direction of the translation
        if (this.getActionPoint().getX() == x && this.getActionPoint().getY() == y) {
            this.updateDir(mousePos);
            if (directions[Direction.valueOf(dir.toString()).ordinal()]) {
                // Do the action
                personalAction();
                Debug.printDecorator(this);
                if (checkCollision(pieceLogic)) {
                    // If the piece is colliding with the walls, then we reverse the action.
                    Debug.out("Collision detected");
                    personalReverseAction();
                } else {
                    // If not, then we add the move to the undo manager.
                    Debug.out("No collision detected");
                    instanceManager.playerController.um.addEdit(new Move(this));
                }
            }
        } else {
            // If not, then we check for the next decorator.
            this.prevPiece.action(pieceLogic, instanceManager, x, y, mousePos);
        }

    }

    /**
     * Translation action of the piece.
     * The translation is done by moving the piece in the direction of the translation.
     */
    @Override
    public void personalAction() {
        IntCoordinates pos = this.getPiece().getPos();
        // Switch to check for each direction
        switch (dir) {
            case NORTH -> this.getPiece().setPos(new IntCoordinates(pos.getX(), pos.getY() - 1));
            case EAST -> this.getPiece().setPos(new IntCoordinates(pos.getX() + 1, pos.getY()));
            case SOUTH -> this.getPiece().setPos(new IntCoordinates(pos.getX(), pos.getY() + 1));
            case WEST -> this.getPiece().setPos(new IntCoordinates(pos.getX() - 1, pos.getY()));
        }
    }

    /**
     * Reverse the translation action of the piece.
     */
    @Override
    public void personalReverseAction() {
        invertDir();
        personalAction();
        invertDir();
    }

    @Override
    public void applyFlip(boolean xAxis){
        if (xAxis)
            for (int i = 0; i < directions.length; i = i + 2) {
                if (directions[i]) {
                    directions[i] = false;
                    directions[(i + 2) % 4] = true;
                }
            }
        else
            for (int i = 1; i < directions.length; i = i + 2) {
                if (directions[i]) {
                    directions[i] = false;
                    directions[(i + 2) % 4] = true;
                }
            }
        this.flipActionPoint(xAxis);
        // recursively apply flip to the rest of the list
        if (this.getPrevPiece() instanceof Decorator){
            ((Decorator) this.getPrevPiece()).applyFlip(xAxis);
        }
    }

    @Override
    public void applyRotation(boolean clockwise) {
        this.rotateActionPoint(clockwise);
        boolean[] tmp = directions.clone();
        if (clockwise) {
            for (int i = 0; i < directions.length; i++)
                if (tmp[i]) {
                    directions[i] = false;
                    directions[(i + 1) % 4] = true;
                }
        } else {
            for (int i = 0; i < directions.length; i++)
                if (tmp[i]) {
                    directions[i] = false;
                    directions[(i + 3) % 4] = true;
                }
        }
        if (this.getPrevPiece() instanceof Decorator)
            ((Decorator) this.getPrevPiece()).applyRotation(clockwise);
    }

    /**
     * Method that updates the direction of the translation according to the mouse position.
     * @param mousePos Coordinates of the mouse
     */
    public void updateDir(IntCoordinates mousePos) {
        int ratio = GuiConfig.CELL_SIZE/4;
        if (mousePos.getX() < ratio) {
            dir = Direction.WEST;
        } else if (mousePos.getY() < ratio) {
            dir = Direction.NORTH;
        } else if (mousePos.getX() > GuiConfig.CELL_SIZE - ratio) {
            dir = Direction.EAST;
        } else if (mousePos.getY() > GuiConfig.CELL_SIZE - ratio) {
            dir = Direction.SOUTH;
        }
    }

    /**
     * Method that inverts the direction of the translation.
     */
    public void invertDir() {
        switch (dir) {
            case NORTH -> dir = Direction.SOUTH;
            case EAST -> dir = Direction.WEST;
            case SOUTH -> dir = Direction.NORTH;
            case WEST -> dir = Direction.EAST;
        }
    }

    /**
     * @return the array of directions (North, East, South, West) according to the Direction.java enumeration class.
     */
    public boolean[] getDirections() {
        return directions;
    }

    /**
     * @return the current direction of the translation.
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Set the direction of the translation.
     * @param dir the new direction of the translation.
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
