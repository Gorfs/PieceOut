package src.com.java.gui;

import src.com.java.config.GuiConfig;
import src.com.java.config.LogicConstants;
import src.com.java.geometry.IntCoordinates;
import src.com.java.piece.Piece;
import src.com.java.piece.Target;
import src.com.java.piece.decorator.Decorator;
import src.com.java.controllers.PlayerController;
import src.com.java.gui.UI.Launcher;
import src.com.java.gui.UI.LevelTransitionPanel;
import src.com.java.gui.gamePlay.*;
import src.com.java.logic.Arena;
import src.com.java.logic.LevelManager;
import src.com.java.logic.PieceLogic;
import src.com.java.utils.Debug;

import static src.com.java.config.GuiConfig.height;

import static src.com.java.config.GuiConfig.width;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent; // Import the ActionEvent class

/**
 * GUIManager class that is responsible for creating, changing,
 * updating and general management of the GUI.
 */
public class InstanceManager {
    /**
     * mains panels and windows for the game
     */
    private final GameWindow mainFrame = new GameWindow();
    private GamePanel gamePanel;

    // layout to change panels in rootPanel
    private final CardLayout layout = new CardLayout();
    private final LevelTransitionPanel levelTransitionPanel = new LevelTransitionPanel(this);

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    private ArrayList<GuiTarget> guiTargets = new ArrayList<GuiTarget>();
    private final PieceLogic pieceLogic;
    public PlayerController playerController;
    public Arena arena;
    private InstanceManager secondPlayer;

    public int level;
    private int players;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InstanceManager(PieceLogic pieceLogic, Point location) {
        /*
         * init of instance
         */
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = this;
        players = 1;
    }

    public InstanceManager(PieceLogic pieceLogic, Point location, InstanceManager secondPlayer) {
        /*
         * init of instance
         */
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = secondPlayer;
        players = 2;
    }

    public void start(int level) {
        /*
         * initiates the GUI window.
         */

        this.level = level;
        playerController = new PlayerController(this, pieceLogic);
        arena = new Arena(this);

        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel());
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(GuiConfig.width, GuiConfig.height));
        gamePanel.setBounds(0, 0, GuiConfig.width, GuiConfig.height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(levelTransitionPanel, JLayeredPane.PALETTE_LAYER);
    
        mainFrame.add(layeredPane);
        mainFrame.setVisible(true);


        LevelManager.loadPiecesFromJson(this.pieceLogic, level, this);

        for (Target target : this.pieceLogic.getTargets()) {
            GuiTarget guiTarget = new GuiTarget(this, target);
            guiTargets.add(guiTarget);
            // guiTarget.repaint();
        }
        
        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }
        this.mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                GuiConfig.width = mainFrame.getWidth();
                GuiConfig.height = mainFrame.getHeight() - 30; // TODO don't hardcode like this
                GuiConfig.updateArenaScale();

                for (Cell[] cells : gamePanel.getArenaPanel().getCells())
                    for (Cell cell : cells) cell.updateCell();
                gamePanel.setBounds(0, 0, GuiConfig.width, GuiConfig.height);
                levelTransitionPanel.setBounds(0, 0, GuiConfig.width, GuiConfig.height);
                if (!levelTransitionPanel.isTransitioning()){
                    levelTransitionPanel.setLocation(GuiConfig.width, 0);
                }
            }
        });
        levelTransitionPanel.animationOnStart();
    }

    public int getArenaPanelY() {
        return this.gamePanel.getArenaPanel().getY() + this.gamePanel.getArenaContainerPanel().getY();
    }

    /** 
     * updates the GUI
    */
    public void update() {
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons(); //TODO move this call to inside the gamePanel.update() function

        if (pieceLogic.checkWin()) levelTransitionPanel.animationAtEnd();

    }

    /*
     * method that is called when the player has won the level
     */



    public boolean isTransitioning() {
        return levelTransitionPanel.isTransitioning();
    }

    public void nextLevel() {
        if (level + 1 != LogicConstants.NUMBER_OF_LEVELS){
            Debug.out("You win!");
            Point location = mainFrame.getLocation();
            InstanceManager next = new InstanceManager(new PieceLogic(), location, secondPlayer);
            level++;
            next.start(level);
            this.clear();
        }
        else{
            this.returnToMainMenu();
        }

    }



    /**
     * changes the panel in the main frame to the panel with the name panelName
     */
    public void changePanel(String panelName) {
        layout.show(mainFrame, panelName);
    }

    public PieceLogic getPieceLogic() {
        return pieceLogic;
    }

    public int getHeight() {
        return this.mainFrame.getHeight();
    }

    private void clear(){
        this.pieceLogic.clearPieces();
        this.pieceLogic.clearTargets();
        this.guiPieces.clear();
        this.guiTargets.clear();
        // close window
        this.mainFrame.dispose();

    }

    public void returnToMainMenu() {
        if (secondPlayer == null && players == 2) {
            return;
        }
        this.clear();
        if (players == 2){
            secondPlayer.clear();
        }
        Point location = mainFrame.getLocation();
        Launcher launcher = new Launcher(location);
        launcher.launch();
    }
}
