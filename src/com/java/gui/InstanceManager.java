package src.com.java.gui;

import src.com.java.config.*;
import src.com.java.config.GuiConfig;
import src.com.java.config.LogicConstants;
import src.com.java.gui.sound.Sound;
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

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

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

    private final GuiConfig guiConfig = new GuiConfig();

    // layout to change panels in rootPanel
    private final CardLayout layout = new CardLayout();
    private final LevelTransitionPanel levelTransitionPanel = new LevelTransitionPanel(this);

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    private ArrayList<GuiTarget> guiTargets = new ArrayList<GuiTarget>();
    private final PieceLogic pieceLogic;
    public PlayerController playerController;
    public Arena arena;
    private Sound sound;
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
        sound = new Sound();
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
        sound = new Sound();
        players = 2;
    }



    public void start(int level) {
        /*
         * initiates the GUI window.
         */

        this.level = level;
        playerController = new PlayerController(this, pieceLogic, guiConfig);
        arena = new Arena(this);

        // ! YOU MUST PRELOAD IMAGES EARLIER THAN LOADING THE MODELS OF THE PIECES.
        GuiPieceImages.preLoadImages();

        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel());
        
        // Pane used for level transition
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(guiConfig.width, guiConfig.height));
        gamePanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(levelTransitionPanel, JLayeredPane.PALETTE_LAYER);
    
        mainFrame.add(layeredPane);
        mainFrame.setVisible(true);


        // loading piece models
        // ! MUST BE DONE AFTER IMAGE PRELOAD, see src.com.java.config.GuiPieceImages.java
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
                guiConfig.width = mainFrame.getWidth();
                // the -30 is due to the height of the top Panel in the gamePanel
                guiConfig.height = mainFrame.getHeight() - 30; // TODO don't hardcode like this
                guiConfig.updateArenaScale();

                for (Cell[] cells : gamePanel.getArenaPanel().getCells())
                    for (Cell cell : cells) cell.updateCell();
                gamePanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
                levelTransitionPanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
                if (!levelTransitionPanel.isTransitioning()){
                    levelTransitionPanel.setLocation(guiConfig.width, 0);
                }
            }
        });
        levelTransitionPanel.animationOnStart();
    }

    public Sound getSound() {
        return sound;
    }

    public int getArenaPanelY() {
        //! this is pretty ugly way of doing this
        return this.gamePanel.getArenaPanel().getY() + this.gamePanel.getArenaContainerPanel().getY();
    }

    /** 
     * updates the GUI
    */
    public void update() {
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons(); //TODO move this call to inside the gamePanel.update() function

        if (pieceLogic.checkWin()) {
            sound.playNote();
            levelTransitionPanel.animationAtEnd();
        }

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
        sound.playNote();
        this.clear();
        if (players == 2){
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }
        }
        Point location = mainFrame.getLocation();
        Launcher launcher = new Launcher(location);
        launcher.launch();
    }

    public GuiConfig getGuiConfig() {
        return guiConfig;
    }
}
