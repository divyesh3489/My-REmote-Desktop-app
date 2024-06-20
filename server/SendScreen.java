import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Robot;
import javax.imageio.ImageIO;
public class SendScreen extends Thread {
    private Socket clientSocket;
    private JLabel statusLabel;
    private Robot robot;
    private Rectangle screenRect;

    public SendScreen(Socket clientSocket, JLabel statusLabel , Robot robot, Rectangle screenRect) {


        this.clientSocket = clientSocket;
        this.statusLabel = statusLabel;
        this.robot = robot;
        this.screenRect = screenRect;
       
        start();
    }

    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                BufferedImage screenCapture = robot.createScreenCapture(screenRect);
                ImageIO.write(screenCapture, "jpg", output);
            }
        } catch (IOException e) {
            statusLabel.setText("Connection lost.");
            e.printStackTrace();
        }
    }
}
