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
import src.gui.GUIManager;
import src.logic.Move;
import src.logic.MoveEdit;
import src.logic.PieceLogic;
import src.piece.decorator.Decorator;
import src.controllers.PlayerController;

import static src.controllers.PlayerController.um;

public class BottomPanel extends JPanel{
    /**
     * this panel represents the bottom panel containing the undo and redo buttons
      */


    private ArrayList<JButton> btnList = new ArrayList<JButton>();

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

        undoButton = new JButton();
        undoButton.setBackground(Colours.BACKGROUND.brighter());
        undoButton.setForeground(Color.WHITE);
        undoButton.setFocusable(false);
        // Setting the icon for the undo button
        ImageIcon undoIcon = new ImageIcon(new ImageIcon("resources/icons/undo.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        undoButton.setIcon(undoIcon);
        undoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // TODO implement undo and redo

                // testing undo
                um.undo();
                GUIManager.INSTANCE.update();
                updateButtons();
            }
        });

        redoButton = new JButton();
        redoButton.setForeground(Color.WHITE);
        redoButton.setFocusable(false);

        // Load the image
        ImageIcon redoIcon = new ImageIcon(new ImageIcon("resources/icons/redo.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        redoButton.setIcon(redoIcon);
        //redoButton.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        redoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // TODO implement undo and redo
                for(Decorator piece: PieceLogic.getPieces()){
//                    piece.personalReverseAction();
                    um.addEdit(new MoveEdit(new Move(piece)));
                    GUIManager.getInstance().update();
                    updateButtons();
                }


            }
        });

        // TODO set dimensiosn of rigid area to dynamic instead of hardcoded values.
        // this.add(Box.createRigidArea(new Dimension(GuiConfig.WINDOW_WIDTH / 5, 20)));
        this.add(buttonContainer);
        buttonContainer.add(undoButton);
        buttonContainer.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonContainer.add(redoButton);
        this.setBorder(BorderFactory.createEmptyBorder(3,3,10,3));


        btnList.add(undoButton);
        btnList.add(redoButton);
        updateButtons();


        // making the main bottom panel take up the whole width
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));


    }


    public void setNotAvailable(JButton btn){
        // sets the button to grey and not clickable
        btn.setEnabled(false);
        btn.setBackground(Colours.BACKGROUND_GREYED);
    }



    public void updateButtons(){
        for(JButton btn : btnList){
            if(btn == undoButton){
                if(PlayerController.canUndo()){
                    btn.setEnabled(true);
                    btn.setBackground(Colours.BACKGROUND.brighter());
                }
                else{
                    setNotAvailable(btn);
                }
            }
            else if(btn == redoButton){
                if(PlayerController.canRedo()){
                    btn.setEnabled(true);
                    btn.setBackground(Colours.BACKGROUND.brighter());
                }
                else{
                    setNotAvailable(btn);
                }

            }
        }
    }
}
