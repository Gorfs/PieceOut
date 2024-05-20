package com.java.gui.UI;

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

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.GameWindow;
import com.java.gui.InstanceManager;
import com.java.gui.sound.SoundManager;
import com.java.logic.LevelManager;
import com.java.logic.PieceLogic;


/**
 * Class that represents the select level menu.
 * It contains the buttons to select the level to start the game.
 */
public class SelectLevelMenu {
    /**
     * Panel that contains the select level panel.
     */
    private final SelectLevelPanel selectLevelPanel;
    /**
     * Mainframe of the game where the select level panel will be shown.
     */
    private final GameWindow mainFrame;
    /**
     * Number of players that will play the game.
     */
    private final int players;
    /**
     * Sound manager instance that manages the sounds of the game.
     */
    private final SoundManager soundManager;

    /**
     * Constructor of the select level menu.
     * @param location where the select level menu will be shown
     * @param soundManager sound manager instance that manages the sounds of the game
     * @param players number of players that will play the game
     */
    public SelectLevelMenu(Point location, SoundManager soundManager, int players) {
        mainFrame = new GameWindow();
        selectLevelPanel = new SelectLevelPanel();
        CardLayout layout = new CardLayout();
        mainFrame.setLayout(layout);
        mainFrame.setLocation(location);
        this.soundManager = soundManager;
        this.players = players;
    }

    /**
     * This method is called to initialize the launcher panel and make it visible.
     */
    public void launch(){
        mainFrame.add(selectLevelPanel, "selectLevel");
        mainFrame.setVisible(true);
    }


    /**
     * This method is called to start the game with the number of players specified.
     */
    public void startGame(int players, int level){
        //TODO: implement the logic to start the game with the number of players specified
        mainFrame.dispose();
        level--;
        if (players == 1) {
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location, soundManager);
            instanceManager.start(level);
        }
        else{
            Point location2 = mainFrame.getLocation();
            location2.setLocation(location2.getX() + GuiConfig.WINDOW_WIDTH + 10, location2.getY());
            InstanceManager instanceManager2 = new InstanceManager(new PieceLogic(), location2, soundManager);
            instanceManager2.start(level);
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location, soundManager, instanceManager2);
            instanceManager.start(level);
        }
    }

    /**
     * This panel contains the title image and the buttons to start the game with one or two players or to exit the game
     */
    class SelectLevelPanel extends JPanel {
        public SelectLevelPanel() {
            this.setBackground(Colours.BACKGROUND);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            Font buttonFont;
            Font titleFont;

            try{
                File fontFile = new File(GuiConfig.resPath + "fonts/Lentariso-Bd.ttf");
                buttonFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD,30f);
                titleFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 50f);
            }
            catch (Exception e) {
                System.out.println("Error in TopPanel: \nFont not found, file either not found or doesn't exist.");
                // If the font is not found, we use the default font
                buttonFont = new Font("Arial", Font.BOLD, 30);
                titleFont = new Font("Arial", Font.BOLD, 14);
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
            // Initialize the 8 buttons for the levels
            SelectButton[] buttons = new SelectButton[8];
            for (int i = 0; i < 8; i++) {
                buttons[i] = new SelectButton(i + 1, buttonFont);
            }
            JPanel column1 = new JPanel();
            column1.setLayout(new BoxLayout(column1, BoxLayout.Y_AXIS));
            add(buttons, column1, true);

            JPanel column2 = new JPanel();
            column2.setLayout(new BoxLayout(column2, BoxLayout.Y_AXIS));
            add(buttons, column2, false);

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

        /**
         * Method that adds the buttons to the column
         * @param buttons array of buttons
         * @param column column where the buttons will be added
         * @param even if the buttons are in an even or odd position
         */
        private void add(SelectButton[] buttons, JPanel column, boolean even) {
            for (int i = (even? 0 : 1); i < buttons.length; i = i + 2) {
                column.add(buttons[i]);
                column.add(Box.createVerticalStrut(10));
            }
            column.setOpaque(false);
        }

        /**
         * Class that represents the buttons to select the level to start the game.
         */
        class SelectButton extends JButton {
            /**
             * Constructor of the SelectButton class.
             * @param level level of the button
             * @param buttonFont font of the button
             */
            public SelectButton(int level, Font buttonFont) {
                super((String.valueOf(level)));
                if (LevelManager.isLocked(level-1)){
                    this.setEnabled(false);
                }
                else{
                    this.setEnabled(true);
                }

                this.setFont(buttonFont);
                this.setBackground(Colours.BACKGROUND.darker());
                this.setForeground(Color.WHITE);
                this.setFocusable(false);
                Dimension size = new Dimension(75, 75);
                this.setPreferredSize(size);
                this.setMaximumSize(size);
                this.addActionListener(e -> {
                    soundManager.playNote();
                    startGame(players, level);
                });
            }
        }
    }
}
