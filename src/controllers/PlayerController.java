package src.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class PlayerController extends JPanel{
    private boolean controlling;

    public PlayerController() {
        this.controlling = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    controlManager();
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    rotate();
                }
                else if (e.getKeyCode() == KeyEvent.VK_Z) {
                    moveUp();
                }
                else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    moveLeft();
                }
                else if (e.getKeyCode() == KeyEvent.VK_S) {
                    moveDown();
                }
                else if (e.getKeyCode() == KeyEvent.VK_D) {
                    moveRight();
                }
            }
        });

        this.setFocusable(true);
    }

    public void controlManager() {
        if (this.controlling) {
            this.controlling = false;
            System.out.println("Player is no longer controlling the piece");
            //todo: add code to release the control of the actual piece
        }
        else {
            this.controlling = true;
            System.out.println("Player is now controlling a piece");
            //todo: add code to take control of the piece that is closest to the mouse
        }
    }

    public void rotate() {
        //todo: add code to ask the piece to rotate according to the decoration pattern
    }

    public void moveUp() {
        //todo: add code to ask the piece to move up according to the decoration pattern
    }

    public void moveLeft() {
        //todo: add code to ask the piece to move left according to the decoration pattern
    }

    public void moveDown() {
        //todo: add code to ask the piece to move down according to the decoration pattern
    }

    public void moveRight() {
        //todo: add code to ask the piece to move right according to the decoration pattern
    }


}
