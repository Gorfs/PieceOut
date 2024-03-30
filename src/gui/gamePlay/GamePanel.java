package src.gui.gamePlay;

import java.awt.Graphics;

import javax.swing.*;

import src.gui.InstanceManager;
import src.gui.UI.*;
import src.logic.PieceLogic;
import src.config.*;
import src.piece.decorator.Decorator;

import java.util.ArrayList;


/**
 * This Panel is the main panel that is the player sees when completing the puzzle
 * Contains the arena, the pieces, the UI, etc.
 */
public class GamePanel extends JPanel {
    private final JPanel topPanel;
    private final ArenaPanel arenaPanel;
    private final JPanel bottomPanel;

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

    private final PieceLogic pieceLogic;

    protected final InstanceManager guiManager;

    public ArenaPanel getArenaPanel(){
        return this.arenaPanel;
    }

    public GamePanel(PieceLogic pieceLogic, InstanceManager guiManager){
        super();

        this.guiManager = guiManager;
        this.pieceLogic = pieceLogic;

        this.setLayout(null);
        this.setBackground(Colours.BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.topPanel = new TopPanel(guiManager);
        this.add(topPanel);

        this.arenaPanel = new ArenaPanel(guiManager);
        this.add(arenaPanel);

        this.bottomPanel = new BottomPanel(guiManager);
        this.add(bottomPanel);

    }

    /**
     * paints the pieces from the Piece logic class to the gui
     */
    public void paintPieces(){
        Graphics g = this.getGraphics();
        for(Decorator piece: this.pieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(this.guiManager, piece);
            guiPiece.paintComponent(g);
        }
    }

    public BottomPanel getBottomPanel(){
        return (BottomPanel) this.bottomPanel;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint the arena
        SwingUtilities.invokeLater(this::paintPieces);
    }

    public void update(){
        //main function that updated the gui of the gameplay


        repaint();
        // scheduling the painting of the pieces on the next event on the event dispatch thread
        SwingUtilities.invokeLater(this::paintPieces);
    }

}







