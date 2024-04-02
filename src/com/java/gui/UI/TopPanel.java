package src.com.java.gui.UI;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.Arena;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel{
    /**
     This panel contains the return to main menu button as well as the label
     which indicates current level
     */

    public TopPanel(InstanceManager guiManager){
        super();
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel levelLabel = new JLabel("Level : " + guiManager.level);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        levelLabel.setForeground(Color.WHITE);
        JButton returnButton = new JButton("Return to Main Menu");
        ImageIcon mainMenuIcon = new ImageIcon(new ImageIcon(GuiConfig.iconPath + "mainmenu.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        returnButton.setIcon(mainMenuIcon);
        returnButton.setBackground(Colours.BACKGROUND.darker());
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusable(false);

        returnButton.addActionListener(e -> {
            // TODO remove testing button and make it return to main menu
            guiManager.update();
        });


        this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(returnButton);
        //TODO set the rigid area to a dynamic size and not just divided by 5
        this.add(Box.createRigidArea(new Dimension(GuiConfig.WINDOW_WIDTH / 5, 0)));
        this.add(levelLabel);
    }
}
