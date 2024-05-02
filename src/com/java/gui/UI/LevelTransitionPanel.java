package src.com.java.gui.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;

public class LevelTransitionPanel extends JPanel{
    
    private boolean isTransitioning = false;
    private final InstanceManager instanceManager;

    public LevelTransitionPanel(InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
        this.setBackground(Colours.BACKGROUND);
        this.setBounds(0, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
    }

    public void animationOnStart() {
        isTransitioning = true;
        Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = LevelTransitionPanel.this.getLocation().x;
                x += 40;
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

    public void animationAtEnd() {
        setLocation(-GuiConfig.WINDOW_WIDTH, 0);
        Timer timer1 = new Timer(10, null);
        timer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = getLocation().x;
                x += 40;
                if (x >= 0) {
                    x = 0;
                    timer1.stop();

                    Timer timer2 = new Timer(200, null);
                    timer2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            timer2.stop();
                            instanceManager.nextLevel();
                        }
                    });
                    timer2.setRepeats(false);
                    timer2.start();
                }
                setLocation(x, 0);
            }
        });
        timer1.start();

    }

    public boolean isTransitioning() {
        return isTransitioning;
    }
}
