package src.gui.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import src.config.Colours;

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
        // Setting the icon for the undo button
        ImageIcon undoIcon = new ImageIcon(new ImageIcon("resources/icons/undoredo.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        undoButton.setIcon(undoIcon);
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

        // Convert the scaled image back to buffered image
        BufferedImage buffered = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        buffered.getGraphics().drawImage(undoIcon, 0, 0 , null);

        // Create a transform object
        AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
        transform.translate(-buffered.getWidth(null), 0);

        // Create the transform operation
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        // Apply the operation
        BufferedImage flippedImage = op.filter(buffered, null);

        // Set the flipped image as icon
        redoIcon = new ImageIcon(flippedImage);
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
