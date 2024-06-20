import java.net.Socket;
import javax.swing.JOptionPane;
public class Start {
    static String port = "5000";
    public static void main(String[] args) {
        
        String ip = JOptionPane.showInputDialog("Enter The Server Ip Address");
        Start stratProgram =new Start();
        stratProgram.CreateSocket(ip,Integer.parseInt(port));
        AudioReceiver audioReceiver = new AudioReceiver(ip);
        audioReceiver.start();

    }
    public  void CreateSocket(String ip , int port) {
        
        try {
            Socket socket1 = new Socket(ip,port);
            System.out.println("Connecting  to  Server");
            Authentication auth = new Authentication(socket1);
            auth.setSize(300,80);
            auth.setLocation(500,300);
            auth.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
