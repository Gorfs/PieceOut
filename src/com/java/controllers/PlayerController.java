package com.java.controllers;

import com.java.logic.Move;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Direction;
import com.java.piece.decorator.Translation;
import com.java.config.GuiConfig;
import com.java.utils.Debug;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.PieceLogic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;


/**
 * Class that controls the player's actions.
 */
public class PlayerController extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * Variable used to manage the undo and redo actions.
     */
    private final UndoManager um = new UndoManager();
    /**
     * Variable used to manage the pieces, it contains the pieces that are in the game.
     */
    protected final PieceLogic pieceLogic;
    /**
     * Variable used to link the instance manager to the controller.
     */
    protected final InstanceManager instanceManager;
    /**
     * Variable used to manage the GUI configuration.
     */
    protected final GuiConfig guiConfig;

    /**
     * Variables used to manage the position of the mouse.
     */
    protected IntCoordinates mousePosInWindow;
    /**
     * Variable used to manage the selected piece.
     */
    protected Decorator selectedPiece = null;
    /**
     * Variables used to manage the position of the selected piece.
     */
    protected int i, j;
    /**
     * Variables used to manage the position of the mouse when the mouse is pressed.
     */
    protected IntCoordinates pressPos = null;

    /**
     * Constructor of the PlayerController where we initialize the attribute
     * controlling and add the MouseListener and KeyListener to the JPanel.
     * @param instanceManager Instance manager of the game we need to link to the player controller
     * @param pieceLogic Logic of the pieces used to manage the pieces in the game
     */
    public PlayerController(InstanceManager instanceManager, PieceLogic pieceLogic, GuiConfig guiConfig) {
        this.instanceManager = instanceManager;
        this.pieceLogic = pieceLogic;
        this.guiConfig = guiConfig;
    }

    /**
     * Method that start the controller.
     * @param content Container where the controller will be added
     */
    public void startController(Container content){
        content.addMouseListener(this);
        content.addMouseMotionListener(this);
    }

    /**
     * Method that stop the controller.
     * @param content Container where the controller will be removed
     */
    public void stopController(Container content){
        content.removeMouseListener(this);
        content.removeMouseMotionListener(this);
    }

    /**
     * Method to enable or disable the control of the piece.
     * @param e MouseEvent that contains the position of the mouse
     */
    public Decorator isMouseOnPiece(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE),
                e.getY() / guiConfig.CELL_SIZE);
        IntCoordinates mousePosInWindow = new IntCoordinates(e.getX() % guiConfig.CELL_SIZE,
                e.getY() % guiConfig.CELL_SIZE);
        // Print the mouse position
        Debug.out("Mouse pos: " + mousePosInWindow + " " + mousePos);
        // Initialize the position of the piece
        IntCoordinates piecePos;

        for (var piece : this.pieceLogic.getPieces()) {
            // Get the position of the piece
            piecePos = piece.getPiece().getOffset();
            // Look into the shape of the piece
            for (IntCoordinates pos: piece.getPiece().getPosShape()) {
                // The position given is a position true in the shape of the piece because it is in the posShape array,
                // so no need to verify if the position is true in the shape
                IntCoordinates temp = new IntCoordinates(piecePos.getX() + pos.getX(),
                        piecePos.getY() + pos.getY());
                // If the mouse position is on the piece, the piece is now controlled
                if (mousePos.equals(temp)) {
                    // triggering action at local coords of a shape array
                    this.mousePosInWindow = mousePosInWindow;
                    this.i = pos.getY();
                    this.j = pos.getX();
                    return piece;
                }
            }
        }
        // If the mouse position is not on the piece, return false
        return null;
    }

    /**
     * Method that gives the direction of the drag.
     * @param newPos new position of the mouse
     * @return the direction of the drag
     */
    private Direction dragDirection(IntCoordinates newPos){
        Direction dragDir = switch (newPos.getX() - pressPos.getX()) {
            case 1, 2 -> Direction.EAST;
            case -1, -2 -> Direction.WEST;
            default -> switch (newPos.getY() - pressPos.getY()) {
                case 1, 2 -> Direction.SOUTH;
                case -1, -2 -> Direction.NORTH;
                default -> null;
            };
        };
        pressPos = newPos;
        return dragDir;
    }

    /**
     * Method that slides the piece in the direction of the drag.
     * @param dragDir the direction of the drag
     */
    private void slidePiece(Direction dragDir){
        // go through the decorators of the piece and check if it matches the drag direction
        var temp = selectedPiece;
        Debug.out("temp : " + temp.getClass().getSimpleName());
        while (temp != null) {
            if (temp instanceof Translation t) {
                Debug.out("t : " + t.getDirection());
                for (int k=0; k<4; k++) {
                    if (t.getDirections()[k] && Direction.values()[k] == dragDir) {
                        t.updateDir(Direction.values()[k]);
                        t.personalAction();
                        if (t.checkCollision(pieceLogic, instanceManager)) {
                            t.personalReverseAction();
                            instanceManager.getSoundManager().playCollisionSound();
                            selectedPiece = null;
                            break;
                        } else {
                            um.addEdit(new Move(t));
                            instanceManager.incMoves();
                            instanceManager.getSoundManager().playMoveSound();
                            instanceManager.update();
                        }
                        Debug.out("actioned");
                        break;
                    }
                }
            }
            if (!(temp.getPrevPiece() instanceof Decorator)) break;
            temp =  (Decorator) temp.getPrevPiece();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        selectedPiece = isMouseOnPiece(e);
        if (selectedPiece != null) {
            boolean actionSuccess = selectedPiece.action(this.pieceLogic, instanceManager, j, i, mousePosInWindow);
            if (actionSuccess) {
                Debug.out("triggering piece action");
                this.instanceManager.update();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectedPiece = isMouseOnPiece(e);
        if (selectedPiece != null) {
            pressPos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE), e.getY() / guiConfig.CELL_SIZE);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Debug.out("piece released");
        selectedPiece = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedPiece == null) return;
        Debug.out("piece dragged");
        // get a new position
        IntCoordinates newPos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE), e.getY() / guiConfig.CELL_SIZE);
        // get drag direction
        Direction dragDir = dragDirection(newPos);
        //slide the piece
        slidePiece(dragDir);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public UndoManager getUm() {
        return um;
    }
}
