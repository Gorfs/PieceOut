package src.com.java.gui.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;

public class LevelTransitionPanel extends JPanel{
    
    private boolean isTransitioning = false;
    private final InstanceManager instanceManager;

    public LevelTransitionPanel(InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
        this.setBackground(Color.BLACK);
        this.setBounds(0, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
    }

    public void animationOnStart() {
        isTransitioning = true;
        Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = LevelTransitionPanel.this.getLocation().x;
                x += 10;
                if (x >= GuiConfig.WINDOW_WIDTH) {
                    x = GuiConfig.WINDOW_WIDTH;
                    isTransitioning = false;
                    instanceManager.update();
                    timer.stop();
                }
                LevelTransitionPanel.this.setLocation(x, 0);
            }
        });
        timer.start();
    }

    public boolean isTransitioning() {
        return isTransitioning;
    }
}
