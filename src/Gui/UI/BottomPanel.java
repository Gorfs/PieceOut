package src.Gui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.Config.*;

public class BottomPanel extends JPanel{
    /**
     * this panel represents the bottom panel containing the undo and redo buttons
      */


    private JButton undoButton;
    private JButton redoButton;


    public BottomPanel(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new BoxLayout(buttonContainer, BoxLayout.X_AXIS));
        buttonContainer.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        buttonContainer.setOpaque(false);

        undoButton = new JButton("Undo");
        undoButton.setBackground(Colours.BACKGROUND.brighter());
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusable(false);
        //undoButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // TODO implement undo and redo
            }
        });

       redoButton = new JButton("Redo");
        redoButton.setBackground(Colours.BACKGROUND.brighter());
        redoButton.setForeground(Color.WHITE);
        redoButton.setFocusable(false);
        //redoButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        redoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // TODO implement undo and redo
            }
        });

        // TODO set dimensiosn of rigid area to dynamic instead of hardcoded values.
        // this.add(Box.createRigidArea(new Dimension(GuiConfig.WINDOW_WIDTH / 5, 20)));
        this.add(buttonContainer);
        buttonContainer.add(undoButton);
        buttonContainer.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonContainer.add(redoButton);
        this.setBorder(BorderFactory.createEmptyBorder(3,3,10,3));


        // making the main bottom panel take up the whole width
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));


    }









}
