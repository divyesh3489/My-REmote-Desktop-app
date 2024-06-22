import java.awt.BorderLayout;
import java.awt.Font;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Start extends JFrame {
    static String port = "5000";
    JLabel statusLabel;
    public void createFrame(String ip)
    {
        statusLabel = new JLabel("Connect to Ip:" + ip);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.setLayout(new BorderLayout());
        this.add(statusLabel, BorderLayout.CENTER);
        this.setTitle("Connection Status");
        this.setSize(400, 150);
        this.setLocation(500, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        Start startProgram = new Start();
        boolean connected = false;
        while(!connected)
        {
            String ip = JOptionPane.showInputDialog(null, "Enter the Server IP Address:", "Server IP", JOptionPane.QUESTION_MESSAGE);
            if (ip == null)
            {
                startProgram.createFrame(ip);
                startProgram.createSocket(ip, Integer.parseInt(port));
                connected = startProgram.createSocket(ip, Integer.parseInt(port));
            }
        }

    }

    public boolean createSocket(String ip, int port) {
        try {
            Socket socket1 = new Socket(ip, port);
            this.setVisible(false);
            this.remove(this);
            this.repaint();
            this.revalidate();
            System.out.println("Connecting to Server");
            Authentication auth = new Authentication(socket1);
            auth.setSize(400, 150);
            auth.setLocationRelativeTo(null);
            auth.setVisible(true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
