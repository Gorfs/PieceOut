package com.java.gui.gamePlay;

import com.java.gui.InstanceManager;

import javax.swing.*;


/**
 * this class is responsible for the padding between the sides of the window and the arena itself.
 */
public class ArenaContainerPanel extends JPanel {
    /**
     * Panel that contains the game area.
     */
    private final ArenaPanel arenaPanel;

    /**
     * Constructor of the ArenaContainerPanel, it initializes the attributes and sets the background color.
     * @param instanceManager Instance manager of the game we need to link to the player controller
     */
    public ArenaContainerPanel(InstanceManager instanceManager) {
        super();
        this.setBackground(com.java.config.Colours.BACKGROUND);
        this.arenaPanel = new ArenaPanel(instanceManager);
        this.add(arenaPanel);
    }

    /**
     * @return Returns the arena panel
     */
    public ArenaPanel getArenaPanel() {
        return arenaPanel;
    }
}
