package src.com.java.controllers;

import src.com.java.logic.Move;
import src.com.java.piece.decorator.Decorator;
import src.com.java.piece.decorator.Direction;
import src.com.java.piece.decorator.Translation;
import src.com.java.utils.Debug;
import src.com.java.geometry.IntCoordinates;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;

import static src.com.java.config.GuiConfig.CELL_SIZE;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

public class PlayerController extends JPanel implements MouseListener, MouseMotionListener {
    public static PlayerController INSTANCE;

    public UndoManager um = new UndoManager();

    protected final PieceLogic pieceLogic;
    protected final InstanceManager instanceManager;


    IntCoordinates mousePosInWindow;
    int i, j; // local coords of a shape array

    Decorator selectedPiece = null;

    /**
     * Constructor of the PlayerController where we initialize the attribute
     * controlling
     * and add the MouseListener and KeyListener to the JPanel
     */
    public PlayerController(InstanceManager instanceManager, PieceLogic pieceLogic) {
        this.instanceManager = instanceManager;
        this.pieceLogic = pieceLogic;
        PlayerController.INSTANCE = this;
    }

    public void startController(Container content){
        content.addMouseListener(this);
        content.addMouseMotionListener(this);
    }

    /**
     * Method to activate or deactivate the control of the piece
     */
    public Decorator isMouseOnPiece(MouseEvent e) {
        // Get mouse position in the ArenaPanel
        IntCoordinates mousePos = new IntCoordinates((e.getX() / CELL_SIZE), e.getY() / CELL_SIZE);
        IntCoordinates mousePosInWindow = new IntCoordinates(e.getX() % CELL_SIZE, e.getY() % CELL_SIZE);
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
//            Debug.out("Selected piece: " + selectedPiece);
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

        IntCoordinates newPos = new IntCoordinates((e.getX() / CELL_SIZE), e.getY() / CELL_SIZE);
        IntCoordinates piecePos = selectedPiece.getLast().getOffset();


//        if (piecePos.getX()+j == newPos.getX() && piecePos.getY()+i == newPos.getY()) return;
//        Debug.out ("clicked: " + piecePos.getX()+j + " " + piecePos.getY()+i);

//        Debug.out("newPos : " + newPos);
//        Debug.out("piecePosBefore : " + selectedPiece.getLast().getOffset());


        // get drag direction
        Direction dragDir;
        switch (newPos.getX() - piecePos.getX()) {
            case 1:
                dragDir = Direction.EAST;
                break;
            case -1:
                dragDir = Direction.WEST;
                break;
            default:
                switch (newPos.getY() - piecePos.getY()) {
                    case 1:
                        dragDir = Direction.SOUTH;
                        break;
                    case -1:
                        dragDir = Direction.NORTH;
                        break;
                    default:
                        return;
                }
        }
        Debug.out("dragDir : " + dragDir);

        // go through the decorators of the piece and check if it matches the drag direction
        var temp = selectedPiece;
        Debug.out("temp : " + temp.getClass().getSimpleName());
        while (temp != null) {
            if (temp instanceof Translation t) {
                Debug.out("t : " + t.getDirection());
                if (t.getDirection() == dragDir) {
                    t.updateDir(t.getDirection());
                    t.personalAction();
                    if (t.checkCollision(pieceLogic)) {
                        t.personalReverseAction();
                        instanceManager.getSound().playCollisionSound();
                        selectedPiece = null;
                        break;
                    } else {
                        instanceManager.playerController.um.addEdit(new Move(t));
                        instanceManager.incMoves();
                        instanceManager.getSound().playMoveSound();
                    }
                    Debug.out("actioned");
                    break;
                }
            }
            if (!(temp.getPrevPiece() instanceof Decorator)) break;
            temp =  (Decorator) temp.getPrevPiece();
        }


        this.instanceManager.update();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
