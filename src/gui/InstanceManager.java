package src.gui;

import src.config.GuiConfig;
import src.controllers.PlayerController;
import src.gui.gamePlay.*;
import src.logic.Arena;
import src.logic.LevelManager;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;
import src.gui.gamePlay.Cell;

import java.awt.*;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

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

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    private final PieceLogic pieceLogic;
    public PlayerController playerController;
    public Arena arena;

    public int level;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InstanceManager(PieceLogic pieceLogic) {
        /*
         * init of instance
         */
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
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

        mainFrame.add(gamePanel, "arena");
        mainFrame.setVisible(true);

        LevelManager.loadPiecesFromJson(this.pieceLogic, level, this);

        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }

        this.mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                GuiConfig.width = mainFrame.getWidth();
                GuiConfig.height = mainFrame.getHeight();
                GuiConfig.updateArenaScale();

                for (Cell[] cells : gamePanel.getArenaPanel().getCells())
                    for (Cell cell : cells) cell.updateCell();
            }
        });
    }

    public int getArenaPanelY() {
        return this.gamePanel.getArenaPanel().getY() + this.gamePanel.getArenaContainerPanel().getY();
    }

    /** 
     * updates the GUI
    */
    public void update() {
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons();

    }

    /*
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
}
