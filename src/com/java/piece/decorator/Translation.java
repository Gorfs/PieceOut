package src.com.java.piece.decorator;

import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.Move;
import src.com.java.logic.MoveEdit;
import src.com.java.logic.PieceLogic;
import src.com.java.piece.Piece;
import src.com.java.geometry.IntCoordinates;
import src.com.java.utils.Debug;

/**
 * Class that represent the decorator pattern for the translation.
 * (North, East, South, West, according to the Direction.java enumeration
 * class).
 */
public class Translation extends Decorator {
    /**
     * Direction of the translation
     */
    private Direction dir = Direction.NORTH;
    /**
     * Array of boolean that represents the direction of the translation
     * (North, East, South, West).
     */
    private final boolean[] directions;

    /**
     * Constructor for the decorator Translation.
     * 
     * @param p Piece on which we want to create the decorator
     * @param d Direction of the translation
     */
    public Translation(Piece p, boolean[] d, IntCoordinates local) {
        super(p, local);
        this.directions = d;
    }


    @Override
    public void applyFlip(boolean xAxis){
        for (int i = 0; i < directions.length; i++) {
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
            for (int i = 0; i < directions.length; i++) {
                if (tmp[i]) {
                    directions[i] = false;
                    directions[(i + 1) % 4] = true;
                }
            }
        } else {
            for (int i = 0; i < directions.length; i++) {
                if (tmp[i]) {
                    directions[i] = false;
                    directions[(i + 3) % 4] = true;
                }
            }
        }
        if(this.getPrevPiece() instanceof Decorator){
            ((Decorator)this.getPrevPiece()).applyRotation(clockwise);
        }
    }

    @Override
    public void action(PieceLogic pieceLogic, InstanceManager instanceManager, int x, int y, IntCoordinates mousePos) {
        // If it is the right action we pressed on, then do the action.
        // (deal with offset)
        if (this.actionPoint.getX() == x && this.actionPoint.getY() == y) {
            // TODO: Create a box for each direction for multiple direction choice
            this.updateDir(mousePos);
            int temp = switch (dir) {
                case NORTH -> 0;
                case EAST -> 1;
                case SOUTH -> 2;
                case WEST -> 3;
            };
            if (directions[temp]) {
                personalAction();
                Debug.printDecorator(this);
                if (checkCollision(pieceLogic)) {
                    Debug.out("Collision detected");
                    personalReverseAction();
                } else {
                    Debug.out("No collision detected");
                    instanceManager.playerController.um.addEdit(new MoveEdit(new Move(this)));
                }
            }

            // If not, then we check for the next decorator.
        } else {
            this.prevPiece.action(pieceLogic, instanceManager, x, y, mousePos);
        }

    }

    /**
     * Translation action of the nextPiece.
     */
    @Override
    public void personalAction() {
        IntCoordinates pos = this.getLast().getPos();
        // Switch to check for each direction
        switch (dir) {
            case NORTH -> this.getLast().setPos(new IntCoordinates(pos.getX(), pos.getY() - 1));
            case EAST -> this.getLast().setPos(new IntCoordinates(pos.getX() + 1, pos.getY()));
            case SOUTH -> this.getLast().setPos(new IntCoordinates(pos.getX(), pos.getY() + 1));
            case WEST -> this.getLast().setPos(new IntCoordinates(pos.getX() - 1, pos.getY()));
        }
    }

    @Override
    public void personalReverseAction() {
        IntCoordinates pos = this.getLast().getPos();
        // Switch to check for each direction
        switch (dir) {
            case NORTH -> this.getLast().setPos(new IntCoordinates(pos.getX(), pos.getY() + 1));
            case EAST -> this.getLast().setPos(new IntCoordinates(pos.getX() - 1, pos.getY()));
            case SOUTH -> this.getLast().setPos(new IntCoordinates(pos.getX(), pos.getY() - 1));
            case WEST -> this.getLast().setPos(new IntCoordinates(pos.getX() + 1, pos.getY()));
        }
    }

    public boolean[] getDirections() {
        return directions;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void updateDir(IntCoordinates mousePos) {
        if (mousePos.getX() < GuiConfig.CELL_SIZE/4) {
            dir = Direction.WEST;
        } else if (mousePos.getY() < GuiConfig.CELL_SIZE/4) {
            dir = Direction.NORTH;
        } else if (mousePos.getX() > GuiConfig.CELL_SIZE - GuiConfig.CELL_SIZE/4) {
            dir = Direction.EAST;
        } else if (mousePos.getY() > GuiConfig.CELL_SIZE - GuiConfig.CELL_SIZE/4) {
            dir = Direction.SOUTH;
        }
    }
}
