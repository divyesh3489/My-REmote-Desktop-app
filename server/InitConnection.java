import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.*;

public class InitConnection {
    private Socket clientSocket;
    private String password;
    JLabel statusLabel;
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
                new ReceiveEvent(this.clientSocket, robot); 
            } else {
                output.writeBoolean(false);
                statusLabel.setText("Authentication failed. Incorrect password. Connection Terminated.");
                clientSocket.close();
                Thread.sleep(3000);
                statusLabel.setText("Waiting for client to connect...");
                SwingUtilities.invokeLater(() -> {
                    if (statusLabel.getParent() instanceof ConnectionStatusFrame) {
                        ((ConnectionStatusFrame) statusLabel.getParent()).restartServer();
                    }
                });
            
            }
        } catch (Exception e) {
            statusLabel.setText("Connection error.");
            e.printStackTrace();
        }
    }
}
