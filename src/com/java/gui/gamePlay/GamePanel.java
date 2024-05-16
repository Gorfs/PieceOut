package src.com.java.gui.gamePlay;

import src.com.java.config.Colours;
import src.com.java.gui.InstanceManager;
import src.com.java.gui.UI.BottomPanel;
import src.com.java.gui.UI.TopPanel;
import src.com.java.logic.PieceLogic;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.Decorator;

import javax.swing.*;
import java.awt.*;


/**
 * This Panel is the main panel that's what the player sees playing.
 * It contains the arena, the pieces, the UI, etc.
 */
public class GamePanel extends JPanel {
    private final ArenaContainerPanel arenaContainerPanel;
    private final ArenaPanel arenaPanel;
    private final JPanel bottomPanel;

    private final PieceLogic pieceLogic;

    protected final InstanceManager instanceManager;

    /**
     * Constructor for the GamePanel class, it initializes the attributes, sets the background color and
     * set up the layout and the panels.
     * @param pieceLogic PieceLogic instance that manages the pieces
     * @param iManager InstanceManager instance that manages the game
     */
    public GamePanel(PieceLogic pieceLogic, InstanceManager iManager){
        super();
        // initializing the attributes
        this.instanceManager = iManager;
        this.pieceLogic = pieceLogic;
        // setting the background color and the layout
        this.setLayout(new BorderLayout());
        this.setBackground(Colours.BACKGROUND);

        JPanel topPanel = new TopPanel(this.instanceManager);
        this.add(topPanel, BorderLayout.NORTH);

        this.arenaContainerPanel = new ArenaContainerPanel(this.instanceManager);
        this.arenaPanel = arenaContainerPanel.getArenaPanel();
        this.add(arenaContainerPanel, BorderLayout.CENTER);

        this.bottomPanel = new BottomPanel(this.instanceManager);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * paints the pieces from the Piece logic class to the gui
     */
    public void paintPieces(){
        if (instanceManager.isTransitioning()){
            return;
        }
        Graphics g = this.getGraphics();
        for(Decorator piece: this.pieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(this.instanceManager, piece);
            guiPiece.paintComponent(g);
        }
    }

    /**
     * paints the targets from the Piece logic class to the gui.
     */
    public void paintTargets(){
        if (instanceManager.isTransitioning()){
            return;
        }
        Graphics g = this.getGraphics();
        for(Target target: this.pieceLogic.getTargets()){
            GuiTarget guiTarget = new GuiTarget(this.instanceManager, target);
            guiTarget.paintComponent(g);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint the arena

        SwingUtilities.invokeLater(this::paintTargets);
        SwingUtilities.invokeLater(this::paintPieces);
    }

    /**
     * Main method that updates the gui of the game.
     */
    public void update(){
        repaint();

        // scheduling the painting of the pieces on the next event on the event dispatch thread
        SwingUtilities.invokeLater(this::paintTargets);
        SwingUtilities.invokeLater(this::paintPieces);
    }

    /**
     * @return the arena container panel.
     */
    // this getter is mostly used to get its position, could probably be done in a better way.
    public ArenaContainerPanel getArenaContainerPanel(){
        return this.arenaContainerPanel;
    }

    /**
     * @return the arena panel.
     */
    public ArenaPanel getArenaPanel(){
        return this.arenaPanel;
    }

    /**
     * @return the bottom panel of the game
     */
    public BottomPanel getBottomPanel(){
        return (BottomPanel) this.bottomPanel;
    }
}







