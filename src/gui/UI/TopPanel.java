package src.gui.UI;

import src.config.Colours;
import src.config.GuiConfig;
import src.logic.Arena;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel{
    /**
     This panel contains the return to main menu button as well as the label
     which indicates current level
     */

    public TopPanel(){
        super();
        this.setOpaque(false);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel levelLabel = new JLabel("Level : " + Arena.getInstance().getLevel());
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        levelLabel.setForeground(Color.WHITE);
        JButton returnButton = new JButton("Return to Main Menu");
        returnButton.setBackground(Colours.BACKGROUND.darker());
        returnButton.setForeground(Color.WHITE);
        returnButton.setFocusable(false);



        this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        this.setMinimumSize(new Dimension(Integer.MAX_VALUE, 30));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(returnButton);
        //TODO set the rigid area to a dynamic size and not just divided by 5
        this.add(Box.createRigidArea(new Dimension(GuiConfig.WINDOW_WIDTH / 5, 0)));
        this.add(levelLabel);
    }
}
