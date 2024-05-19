package com.java.config;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;


/**
 * class to help manage the png and images used for the pieces in the game.
 */
public class GuiPieceImages {

    /**
     * Image for O shape (a 2 by 2 shape).
     */
    public static ImageIcon OImage;
    /**
     * Image for Ho (Horizontal) shape (a 2 by 1 shape).
     */
    public static ImageIcon HoImage;
    /**
     * Image for I (bar) shape (a 1 by 3 shape).
     */
    public static ImageIcon IImage;
    /**
     * Image for J shape (a 2 by 3 shape).
     */
    public static ImageIcon JImage;
    /**
     * Image for L shape (a 2 by 3 shape).
     */
    public static ImageIcon LImage;
    /**
     * Image for Single shape (a 1 by 1 shape).
     */
    public static ImageIcon SingleImage;
    /**
     * Image for T shape (a 3 by 2 shape).
     */
    public static ImageIcon TImage;
    /**
     * Image for Ve (Vertical) shape (a 1 by 2 shape).
     */
    public static ImageIcon VeImage;

    /**
     * This is a computationally expensive function, only call this once at the start of the game.
     */
    public static void preLoadImages() {
        // PreLoad the image from resources
        try {
            OImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/OShape.png")));
            HoImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/HoShape.png")));
            IImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/IShape.png")));
            JImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/JShape.png")));
            LImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/LShape.png")));
            SingleImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/SingleShape.png")));
            TImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/TShape.png")));
            VeImage = new ImageIcon(ImageIO.read(new File(GuiConfig.resPath + "shapes/VeShape.png")));
        } catch (IOException e) {
            System.out.println("ERROR LOADING IMAGES FROM RESOURCES");
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * This method changes the color of an image icon to the specified color.
     * @param icon the image icon to change the color of
     * @param color the color to change the image to
     */
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

    /**
     * This method rotates an image icon by 90 degrees.
     * @param img the image icon to rotate
     * @return the rotated image icon
     */
    public static ImageIcon rotateImage(ImageIcon img) {

        BufferedImage image = (BufferedImage) img.getImage();

        int h0 = image.getHeight();
        double angle = Math.toRadians(90);
        int centerX = h0 / 2;
        int centerY = h0 / 2;
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToRotation(angle, centerX, centerY);
        AffineTransformOp opRotated = new AffineTransformOp(affineTransform,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage transformedImage = opRotated.filter(image,null);
        return new ImageIcon(transformedImage);

    }

    /**
     * This method flips an image icon on the x-axis or y-axis.
     * @param img the image icon to flip
     * @param xAxis if true, flip the image on the x-axis, otherwise flip on the y-axis
     * @return the flipped image icon
     */
    public static ImageIcon flipImage(ImageIcon img, boolean xAxis){
        BufferedImage image = (BufferedImage) img.getImage();
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        if(xAxis){
            // flip the image on the x-axis
            tx.translate(0, -image.getHeight());
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            return new ImageIcon(op.filter(image, null));
        } else {
            // flip the image on the y-axis
            tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);
            return new ImageIcon(image);
        }
    }
}
