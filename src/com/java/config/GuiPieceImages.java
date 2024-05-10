package src.com.java.config;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 * class to help manage the png and images used for the pieces in the game.
 */
public class GuiPieceImages {

    public ImageIcon OImage ;
    public ImageIcon HoImage ;
    public ImageIcon IImage ;
    public ImageIcon JImage ;
    public ImageIcon LImage ;
    public ImageIcon SingleImage ;
    public ImageIcon TImage ;
    public ImageIcon VeImage ;


    // this is a computationally expensive function, only call this once at the start of the game.
    public void PreloadImages() {
        // PreLoad the image from resources
        OImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/OShape.png"));
        HoImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/HoShape.png"));
        IImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/IShape.png"));
        JImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/JShape.png"));
        LImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/LShape.png"));
        SingleImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/SingleShape.png"));
        TImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/TShape.png"));
        VeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/VeShape.png"));
    }


    // we are going to use the shapes to help identify the piece image from the model of the piece in the logic
    public boolean[][] OShape = ;
    public boolean[][] HoShape = ;
    public boolean[][] IShape = ;
    public boolean[][] JShape = ;
    public boolean[][] LShape = ;
    public boolean[][] SingleShape = ;
    public boolean[][] TShape = ;
    public boolean[][] VeShape = ;

}
