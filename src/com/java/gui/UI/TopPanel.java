package com.java.gui.UI;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.InstanceManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 This panel contains the return to main menu button as well as the label
 which indicates the current level
 */
public class TopPanel extends JPanel{
    /**
     * Label that shows the number of moves
     */
    private final JLabel movesLabel;
    /**
     * Button that mutes the music
     */
    private final JButton muteMusicButton;
    /**
     * Button that mutes the sound effects
     */
    private final JButton muteSoundEffects;

    /**
     * Constructor for the TopPanel class
     * @param iManager InstanceManager instance that manages the game
     */
    public TopPanel(InstanceManager iManager){
        super();
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Font titleFont;
        Font subTitleFont;

        try{
            File fontFile = new File(GuiConfig.resPath + "fonts/Lentariso-Bd.ttf");
            titleFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);
            subTitleFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 14f);
        }
        catch (Exception e) {
            System.out.println("Error in TopPanel: \nFont not found, file either not found or doesn't exist.");
            // If the font is not found, we use the default font
            titleFont = new Font("Arial", Font.BOLD, 30);
            subTitleFont = new Font("Arial", Font.BOLD, 14);
        }
        String path = GuiConfig.resPath + "icons/mainmenu.png";
        Image mainMenuImage = new ImageIcon(path).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon mainMenuIcon = new ImageIcon(mainMenuImage);

        JLabel levelLabel = new JLabel("Level : " + iManager.level + 1);
        JButton returnButton = new JButton("Menu");
        movesLabel = new JLabel("Moves : " + iManager.getNumMoves());
        muteMusicButton = new JButton("Music");
        muteSoundEffects = new JButton("Sound");

        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(titleFont);

        movesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        movesLabel.setForeground(Color.WHITE);
        levelLabel.setFont(subTitleFont);

        returnButton.setIcon(mainMenuIcon);
        returnButton.setBackground(Colours.BACKGROUND.darker());
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusable(false);
        returnButton.setFont(subTitleFont);
        returnButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        returnButton.setHorizontalTextPosition(SwingConstants.CENTER);

        returnButton.addActionListener(e -> iManager.returnToMainMenu());

        muteMusicButton.setIcon(getMusicIcon(iManager));
        muteMusicButton.setBackground(Colours.BACKGROUND.darker());
        muteMusicButton.setForeground(Color.WHITE);
        muteMusicButton.setFocusable(false);
        muteMusicButton.setFont(subTitleFont);
        muteMusicButton.addActionListener(e -> {
            iManager.getSoundManager().toggleMute();
            muteMusicButton.setIcon(getMusicIcon(iManager));
        });

        muteSoundEffects.setIcon(getSoundEffectsIcon(iManager));
        muteSoundEffects.setBackground(Colours.BACKGROUND.darker());
        muteSoundEffects.setForeground(Color.WHITE);
        muteSoundEffects.setFocusable(false);
        muteSoundEffects.setFont(subTitleFont);
        muteSoundEffects.addActionListener(e -> {
            iManager.getSoundManager().toggleSoundEffects();
            muteSoundEffects.setIcon(getSoundEffectsIcon(iManager));
        });

        this.setBorder(BorderFactory.createEmptyBorder(3,3,3,20));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(returnButton);
        this.add(Box.createHorizontalGlue());
        this.add(levelLabel);
        this.add(Box.createHorizontalGlue());
        this.add(movesLabel);
        this.add(Box.createHorizontalGlue());
        this.add(muteMusicButton);
        this.add(muteSoundEffects);

    }

    /**
     * Method that returns the music icon
     * @param instanceManager InstanceManager instance that manages the game
     * @return ImageIcon of the music icon
     */
    ImageIcon getMusicIcon(InstanceManager instanceManager){
        boolean isMusicMuted = instanceManager.getSoundManager().isMusicMuted();
        String path = GuiConfig.resPath + (isMusicMuted ? "icons/musicoff.png" : "icons/musicon.png");
        Image musicImage = new ImageIcon(path).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(musicImage);
    }

    /**
     * Method that returns the sound effects icon
     * @param instanceManager InstanceManager instance that manages the game
     * @return ImageIcon of the sound effects icon
     */
    ImageIcon getSoundEffectsIcon(InstanceManager instanceManager){
        boolean isSoundEffectsMuted = instanceManager.getSoundManager().isSoundEffectsMuted();
        String path = GuiConfig.resPath + (isSoundEffectsMuted ? "icons/soundoff.png" : "icons/soundon.png");
        Image soundEffImage = new ImageIcon(path).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(soundEffImage);
    }

    /**
     * Method that updates the number of moves
     * @param instanceManager InstanceManager instance that manages the game
     */
    public void updateCount(InstanceManager instanceManager){
        movesLabel.setText("Moves : " + instanceManager.getNumMoves());
    }
}
