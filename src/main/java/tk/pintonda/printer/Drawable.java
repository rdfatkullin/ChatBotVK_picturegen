package tk.pintonda.printer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;

//This class builds image from URL or file path and returns it
public class Drawable {

    BufferedImage imgIc;

    public Drawable (String file) {
        try {
            imgIc = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable (URL url) {
        try {
            imgIc =  ImageIO.read(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage() {
        return imgIc;
    }
}
