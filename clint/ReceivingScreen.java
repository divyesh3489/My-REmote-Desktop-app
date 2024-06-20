import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;

public class ReceivingScreen extends Thread {
    private ObjectInputStream cObjectInputStream = null;
    private InputStream input;
    private JPanel panel;
    private boolean loopFlag = true;
    Image img = null;
    public ReceivingScreen(InputStream input, JPanel panel) {
        this.input = input;
        this.panel = panel;
        this.start();
    }

    public void run() {
        try {
            while (this.loopFlag) {
                byte array[] = new byte[1024 * 1024];
                int count = 0;
                do {
                    count += input.read(array, count, array.length - count);
                } while (!(count > 4 && array[count - 2] == (byte)-1 && array[count - 1] == (byte)-39));
                img =ImageIO.read(new ByteArrayInputStream(array));
                img = img.getScaledInstance(panel.getWidth(),panel.getHeight(), Image.SCALE_FAST);
                
                Graphics graphics = panel.getGraphics();
                graphics.drawImage(img, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            }
        } catch (IOException e) {/*  */
            e.printStackTrace();
        }
    }
}
