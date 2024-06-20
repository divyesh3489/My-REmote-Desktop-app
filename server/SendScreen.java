import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
public class SendScreen extends Thread {
    private Socket clientSocket;
    private JLabel statusLabel;
    private Robot robot;
    private Rectangle screenRect;
    boolean loopFlag = true;
    OutputStream output = null;

    public SendScreen(Socket clientSocket, JLabel statusLabel , Robot robot, Rectangle screenRect) {


        this.clientSocket = clientSocket;
        this.statusLabel = statusLabel;
        this.robot = robot;
        this.screenRect = screenRect;
       
        this.start();
    }

    public void run() {
        try
        {
            output = clientSocket.getOutputStream();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        while(this.loopFlag)
        {
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);
            try {
                ImageIO.write(screenCapture, "jpeg", output);
                
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
