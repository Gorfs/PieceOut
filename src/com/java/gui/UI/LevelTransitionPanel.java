package com.java.gui.UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.java.utils.Debug;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.InstanceManager;

public class LevelTransitionPanel extends JPanel{
    
    private boolean isTransitioning = false;
    private final InstanceManager instanceManager;

    public LevelTransitionPanel(InstanceManager instanceManager) {
        this.instanceManager = instanceManager;
        this.setBackground(Colours.BACKGROUND);
        this.setBounds(0, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
    }

    /*
     * This method is called when the level begins
     * It moves the panel to the right
     */

    public void animationOnStart() {
        isTransitioning = true;
        instanceManager.getSound().playNote();
        Timer timer = new Timer(10, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = LevelTransitionPanel.this.getLocation().x;
                x += 30;
                if (x >= GuiConfig.WINDOW_WIDTH) {
                    x = GuiConfig.WINDOW_WIDTH;
                    isTransitioning = false;
                    LevelTransitionPanel.this.setVisible(false);
                    instanceManager.update();
                    timer.stop();
                }
                LevelTransitionPanel.this.setLocation(x, 0);
            }
        });
        timer.start();

    }

    /*
     * This method is called when the level is completed
     * It moves the panel to the left to the center of the screen
     * Then it waits for 0.2 seconds and calls the next level
     */

    public void animationAtEnd() {
        setLocation(-GuiConfig.WINDOW_WIDTH, 0);
        setVisible(true);


        Timer timer1 = new Timer(10, null);
        timer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = getLocation().x;
                x += 40;
                if (x >= 0) {
                    x = 0;
                    timer1.stop();

                    Timer timer2 = new Timer(1000, null);
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

    public void showRating(int rating, JLayeredPane layeredPane, GuiConfig guiConfig) {
        BufferedImage starsImage = null;
        switch (rating) {
            case 0:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resourcePath + "icons/0stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 1:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resourcePath + "icons/1stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 2:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resourcePath + "icons/2stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 3:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resourcePath + "icons/3stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;

        }
        JLabel starsLabel = new JLabel(new ImageIcon(starsImage));
        starsLabel.setBounds(0, 0, guiConfig.width, guiConfig.height);
        layeredPane.add(starsLabel, JLayeredPane.MODAL_LAYER);
    }
}
