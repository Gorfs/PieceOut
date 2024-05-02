package src.com.java.gui.gamePlay;

import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;

import src.com.java.gui.InstanceManager;

public class ArenaContainerPanel extends JPanel {
    /**
     * this class is responsible for the padding between the sides of the window and the arena itself
     */

    private ArenaPanel arenaPanel;

    public ArenaContainerPanel(InstanceManager instanceManager) {
        super();
        this.setBackground(src.com.java.config.Colours.BACKGROUND);
        this.arenaPanel = new ArenaPanel(instanceManager);
        // this.setMaximumSize(new Dimension(Integer.MAX_VALUE, arenaPanel.getPreferredSize().height));
        this.add(arenaPanel);
        // this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public ArenaPanel getArenaPanel() {
        return arenaPanel;
    }
    
}
