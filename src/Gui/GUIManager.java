package src.Gui;

import src.Gui.GamePlay.ArenaPanel;
import src.Gui.GamePlay.GamePanel;

import java.awt.*;

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


    private GUIManager(){
        /*
          init of instance
         */
        mainFrame.setLayout(layout);
    }

    public void start(){
        /*
          initiates the GUI window and all.
         */
        mainFrame.add(gamePanel, "arena");
        mainFrame.setVisible(true);
    }

    
    /** 
     * @return GUIManager
     * returns the instance of the GUIManager.
     */
    public static GUIManager getInstance(){
        return INSTANCE;
    }


    public void changePanel(String panelName){
        /*
          changes the panel in the main frame to the panel with the name panelName
         */
        layout.show(mainFrame, panelName);
    }
}
