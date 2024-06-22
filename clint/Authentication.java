import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class Authentication extends JFrame implements ActionListener {
    private Socket clientSocket;
    private DataOutputStream checkPass;
    private DataInputStream verification;
    private boolean verify;
    private JLabel statusLabel;
    private JTextField passwordField;
    private JPanel panel;

    public Authentication(Socket clientSocket) {
        this.clientSocket = clientSocket;
        statusLabel = new JLabel("Enter Password:");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField = new JTextField(15);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        this.panel = new JPanel(new GridLayout(3, 1));
        this.panel.add(statusLabel);
        this.panel.add(passwordField);
        this.panel.add(submitButton);

        this.add(this.panel, BorderLayout.CENTER);
        this.setTitle("Authenticate");
        this.setSize(300, 150);
        this.setLocationRelativeTo(null); // Center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String getPasswordvalue = passwordField.getText();
        try {
            checkPass = new DataOutputStream(clientSocket.getOutputStream());
            verification = new DataInputStream(clientSocket.getInputStream());
            checkPass.writeUTF(getPasswordvalue);
            verify = verification.readBoolean();
            if (verify) {
                String width = verification.readUTF();
                String height = verification.readUTF();
                new CreateFrame(clientSocket, width, height);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password is invalid, Please Connect the Ip and fill the Password again.", "Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                clientSocket.close();
                dispose();
                this.panel.removeAll();
                this.panel.revalidate();
                this.panel.repaint();
                Start.main(new String[0]);
            }
        } catch (IOException ex) {
            System.out.println("Error in Authentication actionPerformed: " + ex);
        }
    }
}
