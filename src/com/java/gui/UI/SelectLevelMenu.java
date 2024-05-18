package src.com.java.gui.UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.GameWindow;
import src.com.java.gui.InstanceManager;
import src.com.java.gui.sound.Sound;
import src.com.java.logic.PieceLogic;

public class SelectLevelMenu {
    private final SelectLevelPanel selectLevelPanel;
    private final GameWindow mainFrame;
    private final CardLayout layout;
    private final int players;
    private final Sound sound = new Sound();

    public SelectLevelMenu(Point location, int players) {
        mainFrame = new GameWindow();
        selectLevelPanel = new SelectLevelPanel();
        layout = new CardLayout();
        mainFrame.setLayout(layout);
        mainFrame.setLocation(location);
        this.players = players;
    }

    /*
     * This method is called to initialize the launcher panel and make it visible
     */
    public void launch(){
        mainFrame.add(selectLevelPanel, "selectLevel");
        mainFrame.setVisible(true);
    }


    /*
     * This method is called to start the game with the number of players specified
     */
    public void startGame(int players, int level){
        //TODO: implement the logic to start the game with the number of players specified
        mainFrame.dispose();
        level--;
        if (players == 1) {
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location);
            instanceManager.start(level);
        }
        else{
            Point location2 = mainFrame.getLocation();
            location2.setLocation(location2.getX() + GuiConfig.WINDOW_WIDTH + 10, location2.getY());
            InstanceManager instanceManager2 = new InstanceManager(new PieceLogic(), location2, null);
            instanceManager2.start(level);
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location, instanceManager2);
            instanceManager.start(level);
        }
    }
    
    
    class SelectLevelPanel extends JPanel {

        private final Sound sound = new Sound();
        

        /*
         * This panel contains the title image and the buttons to start the game with 1 or 2 players or to exit the game
         */

        public SelectLevelPanel() {
            this.setBackground(Colours.BACKGROUND);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            sound.playOST();

            Font buttonFont = null;
            Font titleFont = null;

            try{
                buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File(GuiConfig.resourcePath + "fonts/Lentariso-Bd.ttf")).deriveFont(Font.BOLD,30f);
                titleFont = Font.createFont(Font.TRUETYPE_FONT, new File(GuiConfig.resourcePath + "fonts/Lentariso-Bd.ttf")).deriveFont(Font.BOLD, 50f);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new GridBagLayout());
            titlePanel.setOpaque(false);
            JLabel title = new JLabel("Select a start level", JLabel.CENTER);
            title.setFont(titleFont);
            title.setForeground(Color.WHITE);

            GridBagConstraints titleGbc = new GridBagConstraints();
            titleGbc.gridwidth = 2;
            titleGbc.anchor = GridBagConstraints.NORTH;
            titleGbc.gridy = 0;
            titleGbc.weighty = 0;
            titleGbc.insets.top = 50;
            titlePanel.add(title, titleGbc);
            this.add(titlePanel);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridBagLayout());
            buttonPanel.setOpaque(false);
            SelectButton button1 = new SelectButton(1, buttonFont);
            SelectButton button2 = new SelectButton(2, buttonFont);
            SelectButton button3 = new SelectButton(3, buttonFont);
            SelectButton button4 = new SelectButton(4, buttonFont);
            SelectButton button5 = new SelectButton(5, buttonFont);
            SelectButton button6 = new SelectButton(6, buttonFont);
            SelectButton button7 = new SelectButton(7, buttonFont);
            SelectButton button8 = new SelectButton(8, buttonFont);

            JPanel column1 = new JPanel();
            JPanel column2 = new JPanel();
            column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
            add(button1, button3, button5, button7, column1);

            column2.setLayout(new BoxLayout(column2, BoxLayout.Y_AXIS));
            add(button2, button4, button6, button8, column2);

            GridBagConstraints  gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weighty = 0;
            gbc.insets = new Insets(0, 20, 0, 0);
            buttonPanel.add(column1, gbc);
            gbc.gridx = 1;
            buttonPanel.add(column2, gbc);
            this.add(buttonPanel);




        }

        private void add(SelectButton button1, SelectButton button4, SelectButton button5, SelectButton button7, JPanel column1) {
            column1.add(Box.createVerticalStrut(10));
            column1.add(button1);
            column1.add(Box.createVerticalStrut(10));
            column1.add(button4);
            column1.add(Box.createVerticalStrut(10));
            column1.add(button5);
            column1.add(Box.createVerticalStrut(10));
            column1.add(button7);
            column1.setOpaque(false);
        }

        class SelectButton extends JButton {
            public SelectButton(int level, Font buttonFont) {
                super((String.valueOf(level)));
                this.setFont(buttonFont);
                this.setBackground(Colours.BACKGROUND.darker());
                this.setForeground(Color.WHITE);
                this.setFocusable(false);
                Dimension size = new Dimension(75, 75);
                this.setPreferredSize(size);
                this.setMaximumSize(size);
                this.addActionListener(e -> {
                    startGame(players, level);
                });
            }
        }
    }
}
