package src.com.java.gui;

import src.com.java.config.GuiConfig;
import src.com.java.piece.Piece;
import src.com.java.piece.decorator.Decorator;
import src.com.java.controllers.PlayerController;
import src.com.java.gui.gamePlay.*;
import src.com.java.logic.Arena;
import src.com.java.logic.LevelManager;
import src.com.java.logic.PieceLogic;
import src.com.java.utils.Debug;

import static src.com.java.config.GuiConfig.height;

import static src.com.java.config.GuiConfig.width;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent; // Import the ActionEvent class

/**
 * GUIManager class that is responsible for creating, changing,
 * updating and general management of the GUI.
 */
public class InstanceManager {
    /**
     * mains panels and windows for the game
     */
    private final GameWindow mainFrame = new GameWindow();
    private GamePanel gamePanel;

    // layout to change panels in rootPanel
    private final CardLayout layout = new CardLayout();
    private final JPanel levelTransitionPanel;

    private ArrayList<GuiPiece> guiPieces = new ArrayList<GuiPiece>();
    private final PieceLogic pieceLogic;
    public PlayerController playerController;
    public Arena arena;

    public int level;

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public InstanceManager(PieceLogic pieceLogic) {
        /*
         * init of instance
         */
        mainFrame.setLayout(layout);
        this.pieceLogic = pieceLogic;
        levelTransitionPanel = new JPanel();
        levelTransitionPanel.setBackground(Color.BLACK);
        levelTransitionPanel.setBounds(-GuiConfig.WINDOW_WIDTH, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
    }

    public void start(int level) {
        /*
         * initiates the GUI window.
         */
        this.level = level;
        playerController = new PlayerController(this, pieceLogic);
        arena = new Arena(this);

        this.gamePanel = new GamePanel(pieceLogic, this);
        playerController.startController(gamePanel.getArenaPanel());
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT));
        gamePanel.setBounds(0, 0, GuiConfig.WINDOW_WIDTH, GuiConfig.WINDOW_HEIGHT);
        layeredPane.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(levelTransitionPanel, JLayeredPane.PALETTE_LAYER);
    
        mainFrame.add(layeredPane);
        mainFrame.setVisible(true);


        LevelManager.loadPiecesFromJson(this.pieceLogic, level, this);

        for (Decorator piece : this.pieceLogic.getPieces()) {
            GuiPiece guiPiece = new GuiPiece(this, piece);
            guiPieces.add(guiPiece);
            // guiPiece.repaint();
        }

        this.mainFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                GuiConfig.width = mainFrame.getWidth();
                GuiConfig.height = mainFrame.getHeight();
                GuiConfig.updateArenaScale();

                for (Cell[] cells : gamePanel.getArenaPanel().getCells())
                    for (Cell cell : cells) cell.updateCell();
            }
        });
    }

    public int getArenaPanelY() {
        return this.gamePanel.getArenaPanel().getY() + this.gamePanel.getArenaContainerPanel().getY();
    }

    /** 
     * updates the GUI
    */
    public void update() {
        gamePanel.update();
        gamePanel.getBottomPanel().updateButtons();
        for (Decorator piece : pieceLogic.getPieces()) {
            if (piece.getLast().getTarget() != null) {
                if (!piece.getLast().getTarget().getDest().equals(piece.getLast().getPos())) {
                    // if the piece is not in the correct position, no need to call method won()
                    Debug.out("Piece not in correct position");
                    return;
                }
                // TODO: check for rotation and flip of the piece
            }
        }
        // if all pieces are in the correct position, call method won()
        Debug.out("Piece in correct position");
        this.won();
    }

    public void won() {
        // TODO: implement level transition here
        Timer timer1 = new Timer(10, null);
        timer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = levelTransitionPanel.getLocation().x;
                x += 10;
                if (x >= 0) {
                    x = 0;
                    timer1.stop();

                    Timer timer2 = new Timer(1000, null);
                    timer2.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            timer2.stop();

                            Timer timer3 = new Timer(10, null);
                            timer3.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    int x = levelTransitionPanel.getLocation().x;
                                    x += 10;
                                    if (x >= GuiConfig.WINDOW_WIDTH) {
                                        x = GuiConfig.WINDOW_WIDTH;
                                        timer3.stop();
                                    }
                                    levelTransitionPanel.setLocation(x, 0);
                                }
                            });
                            timer3.start();
                        }
                    });
                    timer2.setRepeats(false);
                    timer2.start();
                }
                levelTransitionPanel.setLocation(x, 0);
            }
        });
        timer1.start();

        levelTransitionPanel.setLocation(-GuiConfig.WINDOW_WIDTH, 0);
    }

    /**
     * changes the panel in the main frame to the panel with the name panelName
     */
    public void changePanel(String panelName) {
        layout.show(mainFrame, panelName);
    }

    public PieceLogic getPieceLogic() {
        return pieceLogic;
    }

    public int getHeight() {
        return this.mainFrame.getHeight();
    }
}
