
import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ReceivingScreen extends Thread {
    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    private boolean  loopFlag = true;
    InputStream Oinput = null;
    Image img = null;

    public ReceivingScreen(InputStream input, JPanel cPanel) {
        this.Oinput = input;
        this.cPanel = cPanel;
        this.start();
    }
    public void run()
    {
        try
        {
            while(this.loopFlag)
            {   
                byte array[] = new byte[1024*1024];
                int count = 0;
                do { 

                    count += Oinput.read(array,count,array.length-count);
                    
                } while (!(count > 4 && array[count-2] == (byte)-1 && array[count -1] == (byte)-39));
                img =ImageIO.read(new ByteArrayInputStream(array));
                img = img.getScaledInstance(cPanel.getWidth(),cPanel.getHeight(), Image.SCALE_FAST);
                Graphics graphics = cPanel.getGraphics();
                graphics.drawImage(img, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
       
    }

    
    


    
}
