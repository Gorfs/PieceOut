package com.java.gui.UI;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.gui.GameWindow;
import com.java.gui.sound.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


/**
 * This class is responsible for the launcher of the game
 */
public class Launcher {
    /**
     * Panel that contains the launcher panel
     */
    private final LauncherPanel launcherPanel;
    /**
     * Main frame of the game
     */
    private final GameWindow mainFrame;
    private final SoundManager soundManager;

    /**
     * Main constructor of the launcher
     */
    public Launcher(Point location, SoundManager sound) {
        mainFrame = new GameWindow();
        this.soundManager = sound;
        launcherPanel = new LauncherPanel();
        CardLayout layout = new CardLayout();
        mainFrame.setLayout(layout);
        mainFrame.setLocation(location);
    }

    /**
     * This method is called to initialize the launcher panel and make it visible
     */
    public void launch(){
        mainFrame.add(launcherPanel, "launcher");
        mainFrame.setVisible(true);
    }

    /**
     * This method is called to start the game with the number of players specified
     * @param players Number of players that will play the game
     */
    public void startGame(int players, SoundManager soundManager){
        mainFrame.dispose();
        Point location = mainFrame.getLocation();
        SelectLevelMenu selectLevelMenu = new SelectLevelMenu(location, soundManager, players);
        selectLevelMenu.launch();
    }

    /**
     * This subclass is responsible for the launcher panel.
     * This panel contains the title image and the buttons to start
     * the game with one or two players or to exit the game.
     */
    class LauncherPanel extends JPanel {
        /**
         * Constructor of the LauncherPanel, it initializes the attributes and sets the background color
         */
        public LauncherPanel() {
            this.setBackground(Colours.BACKGROUND);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            soundManager.playOST();

            try {
                BufferedImage bufferedImage = ImageIO.read(new File(GuiConfig.resPath + "icons/titleImage.png"));
                JLabel titleImage = new JLabel(new ImageIcon(bufferedImage));
                int marginHeightBetweenUpAndImage = 50;
                this.add(Box.createVerticalStrut(marginHeightBetweenUpAndImage));
    
                // Create a JPanel with a FlowLayout and add the titleImage to it
                JPanel imagePanel = new JPanel(new FlowLayout());
                imagePanel.setOpaque(false);
                imagePanel.add(titleImage);
                this.add(imagePanel);
            } catch (Exception e) {
                System.out.println("Image not found");
                System.out.println("Error in LauncherPanel : " + e.getMessage());
            }

            Font buttonFont;

            try{
                File file = new File(GuiConfig.resPath + "fonts/Lentariso-Bd.ttf");
                buttonFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(Font.BOLD,18f);
            }
            catch (Exception e) {
                System.out.println("Error in LauncherPanel: \nFont not found, file either not found or doesn't exist.");
                // If the font is not found, we use the default font
                buttonFont = new Font("Arial", Font.BOLD, 18);
            }

            // Get the buttons
            JButton startOnePlayerButton = getStartOnePlayerButton(buttonFont, soundManager);
            JButton startTwoPlayerButton = getStartTwoPlayerButton(buttonFont, soundManager);
            JButton exitButton = getExitButton(buttonFont);

            // Create a JPanel with a BoxLayout and add the buttons to it
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);
    
            // Wrap each button in a JPanel with a FlowLayout
            // One player button
            JPanel onePlayerPanel = new JPanel(new FlowLayout());
            onePlayerPanel.setOpaque(false);
            onePlayerPanel.add(startOnePlayerButton);
            buttonPanel.add(onePlayerPanel);
            // Two player button
            JPanel twoPlayerPanel = new JPanel(new FlowLayout());
            twoPlayerPanel.setOpaque(false);
            twoPlayerPanel.add(startTwoPlayerButton);
            buttonPanel.add(twoPlayerPanel);
            // Exit button
            JPanel exitPanel = new JPanel(new FlowLayout());
            exitPanel.setOpaque(false);
            exitPanel.add(exitButton);
            buttonPanel.add(exitPanel);
            // Add the button panel to the launcher panel
            this.add(buttonPanel);

        }

        /**
         * This method creates the "Start a game for one player" button
         * @param buttonFont Font to use for the button
         * @return "Start a game for one player" button
         */
        private JButton getStartOnePlayerButton(Font buttonFont, SoundManager sound) {
            JButton onePLayer = new JButton("Play with 1 Player");
            onePLayer.setBackground(Colours.BACKGROUND.darker());
            onePLayer.setForeground(Color.WHITE);
            onePLayer.setFocusable(false);
            onePLayer.setPreferredSize(new Dimension(250, 50));
            onePLayer.setFont(buttonFont);
            onePLayer.addActionListener(e -> {
                sound.playNote();
                Launcher.this.startGame(1, sound);
            });
            return onePLayer;
        }

        /**
         * This method creates the "Start a game for two players" button
         * @param buttonFont Font to use for the button
         * @return "Start a game for two players" button
         */
        private JButton getStartTwoPlayerButton(Font buttonFont, SoundManager sound) {
            JButton twoPlayer = new JButton("Play with 2 Players");
            twoPlayer.setBackground(Colours.BACKGROUND.darker());
            twoPlayer.setForeground(Color.WHITE);
            twoPlayer.setFocusable(false);
            twoPlayer.setPreferredSize(new Dimension(200, 50));
            twoPlayer.setFont(buttonFont);
            twoPlayer.addActionListener(e -> {
                sound.playNote();
                Launcher.this.startGame(2, sound);
            });
            return twoPlayer;
        }

        /**
         * This method creates the exit button
         * @param buttonFont Font to use for the button
         * @return Exit button
         */
        private JButton getExitButton(Font buttonFont) {
            JButton exitButton = new JButton("Exit");
            exitButton.setBackground(Colours.BACKGROUND.darker());
            exitButton.setForeground(Color.WHITE);
            exitButton.setFocusable(false);
            exitButton.setPreferredSize(new Dimension(150, 25));
            exitButton.setFont(buttonFont);
            exitButton.addActionListener(e -> System.exit(0));

            return exitButton;
        }
    }
}
