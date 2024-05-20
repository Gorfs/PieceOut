package com.java.gui;

import com.java.config.GuiConfig;
import com.java.config.GuiPieceImages;
import com.java.config.LogicConstants;
import com.java.controllers.PlayerController;
import com.java.geometry.IntCoordinates;
import com.java.gui.UI.Launcher;
import com.java.gui.UI.LevelTransitionPanel;
import com.java.gui.gamePlay.*;
import com.java.gui.sound.SoundManager;
import com.java.logic.Arena;
import com.java.logic.LevelManager;
import com.java.logic.PieceLogic;
import com.java.piece.Target;
import com.java.piece.decorator.Decorator;
import com.java.utils.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * InstanceManager class that is responsible for creating, changing,
 * updating and general management of the GUI.
 */
public class InstanceManager {
    /**
     * Main frame and window of the game.
     */
    private final GameWindow mainFrame = new GameWindow();
    /**
     * Game panel of the game seen by the player.
     */
    private GamePanel gamePanel;
    /**
     * Configuration of the GUI for the instance.
     */
    private final GuiConfig guiConfig = new GuiConfig();
    /**
     * Layout to change panels in rootPanel.
     */
    private final CardLayout layout = new CardLayout();
    /**
     * Panel that contains the level transition panel, that is shown when the level is completed.
     */
    private final LevelTransitionPanel levelTransitionPanel = new LevelTransitionPanel();
    /**
     * Layered pane that contains the game panel and the level transition panel.
     */
    private final JLayeredPane layeredPane = new JLayeredPane();
    /**
     * Piece logic of the game, where the pieces are stocked.
     */
    private final PieceLogic pieceLogic;
    /**
     * Player controller of the game, that manages the player actions.
     */
    private PlayerController playerController;
    /**
     * Arena of the game, where the pieces are placed.
     */
    private Arena arena;
    /**
     * Sound of the game.
     */
    private final SoundManager soundManager;
    /**
     * level of the puzzle we are currently playing.
     */
    public int level;
    /**
     * Number of players in the game.
     */
    private final int players;
    /**
     * Instance manager of the second player.
     */
    private final InstanceManager secondPlayer;
    /**
     * Number of moves made by the player.
     */
    private int numMoves = 0;
    /**
     * Minimum number of moves to complete the level.
     */
    private int minNumMoves ;

    /**
     * Constructor of the InstanceManager.
     * It initializes the main frame, the layout, the piece logic of the game and the sound manager.
     * @param pieceLogic the piece logic of the game
     */
    public InstanceManager(PieceLogic pieceLogic, Point location, SoundManager sound) {
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = this;
        soundManager = sound;
        players = 1;
    }

    /**
     * Constructor of the InstanceManager.
     * It initializes the main frame, the layout, the piece logic of the game,
     * the location of the window and the instance manager of the second player.
     * @param pieceLogic the piece logic of the game
     * @param location the location of the window
     * @param secondPlayer the instance manager of the second player
     */
    public InstanceManager(PieceLogic pieceLogic, Point location, SoundManager sound, InstanceManager secondPlayer) {
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        mainFrame.setLocation(location);
        this.secondPlayer = secondPlayer;
        soundManager = sound;
        players = 2;
    }

    /**
     * Initiates the GUI window.
     * It creates the game panel, the player controller, the arena and the pieces and targets.
     * @param level the level of the puzzle we are currently playing
     *              (0 for the first level, 1 for the second, etc.)
     */
    public void start(int level) {
        // Initialize the level and the player controller
        this.level = level;
        // Get the minimum move for the level
        this.minNumMoves = LevelManager.NumMovesFromJson(level);
        // Initialize the player controller
        playerController = new PlayerController(this, pieceLogic, guiConfig);
        // Initialize the arena
        this.arena = new Arena(this);
        // ! YOU MUST PRELOAD IMAGES EARLIER THAN LOADING THE MODELS OF THE PIECES.
        GuiPieceImages.preLoadImages();
        // Initialize the game panel
        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel());
        
        // Pane used for level transition
        layeredPane.setPreferredSize(new Dimension(guiConfig.width, guiConfig.height));
        gamePanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(levelTransitionPanel, JLayeredPane.PALETTE_LAYER);
    
        mainFrame.add(layeredPane);
        mainFrame.setVisible(true);

        // !MUST BE DONE AFTER IMAGE PRELOAD, see com.java.config.GuiPieceImages.java
        // Load the pieces from the json file
        LevelManager.loadPiecesFromJson(this.pieceLogic, level);

