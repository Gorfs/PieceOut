package com.java.controllers;

import com.java.utils.Debug;
import com.java.config.GuiConfig;
import com.java.geometry.IntCoordinates;
import com.java.gui.InstanceManager;
import com.java.logic.Move;
import com.java.logic.PieceLogic;
import com.java.piece.decorator.Decorator;
import com.java.piece.decorator.Direction;
import com.java.piece.decorator.Translation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;


/**
 * Class that controls the player's actions
 */
public class PlayerController extends JPanel implements MouseListener, MouseMotionListener {
    /**
     * Variable used to manage the undo and redo actions
     */
    public UndoManager um = new UndoManager();


    /**
     * Variable used to manage the pieces, it contains the pieces that are in the game
     */
    protected final PieceLogic pieceLogic;
    /**
     * Variable used to link the instance manager to the controller
     */
    protected final InstanceManager instanceManager;
    protected final GuiConfig guiConfig;


    IntCoordinates mousePosInWindow;
    int i, j; // local coords of a shape array

    Decorator selectedPiece = null;
    IntCoordinates pressPos = null;

    /**
     * Constructor of the PlayerController where we initialize the attribute
     * controlling
     * and add the MouseListener and KeyListener to the JPanel
     * @param instanceManager Instance manager of the game we need to link to the player controller
     * @param pieceLogic Logic of the pieces used to manage the pieces in the game
     */
    public PlayerController(InstanceManager instanceManager, PieceLogic pieceLogic, GuiConfig guiConfig) {
        this.instanceManager = instanceManager;
        this.pieceLogic = pieceLogic;
        this.guiConfig = guiConfig;
    }

    /**
     * Method to start the controller
     * @param content Container where the controller will be added
     */
    public void startController(Container content){
        content.addMouseListener(this);
        content.addMouseMotionListener(this);
    }

    public void stopController(Container content){
        content.removeMouseListener(this);
        content.removeMouseMotionListener(this);
    }

    /**
     * Method to activate or deactivate the control of the piece
     * @param e MouseEvent that contains the position of the mouse
     */
    public Decorator isMouseOnPiece(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE), e.getY() / guiConfig.CELL_SIZE);
        IntCoordinates mousePosInWindow = new IntCoordinates(e.getX() % guiConfig.CELL_SIZE, e.getY() % guiConfig.CELL_SIZE);
        // Print the mouse position
        Debug.out("Mouse pos: " + mousePosInWindow + " " + mousePos);
        // Initialize the position of the piece
        IntCoordinates piecePos;

        for (var piece : this.pieceLogic.getPieces()) {
            // Get the position of the piece
            piecePos = piece.getPiece().getOffset();
            // Look into the shape of the piece
            for (int i = 0; i < piece.getShape().length; i++) {
                for (int j = 0; j < piece.getShape()[i].length; j++) {
                    // Check if the mouse position is on the piece
                    if (piece.getShape()[i][j]) {
                        IntCoordinates temp = new IntCoordinates(piecePos.getX() + j, piecePos.getY() + i);
                        // If the mouse position is on the piece, the piece is now controlled
                        if (mousePos.equals(temp)) {
                            // triggering action at local coords of a shape array
                            this.mousePosInWindow = mousePosInWindow;
                            this.i = i;
                            this.j = j;
                            return piece;
                        }
                    }
                }
            }
        }
        // If the mouse position is not on the piece, return false
        return null;
    }

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
                        if (t.checkCollision(pieceLogic)) {
                            t.personalReverseAction();
                            instanceManager.getSound().playCollisionSound();
                            selectedPiece = null;
                            break;
                        } else {
                            um.addEdit(new Move(t));
                            instanceManager.incMoves();
                            instanceManager.getSound().playMoveSound();
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
        selectedPiece = null;
//        Debug.out("piece released");
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

        IntCoordinates newPos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE), e.getY() / guiConfig.CELL_SIZE);

        // get drag direction
        Direction dragDir = dragDirection(newPos);

//        Debug.out("dragDir : " + dragDir);

        //slide the piece
        slidePiece(dragDir);


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
