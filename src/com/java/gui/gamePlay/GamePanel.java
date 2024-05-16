package src.com.java.gui.gamePlay;

import java.awt.Graphics;

import javax.swing.*;
import java.awt.*;

import src.com.java.gui.InstanceManager;
import src.com.java.gui.UI.*;
import src.com.java.logic.PieceLogic;
import src.com.java.config.Colours;
import src.com.java.config.*;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.Decorator;

import java.util.ArrayList;


/**
 * This Panel is the main panel that is the player sees when completing the puzzle
 * Contains the arena, the pieces, the UI, etc.
 */
public class GamePanel extends JPanel {
    private final JPanel topPanel;
    private ArenaContainerPanel arenaContainerPanel;
    private ArenaPanel arenaPanel;
    private final JPanel bottomPanel;

    private final PieceLogic pieceLogic;

    protected final InstanceManager guiManager;

    public ArenaPanel getArenaPanel(){
        return this.arenaPanel;
    }

    // this getter is mostly used to get it's y position, could probably be done in a better way.
    public ArenaContainerPanel getArenaContainerPanel(){
        return this.arenaContainerPanel;
    }

    public GamePanel(PieceLogic pieceLogic, InstanceManager guiManager){
        super();

        this.guiManager = guiManager;
        this.pieceLogic = pieceLogic;

        // this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.setBackground(Colours.BACKGROUND);

        this.topPanel = new TopPanel(guiManager);
        this.add(topPanel, BorderLayout.NORTH);

        this.arenaContainerPanel = new ArenaContainerPanel(guiManager);
        this.arenaPanel = arenaContainerPanel.getArenaPanel();
        this.add(arenaContainerPanel, BorderLayout.CENTER);

        this.bottomPanel = new BottomPanel(guiManager);
        this.add(bottomPanel, BorderLayout.SOUTH);
        // this.setMaximumSize(new Dimension(GuiConfig.width, GuiConfig.height - 200));
    }

    /**
     * paints the pieces from the Piece logic class to the gui
     */
    public void paintPieces(){
        if (guiManager.isTransitioning()){
            return;
        }
        Graphics g = this.getGraphics();
        for(Decorator piece: this.pieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(this.guiManager, piece);
            guiPiece.paintComponent(g);
        }
    }

    public void paintTargets(){
        if (guiManager.isTransitioning()){
            return;
        }
        Graphics g = this.getGraphics();
        for(Target target: this.pieceLogic.getTargets()){
            GuiTarget guiTarget = new GuiTarget(this.guiManager, target);
            guiTarget.paintComponent(g);
        }
    }


    public BottomPanel getBottomPanel(){
        return (BottomPanel) this.bottomPanel;
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint the arena

        SwingUtilities.invokeLater(this::paintTargets);
        SwingUtilities.invokeLater(this::paintPieces);
    }

    public void update() {
        //main function that updated the gui of the gameplay
        repaint();

        // scheduling the painting of the pieces on the next event on the event dispatch thread
        SwingUtilities.invokeLater(this::paintTargets);
        SwingUtilities.invokeLater(this::paintPieces);
    }

}







