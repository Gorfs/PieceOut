package com.java.gui.UI;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.InstanceManager;
import com.java.utils.Debug;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class LevelTransitionPanel extends JPanel{

    /**
     * Boolean that indicates if the panel is transitioning
     */
    private boolean isTransitioning = false;

    /**
     * Constructor for the LevelTransitionPanel class
     * It sets the background color and the bounds of the panel
     */
    public LevelTransitionPanel() {
        this.setBounds(0, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
        this.setBackground(Colours.BACKGROUND);
    }

    /**
     * This method is called when the level begins
     * It moves the panel to the right
     */
    public void animationOnStart(InstanceManager iManager) {
        isTransitioning = true;
        iManager.getSoundManager().playNote();
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            int x = LevelTransitionPanel.this.getLocation().x;
            x += 30;
            if (x >= GuiConfig.WINDOW_WIDTH) {
                x = GuiConfig.WINDOW_WIDTH;
                isTransitioning = false;
                LevelTransitionPanel.this.setVisible(false);
                iManager.update();
                timer.stop();
            }
            LevelTransitionPanel.this.setLocation(x, 0);
        });
        timer.start();

    }

    /**
     * Method that animates the panel when the level is completed
     * It moves the panel to the left to the center of the screen
     * Then it waits for 0.2 seconds and calls the next level
     */
    public void animationAtEnd(InstanceManager iManager) {
        setLocation(-GuiConfig.WINDOW_WIDTH, 0);
        setVisible(true);


        Timer timer1 = new Timer(10, null);
        timer1.addActionListener(e -> {
            int x = getLocation().x;
            x += 40;
            if (x >= 0) {
                x = 0;
                timer1.stop();

                Timer timer2 = new Timer(1000, null);
                timer2.addActionListener(e1 -> {
                    timer2.stop();
                    iManager.nextLevel();
                });
                timer2.setRepeats(false);
                timer2.start();
            }
            setLocation(x, 0);
        });
        timer1.start();
    }

    /**
     * Method that returns if the panel is transitioning
     * @return true if the panel is transitioning
     */
    public boolean isTransitioning() {
        return isTransitioning;
    }

    public void showRating(int rating, JLayeredPane layeredPane, GuiConfig guiConfig) {
        BufferedImage starsImage = null;
        switch (rating) {
            case 0:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resPath + "icons/0stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 1:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resPath + "icons/1stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 2:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resPath + "icons/2stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;
            case 3:
                try{
                    starsImage = ImageIO.read(new File(GuiConfig.resPath + "icons/3stars.png"));
                }
                catch (Exception e){
                    Debug.out("Error loading stars image");
                }
                break;

        }
        assert starsImage != null;
        JLabel starsLabel = new JLabel(new ImageIcon(starsImage));
        starsLabel.setBounds(0, 0, guiConfig.width, guiConfig.height);
        layeredPane.add(starsLabel, JLayeredPane.MODAL_LAYER);
    }
}
