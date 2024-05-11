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
 * InstanceManager class that is responsible for creating, changing,
 * updating and general management of the GUI.
 */
public class InstanceManager {
    /**
     * main frame and window of the game
     */
    private final GameWindow mainFrame = new GameWindow();
    /**
     * Game panel of the game seen by the player
     */
    private GamePanel gamePanel;

    /**
     * layout to change panels in rootPanel
     */
    private final CardLayout layout = new CardLayout();
    /**
     * Panel that contains the level transition panel, that is shown when the level is completed
     */
    private final LevelTransitionPanel levelTransitionPanel = new LevelTransitionPanel();

    /**
     * List of the pieces in the GUI
     */
    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    /**
     * List of the targets in the GUI
     */
    private ArrayList<GuiTarget> guiTargets = new ArrayList<GuiTarget>();
    /**
     * Piece logic of the game, where the pieces are stocked
     */
    private final PieceLogic pieceLogic;
    /**
     * Player controller of the game, that manages the player actions
     */
    public PlayerController playerController;
    /**
     * Arena of the game, where the pieces are placed
     */
    public Arena arena;

    /**
     * level of the puzzle we are currently playing
     */
    public int level;
    private int players;
    private InstanceManager secondPlayer;


    /**
     * Constructor of the InstanceManager
     * @param pieceLogic the piece logic of the game
     */
    public InstanceManager(PieceLogic pieceLogic, Point location) {
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = this;
        players = 1;
    }

    public InstanceManager(PieceLogic pieceLogic, Point location, InstanceManager secondPlayer) {
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = secondPlayer;
        players = 2;
    }

    /**
     * Initiates the GUI window.
     * It creates the game panel, the player controller, the arena and the pieces and targets
     * @param level the level of the puzzle we are currently playing
     *              (0 for the first level, 1 for the second, etc.)
     */
    public void start(int level) {
        // Initialize the level and the player controller
        this.level = level;
        // Initialize the player controller
        playerController = new PlayerController(this, pieceLogic);
        // Initialize the arena
        this.arena = new Arena(this);
        // Initialize the game panel
        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel());
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(GuiConfig.width, GuiConfig.height));
        gamePanel.setBounds(0, 0, GuiConfig.width, GuiConfig.height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(levelTransitionPanel, JLayeredPane.PALETTE_LAYER);
    
        mainFrame.add(layeredPane);
        mainFrame.setVisible(true);

        // Load the pieces from the json file
        LevelManager.loadPiecesFromJson(this.pieceLogic, level, this);

        // paint the targets before the pieces so that the pieces are drawn on top
        for (Target target : this.pieceLogic.getTargets()) {
            GuiTarget guiTarget = new GuiTarget(this, target);
            guiTargets.add(guiTarget);
        }
        // paint the pieces on the game
        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
        }
        // Listener for resizing the window
        this.mainFrame.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes. This method is used to update the size of the arena
             * @param componentEvent the event to be processed by the listener
             */
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
        levelTransitionPanel.animationOnStart(this);
    }

    /** 
     * updates the GUI
    */
    public void update() {
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons(); //TODO move this call to inside the gamePanel.update() function

        if (pieceLogic.checkWin()) levelTransitionPanel.animationAtEnd(this);
    }

    /**
     * Method that is called when the level is completed
     */
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
     * Method used to clear the GUI
     */
    private void clear(){
        this.pieceLogic.clearPieces();
        this.pieceLogic.clearTargets();
        this.guiPieces.clear();
        this.guiTargets.clear();
        // close window
        this.mainFrame.dispose();
    }

    /**
     * changes the panel in the main frame to the panel with the name panelName
     */
    public void changePanel(String panelName) {
        layout.show(mainFrame, panelName);
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
    /**
     * @return the game panel
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * @return the piece logic of the game, where the pieces are stocked
     */
    public PieceLogic getPieceLogic() {
        return pieceLogic;
    }

    /**
     * @return the height of the main frame
     */
    public int getHeight() {
        return this.mainFrame.getHeight();
    }

    /**
     * @return the width of the game panel
     */
    public int getArenaPanelY() {
        return this.gamePanel.getArenaPanel().getY() + this.gamePanel.getArenaContainerPanel().getY();
    }

    /**
     * @return if the level transition panel is transitioning
     */
    public boolean isTransitioning() {
        return levelTransitionPanel.isTransitioning();
    }
}
