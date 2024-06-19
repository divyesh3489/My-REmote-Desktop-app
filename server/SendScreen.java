import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;

public  class SendScreen extends  Thread
{
    Socket cs = null;
    Robot robot = null;
    Rectangle rect = null;
    boolean loopFlag = true;
    OutputStream outstream = null;
    SendScreen(Socket cs, Robot robot , Rectangle rect)
    {
        this.cs = cs;
        this.robot = robot;
        this.rect = rect;
        this.start();
    } 
    public void run()
    {
        try
        {
            outstream = cs.getOutputStream();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        while(this.loopFlag)
        {
            BufferedImage  bImg= robot.createScreenCapture(rect);
            try {
                ImageIO.write(bImg,"jpeg", outstream);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
} 