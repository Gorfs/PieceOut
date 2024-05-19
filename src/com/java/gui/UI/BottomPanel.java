package com.java.gui.UI;

import com.java.config.Colours;
import com.java.config.GuiConfig;
import com.java.controllers.PlayerController;
import com.java.gui.InstanceManager;
import com.java.utils.Debug;

import javax.swing.*;
import java.awt.*;


/**
 * this panel represents the bottom panel containing the undo and redo buttons
 */
public class BottomPanel extends JPanel {
    /**
     * undo button.
     */
    private final JButton undoButton;
    /**
     * redo button.
     */
    private final JButton redoButton;
    /**
     * player controller instance that attached to the undo and redo button.
     */
    public PlayerController playerController;

    /**
     * main constructor of the bottom panel
     */
    public BottomPanel(InstanceManager instanceManager) {
        super();
        // setting the layout of the bottom panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        JPanel buttonContainer = new JPanel();

        // setting up the undoManager
        this.playerController = instanceManager.getPlayerController();


        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        buttonContainer.setOpaque(false);

        undoButton = new JButton();
        undoButton.setBackground(Colours.BACKGROUND.brighter());
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusable(false);

        // Load the image for the undo button
        Image image = new ImageIcon(GuiConfig.resPath + "icons/undo.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon undoIcon = new ImageIcon(image);
        // Initialize the undo button
        undoButton.setIcon(undoIcon);
        undoButton.addActionListener(e -> {
            playerController.getUm().undo();
            instanceManager.decMoves();
            Debug.out("numMoves: " + instanceManager.getNumMoves());
            instanceManager.getSoundManager().playMoveSound();instanceManager.update();
            updateButtons();
        });

        redoButton = new JButton();
        redoButton.setForeground(Color.WHITE);
        redoButton.setFocusable(false);

        // Load the image for the redo button
        image = new ImageIcon(GuiConfig.resPath + "icons/redo.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon redoIcon = new ImageIcon(image);
        // Initialize the redo button
        redoButton.setIcon(redoIcon);
        redoButton.addActionListener(e -> {
            playerController.getUm().redo();
            instanceManager.incMoves();
            Debug.out("numMoves: " + instanceManager.getNumMoves());
            instanceManager.getSoundManager().playMoveSound();instanceManager.update();
            updateButtons();
        });

        this.add(buttonContainer);
        buttonContainer.add(undoButton);
        buttonContainer.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonContainer.add(redoButton);
        this.setBorder(BorderFactory.createEmptyBorder(3, 3, 10, 3));
        // making the main bottom panel takes up the whole width
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        updateButtons();
    }

    /**
     * updates the buttons to clickable or not
     */
    public void updateButtons() {
        // on update of game loop, update the buttons to clickable or not.
        updateButton(redoButton, playerController.getUm().canRedo());
        updateButton(undoButton, playerController.getUm().canUndo());
    }

    /**
     * updates a single button to clickable or not
     * @param btn the button to update, either undo or redo
     * @param available whether the button should be clickable or not
     */
    public void updateButton(JButton btn, boolean available) {
        // setting the value of the button to available (clickable) or not
        if (available) {
            btn.setEnabled(true);
            btn.setBackground(Colours.BACKGROUND.brighter());
        } else {
            setNotAvailable(btn);
        }
    }

    /**
     * set the button to not clickable and all gray.
     * @param btn the button to set to not clickable.
     */
    public void setNotAvailable(JButton btn) {
        // sets the button to gray and not clickable
        btn.setEnabled(false);
        btn.setBackground(Colours.BACKGROUND_GREYED);
    }

}
