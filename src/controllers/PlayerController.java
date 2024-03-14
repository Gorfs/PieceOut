package src.controllers;

import src.geometry.IntCoordinates;
import src.gui.GUIManager;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;
import src.utils.Debug;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;

public class PlayerController extends JPanel implements MouseListener {
    private Decorator piece;
    protected IntCoordinates[][] cellsCoords = GUIManager.INSTANCE.getGamePanel().getArenaPanel().getCellCoordArray();

    public static PlayerController INSTANCE;

    /**
     * Constructor of the PlayerController where we initialize the attribute controlling
     * and add the MouseListener and KeyListener to the JPanel
     */

    public PlayerController(Container content) {
        this.piece = null;
        content.addMouseListener(this);
        PlayerController.INSTANCE = this;
    }


    /**
     * Method to activate or deactivate the control of the piece
     */
    public void toggleControlManager(MouseEvent e) {
        //todo: add code to take control of the piece that is closest to the mouse
        IntCoordinates pos = new IntCoordinates(e.getX(), e.getY());
        boolean found = false;
        int x = -1, y = -1;
        for (int i = 0; i < this.cellsCoords.length; i++) {
            for (int j = 0; j < this.cellsCoords[i].length; j++) {
                if (this.cellsCoords[i][j].x() >= pos.x() && this.cellsCoords[i][j].y() >= pos.y()) {
                    x = j;
                    y = i;
                    Debug.out("x: " + x + " y: " + y);
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        for (var piece: PieceLogic.getPieces()) {
            pos = piece.getLast().getOffset();

            Debug.out(pos.toString());
            if ((pos.x()) == x && pos.y() == y) {
                Debug.out("Player is now controlling the piece");
                this.piece = piece;
                return;
            }
        }
        Debug.out("Player is no longer controlling the piece");
        this.piece = null;
    }

    /**
     * Method to rotate the piece if the player is controlling one

    public void rotate() {
        if (this.controlling){
            //todo: add code to ask the piece to rotate according to the decoration pattern
        }
    }
     */


    // ! these functions are temporary to allow development of gui.
    public static boolean canUndo(){
        return false;
    }

    public static boolean canRedo(){
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        toggleControlManager(e);
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

    public Decorator getPiece() {
        return piece;
    }

    /**
     * Method to move the piece if the player is controlling one according to the key pressed
     * @param e the KeyEvent that contains the key pressed by the player

    public void move(KeyEvent e){
        if(this.controlling){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Z:
                    //todo: add code to ask the piece to move up according to the decoration pattern
                    break;

                case KeyEvent.VK_Q:
                    //todo: add code to ask the piece to move left according to the decoration pattern
                    break;
                
                case KeyEvent.VK_S:
                    //todo: add code to ask the piece to move down according to the decoration pattern
                    break;
                
                case KeyEvent.VK_D:
                    //todo: add code to ask the piece to move right according to the decoration pattern
                    break;
            
                default:
                    break;
            }
        }
    }
     */
}
