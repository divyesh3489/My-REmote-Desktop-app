import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ReceivingScreen extends Thread {
    private InputStream input;
    private JPanel panel;
    private boolean loopFlag = true;

    public ReceivingScreen(InputStream input, JPanel panel) {
        this.input = input;
        this.panel = panel;
        start();
    }

    public void run() {
        try {
            while (loopFlag) {
                byte[] array = new byte[1024 * 1024];
                int count = 0;
                do {
                    count += input.read(array, count, array.length - count);
                } while (!(count > 4 && array[count - 2] == (byte) -1 && array[count - 1] == (byte) -39));

                BufferedImage img = ImageIO.read(new ByteArrayInputStream(array));
                Image scaledImg = img.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_FAST);

                BufferedImage bufferedScaledImg = toBufferedImage(scaledImg); // Convert Image to BufferedImage

                Graphics graphics = panel.getGraphics();
                graphics.drawImage(bufferedScaledImg, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to convert Image to BufferedImage
    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
