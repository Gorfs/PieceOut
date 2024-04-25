package src.com.java.controllers;

import src.com.java.utils.Debug;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;

import static src.com.java.config.GuiConfig.CELL_SIZE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;


/**
 * Class that controls the player's actions
 */
public class PlayerController extends JPanel implements MouseListener {
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

    /**
     * Constructor of the PlayerController where we initialize the attribute
     * controlling
     * and add the MouseListener and KeyListener to the JPanel
     * @param instanceManager Instance manager of the game we need to link to the player controller
     * @param pieceLogic Logic of the pieces used to manage the pieces in the game
     */
    public PlayerController(InstanceManager instanceManager, PieceLogic pieceLogic) {
        this.instanceManager = instanceManager;
        this.pieceLogic = pieceLogic;
    }

    /**
     * Method to start the controller
     * @param content Container where the controller will be added
     */
    public void startController(Container content){
        content.addMouseListener(this);
    }

    /**
     * Method to activate or deactivate the control of the piece
     * @param e MouseEvent that contains the position of the mouse
     */
    public void toggleControlManager(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX() / CELL_SIZE), e.getY() / CELL_SIZE);
        // Print the mouse position
        Debug.out(mousePos.toString());
        // Initialize the position of the piece
        IntCoordinates piecePos;

        for (var piece : this.pieceLogic.getPieces()) {
            // Get the position of the piece
            piecePos = piece.getLast().getOffset();
            // Look into the shape of the piece
            for (int i = 0; i < piece.getShape().length; i++) {
                for (int j = 0; j < piece.getShape()[i].length; j++) {
                    // Check if the mouse position is on the piece
                    if (piece.getShape()[i][j]) {
                        IntCoordinates temp = new IntCoordinates(piecePos.getX() + j, piecePos.getY() + i);
                        // If the mouse position is on the piece, the piece is now controlled
                        if (mousePos.equals(temp)) {
                            // triggering action at local coords of a shape array
                            piece.action(this.pieceLogic, instanceManager, j, i);
                            Debug.out("triggering piece action");
                            return;
                        }
                    }
                }
            }
        }
        // If the mouse position is not on the piece, Nothing happens
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        toggleControlManager(e);
        this.instanceManager.update();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
