package src.gui;

import src.controllers.PlayerController;
import src.gui.gamePlay.*;
import src.logic.Arena;
import src.logic.LevelManager;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;

import java.awt.*;

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
        playerController.startController(gamePanel.getArenaPanel().getContainerPanel());

        mainFrame.add(gamePanel, "arena");
        mainFrame.setVisible(true);

        LevelManager.loadPiecesFromJson(this.pieceLogic, level, this);

        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }


    }

    public int getArenaPanelY() {
        return gamePanel.getArenaPanel().getY();
    }

    public void update() {
        /*
         * updates the GUI
         */
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons();

    }

    public void changePanel(String panelName) {
        /*
         * changes the panel in the main frame to the panel with the name panelName
         */
        layout.show(mainFrame, panelName);
    }

    public PieceLogic getPieceLogic() {
        return pieceLogic;
    }
}
