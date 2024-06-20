import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class CreateFrame extends Thread {
    private JFrame frame;
    private JPanel panel;
    private JLabel statusLabel;
    private Socket clientSocket;
    private String width;
    private String height;

    public CreateFrame(Socket clientSocket, String width, String height) {
        this.clientSocket = clientSocket;
        this.width = width;
        this.height = height;

        frame = new JFrame("Remote Desktop Client");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        statusLabel = new JLabel("Connected to Server", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(statusLabel, BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        start();
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            new ReceivingScreen(input, panel);
            new SendEvents(clientSocket, panel, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
