import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class InitConnection {
    private Socket clientSocket;
    private String password;
    private JLabel statusLabel;

    public InitConnection(Socket clientSocket, String password, JLabel statusLabel) {
        this.clientSocket = clientSocket;
        this.password = password;
        this.statusLabel = statusLabel;
        handleConnection();
    }

    private void handleConnection() {
        try {
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            String receivedPassword = input.readUTF();
            if (receivedPassword.equals(password)) {
                output.writeBoolean(true);
                statusLabel.setText("Authentication successful. Connection established.");
                new SendScreen(clientSocket, statusLabel);  // Add appropriate constructor for SendScreen
            } else {
                output.writeBoolean(false);
                statusLabel.setText("Authentication failed. Incorrect password.");
                clientSocket.close();
            }
        } catch (IOException e) {
            statusLabel.setText("Connection error.");
            e.printStackTrace();
        }
    }
}
    