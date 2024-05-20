package com.java.piece.decorator;

import com.java.config.GuiConfig;
import com.java.gui.InstanceManager;
import com.java.logic.Move;
import com.java.logic.PieceLogic;
import com.java.piece.Piece;
import com.java.geometry.IntCoordinates;
import com.java.utils.Debug;


/**
 * Class that represent the decorator pattern for the translation.
 * (North, East, South, West, according to the Direction.java enumeration class).
 * It is used to translate the piece in the direction of the translation.
 * @see Decorator
 */
public class Translation extends Decorator {
    /**
     * Current direction of the translation.
     */
    private Direction dir = Direction.NORTH;
    /**
     * Array of boolean that represents the possible direction of the translation
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
    public boolean action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // If it is the right action we pressed on, then do the action.
        if (this.getActionPoint().getX() == x && this.getActionPoint().getY() == y) {
            this.updateDir(mousePos, instanceManager);
            int temp = switch (dir) {
                case NORTH -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case WEST -> 3;
            };
            if (directions[temp]) {
                personalAction();
                Debug.printDecorator(this);
                if (checkCollision(pieceLogic, instanceManager)) {
                    Debug.out("Collision detected");
                    personalReverseAction();
                    instanceManager.getSoundManager().playCollisionSound();
                    return false;
                } else {
                    Debug.out("No collision detected");
                    instanceManager.getPlayerController().getUm().addEdit(new Move(this));
                    instanceManager.incMoves();
                    instanceManager.getSoundManager().playMoveSound();
                    return true;
                }
            }
        } else {
            // If not, then we check for the next decorator.
            return this.prevPiece.action(pieceLogic, instanceManager, x, y, mousePos);
        }
        return false;
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

    /**
     * Method that applies the flip to the translation decorator.
     * @param xAxis boolean that represents the axis of the flip. True for the X-axis, false for the Y-axis
     */
    @Override
    public void applyFlip(boolean xAxis){
        boolean[] temp = directions.clone();
        int startValue = 0;
        if (!xAxis) startValue = 1;
        for (int i = startValue; i < directions.length; i = i + 2) {
            if (temp[i]) {
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

    /**
     * Method that applies the rotation to the translation decorator.
     * @param clockwise boolean that true when we rotate clockwise and false when we rotate counter-clockwise
     */
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
    public void updateDir(IntCoordinates mousePos, InstanceManager instanceManager) {
        GuiConfig guiConfig = instanceManager.getGuiConfig();
        int ratio = guiConfig.CELL_SIZE/4;
        if (mousePos.getX() < ratio) {
            dir = Direction.WEST;
        } else if (mousePos.getY() < ratio) {
            dir = Direction.NORTH;
        } else if (mousePos.getX() > guiConfig.CELL_SIZE - ratio) {
            dir = Direction.EAST;
        } else if (mousePos.getY() > guiConfig.CELL_SIZE - ratio) {
            dir = Direction.SOUTH;
        }
    }

    /**
     * Method that updates the direction of the translation.
     * @param dir the new direction of the translation
     */
    public void updateDir(Direction dir) {
        this.dir = dir;
    }

    /**
     * @return the direction of the translation
     */
    public Direction getDirection() {
        if (directions[0]) return Direction.NORTH;
        if (directions[1]) return Direction.EAST;
        if (directions[2]) return Direction.SOUTH;
        if (directions[3]) return Direction.WEST;
        return null;
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
     * @return the array of directions (North, East, South, West) according to the Direction.java enumeration class
     */
    public boolean[] getDirections() {
        return directions;
    }

    /**
     * @return the current direction of the translation
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Set the direction of the translation.
     * @param dir the new direction of the translation
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
