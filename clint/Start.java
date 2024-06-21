import java.net.Socket;
import javax.swing.JOptionPane;

public class Start {
    static String port = "5000";

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog(null, "Enter the Server IP Address:", "Server IP", JOptionPane.QUESTION_MESSAGE);
        Start startProgram = new Start();
        startProgram.createSocket(ip, Integer.parseInt(port));
    }

    public void createSocket(String ip, int port) {
        try {
            Socket socket1 = new Socket(ip, port);
            System.out.println("Connecting to Server");
            Authentication auth = new Authentication(socket1);
            auth.setSize(400, 150);
            auth.setLocationRelativeTo(null);
            auth.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
