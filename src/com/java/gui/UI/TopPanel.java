package src.com.java.gui.UI;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.Arena;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class TopPanel extends JPanel{
    /**
     This panel contains the return to main menu button as well as the label
     which indicates current level
     */

    public TopPanel(InstanceManager guiManager){
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

        JLabel levelLabel = new JLabel("Level : " + guiManager.level);
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(titleFont);
        JButton returnButton = new JButton("Return to Main Menu");
        ImageIcon mainMenuIcon = new ImageIcon(new ImageIcon(GuiConfig.resourcePath + "icons/mainmenu.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        returnButton.setIcon(mainMenuIcon);
        returnButton.setBackground(Colours.BACKGROUND.darker());
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusable(false);
        returnButton.setFont(subTitleFont);

        returnButton.addActionListener(e -> {
            guiManager.returnToMainMenu();
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
