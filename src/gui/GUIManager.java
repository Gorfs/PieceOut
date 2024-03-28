package src.gui;

import src.controllers.PlayerController;
import src.gui.gamePlay.*;
import src.logic.LevelManager;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;

import java.awt.*;

import java.util.ArrayList;

/**
 * GUIManager class that is responsible for creating, changing,
 * updating and general management of the GUI.
 */
public class GUIManager {
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

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GUIManager(PieceLogic pieceLogic) {
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
        playerController = new PlayerController(this, pieceLogic);
        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel().getContainerPanel());

        mainFrame.add(gamePanel, "arena");
        mainFrame.setVisible(true);

        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }
        // TODO: Choose the id of the level to load (same in Arena.java -> line 11)
        LevelManager.loadPiecesFromJson(this.pieceLogic, 4, this);

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
