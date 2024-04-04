package src.com.java.gui.gamePlay;

import javax.swing.*;

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
        this.add(arenaPanel);
    }

    public ArenaPanel getArenaPanel() {
        return arenaPanel;
    }
    
}
