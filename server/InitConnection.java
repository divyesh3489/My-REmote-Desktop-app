import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
    private ServerSocket screenSocket;
    private ServerSocket audioSocket;
    private static final int AUDIO_PORT = 5001;
    private String setPassword;
    private String width = "";
    private String height = "";

    public InitConnection(int port, String setPassword) {
        this.setPassword = setPassword;

        try {
            System.out.println("Waiting for Connection from Client");
            screenSocket = new ServerSocket(port);
            audioSocket = new ServerSocket(AUDIO_PORT);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDevice = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = "" + dim.getWidth();
            height = "" + dim.getHeight();
            Rectangle rect = new Rectangle(dim);
            Robot robot = new Robot(gDevice);
            while (true) {
                Socket screenClientSocket = screenSocket.accept();
                Socket audioClientSocket = audioSocket.accept();
                handleClient(screenClientSocket, audioClientSocket, robot, rect);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket screenClientSocket, Socket audioClientSocket, Robot robot, Rectangle rect) {
        try (DataInputStream password = new DataInputStream(screenClientSocket.getInputStream());
                DataOutputStream verify = new DataOutputStream(screenClientSocket.getOutputStream())) {

            String clientPassword = password.readUTF();
            if (clientPassword.equals(setPassword)) {
                verify.writeBoolean(true);
                verify.writeUTF(width);
                verify.writeUTF(height);
                new SendScreen(screenClientSocket, robot, rect);
                new ReceiveEvent(screenClientSocket, robot);

                
                new AudioSender(audioClientSocket).start();
            } else {
                verify.writeBoolean(false);
                System.out.println("Invalid Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawGUI() {
        
    }
}
