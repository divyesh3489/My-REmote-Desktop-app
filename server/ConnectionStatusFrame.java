import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionStatusFrame extends JFrame {
    private JLabel statusLabel;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String password;

    public ConnectionStatusFrame(int port, String password) {
        this.password = password;
        statusLabel = new JLabel("Waiting for client to connect...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        this.setLayout(new BorderLayout());
        this.add(statusLabel, BorderLayout.CENTER);
        this.setTitle("Connection Status");
        this.setSize(400, 150);
        this.setLocation(500, 300);
        this.setVisible(true);

        new ConnectionHandler(port, password).start();
    }

    private class ConnectionHandler extends Thread {
        private int port;
        private String password;

        public ConnectionHandler(int port, String password) {
            this.port = port;
            this.password = password;
        }

        public void run() {
            try {
                serverSocket = new ServerSocket(port);
                clientSocket = serverSocket.accept();
                statusLabel.setText("Client connected. Authenticating...");
                new InitConnection(clientSocket, password, statusLabel);
            } catch (IOException e) {
                statusLabel.setText("Connection failed.");
                e.printStackTrace();
            }
        }
    }
}
