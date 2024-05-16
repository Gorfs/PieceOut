package src.com.java.gui.UI;


import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.GameWindow;
import src.com.java.gui.InstanceManager;
import src.com.java.gui.sound.Sound;
import src.com.java.logic.PieceLogic;

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

    /**
     * Main constructor of the launcher
     */
    public Launcher(Point location) {
        mainFrame = new GameWindow();
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
    public void startGame(int players){
        //TODO: implement the logic to start the game with the number of players specified
        mainFrame.dispose();
        if (players == 1) {
            int level = 0;
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location);
            instanceManager.start(level);
        }
        else{
            int level = 0;
            Point location2 = mainFrame.getLocation();
            location2.setLocation(location2.getX() + GuiConfig.WINDOW_WIDTH + 10, location2.getY());
            InstanceManager instanceManager2 = new InstanceManager(new PieceLogic(), location2, null);
            instanceManager2.start(level);
            Point location = mainFrame.getLocation();
            InstanceManager instanceManager = new InstanceManager(new PieceLogic(), location, instanceManager2);
            instanceManager.start(level);
        }
    }


    /**
     * This subclass is responsible for the launcher panel.
     * This panel contains the title image and the buttons to start
     * the game with one or two players or to exit the game.
     */
    class LauncherPanel extends JPanel {
        private final Sound sound = new Sound();

        /**
         * Constructor of the LauncherPanel, it initializes the attributes and sets the background color
         */
        public LauncherPanel() {
            this.setBackground(Colours.BACKGROUND);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            sound.playOST();

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
            JButton startOnePlayerButton = getStartOnePlayerButton(buttonFont);
            JButton startTwoPlayerButton = getStartTwoPlayerButton(buttonFont);
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
        private JButton getStartOnePlayerButton(Font buttonFont) {
            JButton startOnePlayerButton = new JButton("Play with 1 Player");
            startOnePlayerButton.setBackground(Colours.BACKGROUND.darker());
            startOnePlayerButton.setForeground(Color.WHITE);
            startOnePlayerButton.setFocusable(false);
            startOnePlayerButton.setPreferredSize(new Dimension(250, 50));
            startOnePlayerButton.setFont(buttonFont);
            startOnePlayerButton.addActionListener(e -> {
                sound.playNote();
                Launcher.this.startGame(1);
            });
            return startOnePlayerButton;
        }

        /**
         * This method creates the "Start a game for two players" button
         * @param buttonFont Font to use for the button
         * @return "Start a game for two players" button
         */
        private JButton getStartTwoPlayerButton(Font buttonFont) {
            JButton startTwoPlayerButton = new JButton("Play with 2 Players");
            startTwoPlayerButton.setBackground(Colours.BACKGROUND.darker());
            startTwoPlayerButton.setForeground(Color.WHITE);
            startTwoPlayerButton.setFocusable(false);
            startTwoPlayerButton.setPreferredSize(new Dimension(200, 50));
            startTwoPlayerButton.setFont(buttonFont);
            startTwoPlayerButton.addActionListener(e -> {
                sound.playNote();
                Launcher.this.startGame(2);
            });
            return startTwoPlayerButton;
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
