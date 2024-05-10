package src.com.java.gui.gamePlay;

import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import src.com.java.gui.InstanceManager;

/**
 * this class is responsible for the padding between the sides of the window and the arena itself
 */
public class ArenaContainerPanel extends JPanel {
    /**
     * Panel that contains the game area
     */
    private final ArenaPanel arenaPanel;

    /**
     * Constructor of the ArenaContainerPanel, it initializes the attributes and sets the background color
     * @param instanceManager Instance manager of the game we need to link to the player controller
     */
    public ArenaContainerPanel(InstanceManager instanceManager) {
        super();
        this.setBackground(src.com.java.config.Colours.BACKGROUND);
        this.arenaPanel = new ArenaPanel(instanceManager);
        // this.setMaximumSize(new Dimension(Integer.MAX_VALUE, arenaPanel.getPreferredSize().height));
        this.add(arenaPanel);
        // this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * @return Returns the arena panel
     */
    public ArenaPanel getArenaPanel() {
        return arenaPanel;
    }
}
