package tk.pintonda.printer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//Class that handles image processing based on user's input
public class Printer {

    Drawable back;
    Drawable pengn;

    int y0;
    int y1;
    int x = 1600;
    int y = 900;

    BufferedImage buf;
    Graphics imgGrap;

    String url;
    String peng;
    String mainLine;
    String upperLine;
    String angleLine;

    File output;

    public Printer(String[] lines, String url){
        //Grabbing parameters, executing image construction
        System.out.println(url);
        this.url = url;
        peng = lines[0];
        mainLine = lines[1];
        upperLine = lines[2];
        angleLine = lines[3];

        getBackground(url);
        printAll();
    }

    public void getBackground(String url) {
        //Grabbing image from url
        try {
            back = new Drawable(new URL(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void printStrip() {
        //Setting gradient, drawing the tape at bottom
        Graphics2D gg = (Graphics2D) imgGrap;
        Color startColor = new Color (252,252,252,204);
        Color endColor = new Color (220,233,250,204);
        gg.setPaint(new GradientPaint(5,3, startColor, 100, 200, endColor));
        y0 = (int) Math.round(0.85 * y);
        y1 = (int) Math.round(0.94 * y);
        gg.fillRect(0, y0, x, y1-y0);
        imgGrap = gg;
    }

    public void printText(String title, String up, String angle) {
        // Adding text to the picture, main text first
        title = title.toUpperCase();
        Graphics2D gg = (Graphics2D) imgGrap;
        gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gg.setColor(new Color (1, 23, 53));
        int fontSize = getFont(title);
        gg.setFont(new Font("Segoe UI Light", Font.PLAIN, fontSize));
        gg.drawString(title, x/2 - (int) (x*0.12) - (int) Math.round(fontSize*title.length()*0.3), y0 + (y1 - y0)/2+ (int) Math.round(0.34*fontSize));

        //Adding text to the upper part
        fontSize = getFont(up);
        up = up.toUpperCase();
        gg.setColor(new Color (247, 247, 247));
        gg.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(fontSize*0.5)));
        gg.drawString(up, x/2 - (int) Math.round(up.length()*0.125), y0 - (int)Math.round(y * 0.005));

        //Adding text at specified angle
        fontSize = getFont(angle);
        angle = angle.toUpperCase();
        gg.setColor(new Color (247, 247, 247));
        gg.setFont(new Font("Segoe UI Light", Font.PLAIN, (int) Math.round(fontSize*0.7)));
        gg.rotate(-0.5);
        gg.drawString(angle, x/2 - (int) Math.round(up.length()*0.3), y0 + (int)Math.round(y * 0.22));
        gg.rotate(0.2);

        imgGrap = gg;
    }

    public int getFont(String s) {
        if (s.length() < 26) {
            return (int) (Math.round(0.08 * y));
        } else {
            return (int) (Math.round(0.08 * y) - s.length()*0.3);
        }
    }

    public void saveImage() {
        //Saving that image in PNG
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
        Date date = new Date();
        output = new File("resources/output/" + dateFormat.format(date)+ 100*Math.random() + ".png");
        try {
            ImageIO.write(buf, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getOutput() {
        return output;
    }

    public void printAll() {

        //Creating image
        buf = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        imgGrap = buf.getGraphics();
        pengn = new Drawable("resources/ping/"+peng+".png"); //trim needs only on linux
        //Background
        imgGrap.drawImage(back.getImage(), 0, 0, x, y, 0, 0, back.imgIc.getWidth(), back.imgIc.getHeight(), null);
        printStrip();
        printText(mainLine, upperLine, angleLine);
        int px = (int) Math.round(0.4*x);
        int py = (int) Math.round(0.7*y);
        //Drawing penguin
        imgGrap.drawImage(pengn.getImage(), px, py, x, (int) (1.74*y), 0, 0, pengn.imgIc.getWidth(), pengn.imgIc.getHeight(), null);
        saveImage();
        return;
    }
}
