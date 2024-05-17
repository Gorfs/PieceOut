package src.com.java.controllers;

import src.com.java.config.GuiConfig;
import src.com.java.utils.Debug;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

public class PlayerController extends JPanel implements MouseListener {
    public static PlayerController INSTANCE;

    public UndoManager um = new UndoManager();

    protected final PieceLogic pieceLogic;
    protected final InstanceManager instanceManager;
    protected final GuiConfig guiConfig;

    /**
     * Constructor of the PlayerController where we initialize the attribute
     * controlling
     * and add the MouseListener and KeyListener to the JPanel
     */
    public PlayerController(InstanceManager instanceManager, PieceLogic pieceLogic, GuiConfig guiConfig) {
        this.instanceManager = instanceManager;
        this.pieceLogic = pieceLogic;
        this.guiConfig = guiConfig;
        PlayerController.INSTANCE = this;
    }

    public void startController(Container content){
        content.addMouseListener(this);
    }

    /**
     * Method to activate or deactivate the control of the piece
     */
    public void toggleControlManager(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX() / guiConfig.CELL_SIZE), e.getY() / guiConfig.CELL_SIZE);
        IntCoordinates mousePosInWindow = new IntCoordinates(e.getX() % guiConfig.CELL_SIZE, e.getY() % guiConfig.CELL_SIZE);
        // Print the mouse position
        Debug.out("Mouse pos: " + mousePosInWindow + " " + mousePos);
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
                            piece.action(this.pieceLogic, instanceManager, j, i, mousePosInWindow);
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
