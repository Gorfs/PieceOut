package src.gui.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import java.util.ArrayList;

import src.config.Colours;
import src.config.GuiConfig;
import src.gui.GUIManager;
import src.logic.Move;
import src.logic.MoveEdit;
import src.logic.PieceLogic;
import src.logic.Player;
import src.piece.decorator.Decorator;
import src.utils.Debug;
import src.controllers.PlayerController;

public class BottomPanel extends JPanel {
    /**
     * this panel represents the bottom panel containing the undo and redo buttons
     */

    private ArrayList<JButton> btnList = new ArrayList<JButton>();

    private JButton undoButton;
    private JButton redoButton;

    public PlayerController playerController;

    public BottomPanel(GUIManager guiManager) {
        /**
         * main constructur of the bottom panel
         */
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        JPanel buttonContainer = new JPanel();

        // setting up the undoManager
        this.playerController = guiManager.playerController;

        Debug.out("Plyaer controller: " + playerController);

        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        buttonContainer.setOpaque(false);

        undoButton = new JButton();
        undoButton.setBackground(Colours.BACKGROUND.brighter());
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusable(false);
        // Setting the icon for the undo button
        ImageIcon undoIcon = new ImageIcon(
                new ImageIcon(GuiConfig.iconPath + "undo.png").getImage().getScaledInstance(50, 50,
                        Image.SCALE_SMOOTH));
        undoButton.setIcon(undoIcon);
        undoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // testing undo
                playerController.um.undo();
                guiManager.update();
                updateButtons();
            }
        });

        redoButton = new JButton();
        redoButton.setForeground(Color.WHITE);
        redoButton.setFocusable(false);

        // Load the image
        ImageIcon redoIcon = new ImageIcon(
                new ImageIcon(GuiConfig.iconPath + "redo.png").getImage().getScaledInstance(50, 50,
                        Image.SCALE_SMOOTH));
        redoButton.setIcon(redoIcon);
        // redoButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        redoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerController.um.redo();
                guiManager.update();
                updateButtons();
            }
        });

        // this.add(Box.createRigidArea(new Dimension(GuiConfig.WINDOW_WIDTH / 5, 20)));
        this.add(buttonContainer);
        buttonContainer.add(undoButton);
        buttonContainer.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonContainer.add(redoButton);
        this.setBorder(BorderFactory.createEmptyBorder(3, 3, 10, 3));

        btnList.add(undoButton);
        btnList.add(redoButton);
        updateButtons();

        // making the main bottom panel take up the whole width
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

    }

    public void updateButtons() {
        // on update of gameloop, update the buttons to clickable or not.
        updateButton(redoButton, playerController.um.canRedo());
        updateButton(undoButton, playerController.um.canUndo());
    }

    public void updateButton(JButton btn, boolean available) {
        // setting the value of the button to available (clickable) or not
        if (available) {
            btn.setEnabled(true);
            btn.setBackground(Colours.BACKGROUND.brighter());
        } else {
            setNotAvailable(btn);
        }
    }

    public void setNotAvailable(JButton btn) {
        // sets the button to grey and not clickable
        btn.setEnabled(false);
        btn.setBackground(Colours.BACKGROUND_GREYED);
    }

}
