package src.com.java.config;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.*;
import src.com.java.utils.Debug;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
/**
 * class to help manage the png and images used for the pieces in the game.
 */
public class GuiPieceImages {

    // these images should be available to use throughout the code since they are preloaded at the start of the instance
    // by the instance manager.
    public static ImageIcon OImage ;
    public static ImageIcon HoImage ;
    public static ImageIcon IImage ;
    public static ImageIcon JImage ;
    public static ImageIcon LImage ;
    public static ImageIcon SingleImage ;
    public static ImageIcon TImage ;
    public static ImageIcon VeImage ;


    // this is a computationally expensive function, only call this once at the start of the game.
    public static void preLoadImages() {
        // PreLoad the image from resources
        try {
            OImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/OShape.png")));
            HoImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/HoShape.png")));
            IImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/IShape.png")));
            JImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/JShape.png")));
            LImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/LShape.png")));
            SingleImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/SingleShape.png")));
            TImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/TShape.png")));
            VeImage = new ImageIcon(ImageIO.read(new File("src/com/resources/shapes/VeShape.png")));
        } catch (IOException e) {
            Debug.out("ERROR LOADING IMAGES FROM RESOURCES");
            e.printStackTrace();
        }
    }

    public static void changeColour(ImageIcon icon, Color color){
        // get the buffered image
        BufferedImage bufferedImage = (BufferedImage) icon.getImage();
        WritableRaster raster = bufferedImage.getRaster();
        int[] pixels = new int[4];
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                raster.getPixel(x, y, pixels);
                pixels[0] = color.getRed();
                pixels[1] = color.getGreen();
                pixels[2] = color.getBlue();
                raster.setPixel(x, y, pixels);
        }
        }
        bufferedImage.setData(raster);
        icon.setImage(bufferedImage);
    }
    public static ImageIcon rotateImage(ImageIcon img) {

        BufferedImage image = (BufferedImage) img.getImage();
        
        int w0 = image.getWidth();
        int h0 = image.getHeight();
        double angle = Math.toRadians(90);
                int centerX = w0 / 2;
        int centerY = h0 / 2;
        centerX = h0 / 2;
        centerY = h0 / 2;
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(angle, centerX, centerY);
        AffineTransformOp opRotated = new AffineTransformOp(affineTransform,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage transformedImage = opRotated.filter(image,null);
        return new ImageIcon(transformedImage);

    }

    public static ImageIcon flipImage(ImageIcon img, boolean xAxis){
        BufferedImage image = (BufferedImage) img.getImage();
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        if(xAxis){
            tx.translate(0, -image.getHeight());
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            return new ImageIcon(op.filter(image, null));

        }else{
            tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);
            return new ImageIcon(image);
        } 
    }


}