        // paint the targets before the pieces so that the pieces are drawn on top
        for (Target target : this.pieceLogic.getTargets()) {
            new GuiTarget(this, target);
        }
        // paint the pieces on the game
        for (Decorator piece : this.pieceLogic.getPieces()) {
            new GuiPiece(this, piece);
        }
        // Listener for resizing the window
        this.mainFrame.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes. This method is used to update the size of the arena.
             * @param componentEvent the event to be processed by the listener
             */
            public void componentResized(ComponentEvent componentEvent) {
                guiConfig.width = mainFrame.getWidth();
                // the -30 is due to the height of the top Panel in the gamePanel
                guiConfig.height = mainFrame.getHeight() - getGamePanel().getBottomPanel().getHeight()/2;
                guiConfig.updateArenaScale();

                for (Cell[] cells : gamePanel.getArenaPanel().getCells())
                    for (Cell cell : cells) cell.updateCell();
                gamePanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
                levelTransitionPanel.setBounds(0, 0, guiConfig.width, guiConfig.height);
                if (!isTransitioning()){
                    levelTransitionPanel.setLocation(guiConfig.width, 0);
                }
            }
        });
        levelTransitionPanel.animationOnStart(this);
        Debug.out("NumMoves: " + numMoves + " minNumMoves: " + minNumMoves);
    }

    /**
     * returns the pixel coordinates of the cell relative to the top left of the window.
     * @param cellCoords Coordinates of the cell int the game
     * @return IntCoordinates of the cell in pixels
     */
    public IntCoordinates getPixelCoordinates(IntCoordinates cellCoords) {
        ArenaPanel arenaPanel = this.getGamePanel().getArenaPanel();
        ArenaContainerPanel arenaContainerPanel = this.getGamePanel().getArenaContainerPanel();
        try {
            Cell[][] cells = arenaPanel.getCells();
            Cell cell = cells[cellCoords.getY()][cellCoords.getX()];

            int xOffSet = arenaContainerPanel.getX() + arenaPanel.getX();
            int yOffSet = arenaContainerPanel.getY() + arenaPanel.getY();

            return new IntCoordinates(cell.getX() + xOffSet, cell.getY() + yOffSet);
        } catch (Exception e) {
            Debug.out("Error in getPixelCoordinates : " + e.getMessage());
            return new IntCoordinates(-100, 0);
        }
    }

    /** 
     * updates the GUI of the game.
    */
    public void update() {
        gamePanel.update();

        if (pieceLogic.checkWin()) {
            playerController.stopController(gamePanel.getArenaPanel());
            soundManager.playNote();
            LevelManager.unlockLevel(level);
            Debug.out("Result : " + rating() + " stars!");
            levelTransitionPanel.showRating(rating(), layeredPane, guiConfig);
            levelTransitionPanel.animationAtEnd(this);
        }

    }

    /**
     * Method that is called when the level is completed and switches to the next level.
     */
    public void nextLevel() {
        if (level + 1 != LogicConstants.NUMBER_OF_LEVELS){
            Debug.out("You win!");
            Point location = mainFrame.getLocation();
            InstanceManager next = new InstanceManager(new PieceLogic(), location, soundManager, secondPlayer);
            level++;
            next.start(level);
            this.clear();
        }
        else{
            this.returnToMainMenu();
        }

    }

    /**
     * @return the rating of the player based on the number of moves made.
     */
    public int rating() {
        if (numMoves <= minNumMoves) {
            return 3;
        } else if (numMoves <= minNumMoves * 1.5) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Method used to clear the GUI.
     */
    private void clear(){
        this.pieceLogic.clearPieces();
        this.pieceLogic.clearTargets();
        // close window
        this.mainFrame.dispose();
    }

    /**
     * Method that is called when the player wants to return to the main menu.
     */
    public void returnToMainMenu() {
        if (secondPlayer == null && players == 2) {
            return;
        }
        soundManager.playNote();
        this.clear();
        if (players == 2){
            for (Frame frame : Frame.getFrames()) {
                frame.dispose();
            }
        }
        Point location = mainFrame.getLocation();
        Launcher launcher = new Launcher(location, soundManager);
        launcher.launch();
    }

    /**
     * Method that is called when the level is completed.
     */
    public boolean isTransitioning() {
        return levelTransitionPanel.isTransitioning();
    }

    /**
     * @return the game panel
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Increments the number of moves made by the player.
     */
    public void incMoves() {
        numMoves++;
    }

    /**
     * Decrements the number of moves made by the player.
     */
    public void decMoves() {
        numMoves--;
    }

    /**
     * @return the number of moves made by the player
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * @return the configuration of the GUI
     */
    public GuiConfig getGuiConfig() {
        return guiConfig;
    }
    /**
     * @return the height of the main frame
     */
    public int getHeight() {
        return this.mainFrame.getHeight();
    }

    /**
     * @return the width of the main frame
     */
    public PlayerController getPlayerController() {
        return playerController;
    }

    /**
     * @return the arena of the game
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * @return the sound manager of the game
     */
    public SoundManager getSoundManager() {
        return soundManager;
    }
}
