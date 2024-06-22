import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class ConnectionStatusFrame extends JFrame {
    private JLabel statusLabel;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private String password;
    private int port;

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
        this.port = port;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startServer();
    }
    
    private void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            new ConnectionHandler().start();
        } catch (IOException e) {
            statusLabel.setText("Failed to start server on port " + port);
            e.printStackTrace();
        }
    }

    public void restartServer() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ConnectionHandler extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    statusLabel.setText("Waiting for client to connect...");
                    Socket clientSocket = serverSocket.accept();
                    statusLabel.setText("Client connected successfully! Authenticating... ");
                    handleClient(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleClient(Socket clientSocket) {
            try {
                new InitConnection(clientSocket, password, statusLabel);
            } catch (Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
