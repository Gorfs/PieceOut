package src.com.java.config;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
/**
 * class to help manage the png and images used for the pieces in the game.
 */
public class GuiPieceImages {

    public ImageIcon OShapeImage ;
    public ImageIcon HoShapeImage ;
    public ImageIcon IShapeImage ;
    public ImageIcon JShapeImage ;
    public ImageIcon LShapeImage ;
    public ImageIcon SingleShapeImage ;
    public ImageIcon TShapeImage ;
    public ImageIcon VeShapeImage ;


    // this is a computationally expensive function, only call this once at the start of the game.
    public void PreloadImages() {
        // PreLoad the image from resources
        OShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/OShape.png"));
        HoShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/HoShape.png"));
        IShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/IShape.png"));
        JShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/JShape.png"));
        LShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/LShape.png"));
        SingleShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/SingleShape.png"));
        TShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/TShape.png"));
        VeShapeImage = new ImageIcon(getClass().getResource("src/com/resources/shapes/VeShape.png"));
    }
}
