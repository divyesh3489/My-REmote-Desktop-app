import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
    private ServerSocket screenSocket;
    private ServerSocket audioSocket;
    private String password;

    private static final int AUDIO_PORT = 5001;

    public InitConnection(int screenPort, String password) {
        this.password = password;
        try {
            screenSocket = new ServerSocket(screenPort);
            audioSocket = new ServerSocket(AUDIO_PORT);
            waitForConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() {
        try {
            while (true) {
                Socket screenClientSocket = screenSocket.accept();
                Socket audioClientSocket = audioSocket.accept();

                authenticateAndStartSession(screenClientSocket, audioClientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticateAndStartSession(Socket screenSocket, Socket audioSocket) {
        try (DataInputStream in = new DataInputStream(screenSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(screenSocket.getOutputStream())) {

            String receivedPassword = in.readUTF();
            if (receivedPassword.equals(password)) {
                out.writeBoolean(true);
                Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
                out.writeUTF(String.valueOf(screenDim.width));
                out.writeUTF(String.valueOf(screenDim.height));

                Robot robot = new Robot(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
                Rectangle screenRect = new Rectangle(screenDim);
                new SendScreen(screenSocket, robot, screenRect);
                new ReceiveEvent(screenSocket, robot);

                // Start sending audio
                AudioSender audioSender = new AudioSender(audioSocket);
                audioSender.start();
            } else {
                out.writeBoolean(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
