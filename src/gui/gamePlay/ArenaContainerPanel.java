package src.gui.gamePlay;

import javax.swing.*;

import src.gui.InstanceManager;

public class ArenaContainerPanel extends JPanel {
    /**
     * this class is responsible for the padding between the sides of the window and the arena itself
     */

    private ArenaPanel arenaPanel;

    public ArenaContainerPanel(InstanceManager instanceManager) {
        super();
        this.setBackground(src.config.Colours.BACKGROUND);
        this.arenaPanel = new ArenaPanel(instanceManager);
        this.add(arenaPanel);
    }

    public ArenaPanel getArenaPanel() {
        return arenaPanel;
    }
    
}
