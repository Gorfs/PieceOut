package src.com.java.gui.UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.com.java.config.Colours;
import src.com.java.config.GuiConfig;
import src.com.java.gui.GameWindow;
import src.com.java.gui.InstanceManager;
import src.com.java.logic.PieceLogic;

public class Launcher {

    private final LauncherPanel launcherPanel;
    private final GameWindow mainFrame;
    private final CardLayout layout;

    public Launcher(Point location) {
        mainFrame = new GameWindow();
        launcherPanel = new LauncherPanel();
        layout = new CardLayout();
        mainFrame.setLayout(layout);
        mainFrame.setLocation(location);
    }

    /*
     * This method is called to initialize the launcher panel and make it visible
     */
    public void launch(){
        mainFrame.add(launcherPanel, "launcher");
        mainFrame.setVisible(true);
    }


    /*
     * This method is called to start the game with the number of players specified
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
    
    
    class LauncherPanel extends JPanel {

        private JLabel titleImage;
        private JButton startOnePlayerButton;
        private JButton startTwoPlayerButton;
        private JButton exitButton;
        

        /*
         * This panel contains the title image and the buttons to start the game with 1 or 2 players or to exit the game
         */

        public LauncherPanel() {
            this.setBackground(Colours.BACKGROUND);
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            try {
                BufferedImage bufferedImage = ImageIO.read(new File(GuiConfig.resourcePath + "icons/titleImage.png"));
                titleImage = new JLabel(new ImageIcon(bufferedImage));
                int marginHeightBetweenUpAndImage = 50;
                this.add(Box.createVerticalStrut(marginHeightBetweenUpAndImage));
    
                // Create a JPanel with a FlowLayout and add the titleImage to it
                JPanel imagePanel = new JPanel(new FlowLayout());
                imagePanel.setOpaque(false);
                imagePanel.add(titleImage);
                this.add(imagePanel);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Font buttonFont = new Font("Arial", Font.BOLD, 15);

            startOnePlayerButton = new JButton("Play with 1 Player");
            startOnePlayerButton.setBackground(Colours.BACKGROUND.darker());
            startOnePlayerButton.setForeground(Color.WHITE);
            startOnePlayerButton.setFocusable(false);
            startOnePlayerButton.setPreferredSize(new Dimension(250, 50));
            startOnePlayerButton.setFont(buttonFont);
            startOnePlayerButton.addActionListener(e -> {
                Launcher.this.startGame(1);
            });
    
            startTwoPlayerButton = new JButton("Play with 2 Players");
            startTwoPlayerButton.setBackground(Colours.BACKGROUND.darker());
            startTwoPlayerButton.setForeground(Color.WHITE);
            startTwoPlayerButton.setFocusable(false);
            startTwoPlayerButton.setPreferredSize(new Dimension(200, 50));
            startTwoPlayerButton.setFont(buttonFont);
            startTwoPlayerButton.addActionListener(e -> {
                Launcher.this.startGame(2);
            });
    
            exitButton = new JButton("Exit");
            exitButton.setBackground(Colours.BACKGROUND.darker());
            exitButton.setForeground(Color.WHITE);
            exitButton.setFocusable(false);
            exitButton.setPreferredSize(new Dimension(150, 25));
            exitButton.setFont(buttonFont);
            exitButton.addActionListener(e -> System.exit(0));
    
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setOpaque(false);
    
            // Wrap each button in a JPanel with a FlowLayout
            JPanel onePlayerPanel = new JPanel(new FlowLayout());
            onePlayerPanel.setOpaque(false);
            onePlayerPanel.add(startOnePlayerButton);
            buttonPanel.add(onePlayerPanel);
    
            JPanel twoPlayerPanel = new JPanel(new FlowLayout());
            twoPlayerPanel.setOpaque(false);
            twoPlayerPanel.add(startTwoPlayerButton);
            buttonPanel.add(twoPlayerPanel);
    
            JPanel exitPanel = new JPanel(new FlowLayout());
            exitPanel.setOpaque(false);
            exitPanel.add(exitButton);
            buttonPanel.add(exitPanel);
    
            this.add(buttonPanel);

        }
    }
}
