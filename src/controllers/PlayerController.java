package src.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class PlayerController extends JPanel{
    private boolean controlling; //todo: change boolean to the type of a nextPiece


    /**
     * Constructor of the PlayerController where we initialize the attribute controlling
     * and add the MouseListener and KeyListener to the JPanel
     */

    public PlayerController() {
        this.controlling = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    toggleControlManager();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotate();
                }
                else{
                    move(e);
                }
            }
        });

        this.setFocusable(true);
    }


    /**
     * Method to activate or deactivate the control of the nextPiece
     */

    public void toggleControlManager() {
        if (this.controlling) {
            this.controlling = false;
            System.out.println("Player is no longer controlling the nextPiece");
            //todo: add code to release the control of the actual nextPiece
        }
        else {
            this.controlling = true;
            System.out.println("Player is now controlling a nextPiece");
            //todo: add code to take control of the nextPiece that is closest to the mouse
        }
    }

    /**
     * Method to rotate the nextPiece if the player is controlling one
     */

    public void rotate() {
        if (this.controlling){
            //todo: add code to ask the nextPiece to rotate according to the decoration pattern
        }
    }


    // ! these functions are temporary to allow development of gui.
    public static boolean canUndo(){
        return false;
    }

    public static boolean canRedo(){
        return true;
    }

    /**
     * Method to move the nextPiece if the player is controlling one according to the key pressed
     * @param e the KeyEvent that contains the key pressed by the player
     */

    public void move(KeyEvent e){
        if(this.controlling){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_Z:
                    //todo: add code to ask the nextPiece to move up according to the decoration pattern
                    break;

                case KeyEvent.VK_Q:
                    //todo: add code to ask the nextPiece to move left according to the decoration pattern
                    break;
                
                case KeyEvent.VK_S:
                    //todo: add code to ask the nextPiece to move down according to the decoration pattern
                    break;
                
                case KeyEvent.VK_D:
                    //todo: add code to ask the nextPiece to move right according to the decoration pattern
                    break;
            
                default:
                    break;
            }
        }
    }


}
