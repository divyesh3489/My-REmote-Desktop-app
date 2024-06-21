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
    private boolean  flag = true;
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
        panel.add(statusLabel);
        panel.add(passwordField);
        panel.add(submitButton);

        this.add(this.panel , BorderLayout.CENTER);
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
            System.out.println(verify);
            if (verify) {
                String width = verification.readUTF();
                String height = verification.readUTF();
                new CreateFrame(clientSocket, width, height);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password is invalid, Please Enter Valid password", "Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                passwordField.requestFocus();
                this.remove(this.panel);
                this.revalidate();
                this.repaint();
                Start.main(new String[0]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
