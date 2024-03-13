package src.gui.gamePlay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import src.gui.GUIManager;
import src.gui.UI.*;
import src.logic.PieceLogic;
import src.config.*;
import src.piece.decorator.Decorator;
import src.utils.Debug;

import java.util.ArrayList;


public class GamePanel extends JPanel {
    /**
     * This Panel is the main panel that is the player sees when completing the puzzle
     * Contains the arena, the pieces, the UI,etc..
     */
    private ArenaPanel arenaPanel = new ArenaPanel();
    private JPanel topPanel = new TopPanel();
    private JPanel bottomPanel = new BottomPanel();

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

    public ArenaPanel getArenaPanel(){
        return this.arenaPanel;
    }


    public GamePanel(){
        super();
        this.setLayout(null);
        this.setBackground(Colours.BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        this.add(arenaPanel);
        this.add(bottomPanel);

    }

    public void paintPieces(){
        /**
         * paints the pieces from the Piece logic class to the gui
         */
        Graphics g = this.getGraphics();
        for(Decorator piece: PieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(piece);
            guiPiece.paintComponent(g);
        }

    }
    


    public void update(){
        //main function that updated the gui of the gameplay


        repaint();
        // scheduling the painting of the pieces on the next event on the event dispatch thread
        SwingUtilities.invokeLater(() -> {
            paintPieces();
        });
    }

}







