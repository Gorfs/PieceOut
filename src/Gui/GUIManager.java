package src.Gui;

import src.Gui.GamePlay.ArenaPanel;

import java.awt.*;

public class GUIManager {
    /**
     * GUIManager class that is responsible for creating, chainging,
     * updaing and general management of the GUI.
     */
    public static final GUIManager INSTANCE = new GUIManager();

    
    /**
     * mains panels and windows for the game
     */
    private GameWindow mainFrame = new GameWindow();
    private ArenaPanel arenaPanel = new ArenaPanel();

    // layout to change panels in rootPanel
    private CardLayout layout = new CardLayout();


    private GUIManager(){
        /**
         * init of instance
         */
        mainFrame.setLayout(layout);
    }

    public void start(){
        /**
         * initiates the GUI window and all.
         */
        mainFrame.add(arenaPanel, "arena");
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
        /**
         * changes the panel in the main frame to the panel with the name panelName
         */
        layout.show(mainFrame, panelName);
    }
}
