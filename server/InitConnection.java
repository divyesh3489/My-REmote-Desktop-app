import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.net.ServerSocket;



public class InitConnection {
    private Socket clientSocket;
    private String password;
    private JLabel statusLabel;
    String width = "";
    String height = "";

    public InitConnection(Socket clientSocket, String password, JLabel statusLabel) {
        this.clientSocket = clientSocket;
        this.password = password;
        this.statusLabel = statusLabel;
        handleConnection();
    }

    private void handleConnection(){
        Robot robot = null;
        Rectangle rect = null;
        try {
            
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDivce = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = "" + dim.getWidth();
            height = "" + dim.getHeight();
            rect = new Rectangle(dim);
            robot = new Robot(gDivce);
            String receivedPassword = input.readUTF();
            if (receivedPassword.equals(password)) {
                output.writeBoolean(true);
                output.writeUTF(width);
                output.writeUTF(height);
                statusLabel.setText("Authentication successful. Connection established.");
                new SendScreen(this.clientSocket, this.statusLabel , robot, rect);  
            } else {
                output.writeBoolean(false);
                statusLabel.setText("Authentication failed. Incorrect password.");
                clientSocket.close();
            }
        } catch (Exception e) {
            statusLabel.setText("Connection error.");
            e.printStackTrace();
        }
    }
}
