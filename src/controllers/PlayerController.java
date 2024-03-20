package src.controllers;

import src.geometry.IntCoordinates;
import src.gui.GUIManager;
import src.gui.UI.BottomPanel;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;
import src.utils.Debug;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import static src.config.GuiConfig.CELL_SIZE;

public class PlayerController extends JPanel implements MouseListener {
    public static PlayerController INSTANCE;

    public static UndoManager um = new UndoManager();


    /**
     * Constructor of the PlayerController where we initialize the attribute controlling
     * and add the MouseListener and KeyListener to the JPanel
     */
    public PlayerController(Container content) {
        content.addMouseListener(this);
        PlayerController.INSTANCE = this;
    }

    /**
     * Method to activate or deactivate the control of the piece
     */
    public void toggleControlManager(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX()/CELL_SIZE), e.getY()/CELL_SIZE);
        // Print the mouse position
        Debug.out(mousePos.toString());
        // Initialize the position of the piece
        IntCoordinates piecePos;

        for (var piece: PieceLogic.getPieces()) {
            // Get the position of the piece
            piecePos = piece.getLast().getOffset();
            // Look into the shape of the piece
            for(int i = 0; i < piece.getShape().length; i++){
                for(int j = 0; j < piece.getShape()[i].length; j++){
                    // Check if the mouse position is on the piece
                    if (piece.getShape()[i][j]) {
                        IntCoordinates temp = new IntCoordinates(piecePos.x() + j, piecePos.y() + i);
                        // If the mouse position is on the piece, the piece is now controlled
                        if (mousePos.equals(temp)) {
                            piece.action(temp);
                            return;
                        }
                    }
                }
            }
        }
        // If the mouse position is not on the piece, Nothing happens
    }

    // ! these functions are temporary to allow development of gui.
    public static boolean canUndo(){
        Debug.out((um.canUndo())?"can Undo":"cannot Undo");
        return um.canUndo();}

    public static boolean canRedo(){
        Debug.out((um.canRedo())?"can Redo":"cannot Redo");
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        toggleControlManager(e);
        GUIManager.INSTANCE.update();
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
