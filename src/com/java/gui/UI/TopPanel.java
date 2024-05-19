package com.java.gui.UI;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.InstanceManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TopPanel extends JPanel{
    /**
     This panel contains the return to main menu button as well as the label
     which indicates current level
     */
    JLabel levelLabel;
    JButton returnButton;
    JLabel movesLabel;

    public TopPanel(InstanceManager instanceManager){
        super();
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Font titleFont = null;
        Font subTitleFont = null;

        try{
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(GuiConfig.resourcePath + "fonts/Lentariso-Bd.ttf")).deriveFont(Font.BOLD, 30f);
            subTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File(GuiConfig.resourcePath + "fonts/Lentariso-Bd.ttf")).deriveFont(Font.BOLD, 14f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ImageIcon mainMenuIcon = new ImageIcon(new ImageIcon(GuiConfig.resourcePath + "icons/mainmenu.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        levelLabel = new JLabel("Level : " + (instanceManager.level + 1));
        returnButton = new JButton("Return to Main Menu");
        movesLabel = new JLabel("Moves : " + instanceManager.getNumMoves());


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

        returnButton.addActionListener(e -> {
            instanceManager.returnToMainMenu();
        });


        this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(returnButton);
        this.add(Box.createHorizontalGlue());
        this.add(levelLabel);
        this.add(Box.createHorizontalGlue());
        this.add(movesLabel);
        this.add(Box.createHorizontalGlue());
    }


    public void updateCount(InstanceManager instanceManager){
        movesLabel.setText("Moves : " + instanceManager.getNumMoves());
    }
}
