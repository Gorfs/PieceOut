package src.gui;

import src.controllers.PlayerController;
import src.gui.gamePlay.*;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;
import src.utils.Debug;

import java.awt.*;

import java.util.ArrayList;

public class GUIManager {
    /**
     * GUIManager class that is responsible for creating, changing,
     * updating and general management of the GUI.
     */
    public static final GUIManager INSTANCE = new GUIManager();

    
    /**
     * mains panels and windows for the game
     */
    private final GameWindow mainFrame = new GameWindow();
    private final GamePanel gamePanel = new GamePanel();

    // layout to change panels in rootPanel
    private final CardLayout layout = new CardLayout();

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();

    public GamePanel getGamePanel(){
        return gamePanel;
    }


    private GUIManager(){
        /*
          init of instance
         */
        mainFrame.setLayout(layout);
    }

    public void start(){
        /*
          initiates the GUI window.
         */
        mainFrame.add(gamePanel, "arena");
        mainFrame.setVisible(true);
        new PlayerController(gamePanel.getArenaPanel().getContainerPanel());

        for(Decorator piece: PieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }

    }

    public int getArenaPanelY(){
        return gamePanel.getArenaPanel().getY();
    }


    
    /** 
     * @return GUIManager
     * returns the instance of the GUIManager.
     */
    public static GUIManager getInstance(){
        return INSTANCE;
    }

    public void update(){
        /*
          updates the GUI
         */
        gamePanel.update();
        // Debug.out("updating " + PieceLogic.getPieces().size());

    }
    
    


    public void changePanel(String panelName){
        /*
          changes the panel in the main frame to the panel with the name panelName
         */
        layout.show(mainFrame, panelName);
    }
}
