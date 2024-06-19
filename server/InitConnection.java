
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class InitConnection
{
    ServerSocket socket = null;
    DataInputStream password = null;
    DataOutputStream verify = null;
    String width = "";
    String height = "";
    InitConnection(int port,String setPassword)
    {
        Robot robot = null;
        Rectangle rect = null;
        try{
            System.out.println("Wating For Connation from Clint");
            socket = new ServerSocket(port);
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDivce = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = "" + dim.getWidth();
            height = "" + dim.getHeight();
            rect = new Rectangle(dim);
            robot = new Robot(gDivce);
            drawGUI();
            while (true) { 
                Socket cs = socket.accept();
                password = new DataInputStream(cs.getInputStream());
                verify = new DataOutputStream(cs.getOutputStream());
                String clintPassword = password.readUTF();
                if(clintPassword.equals(setPassword)) 
                {
                    verify.writeUTF("Valid");
                    verify.writeUTF(width);
                    verify.writeUTF(height);
                    new SendScreen(cs,robot,rect);
                    new ReceiveEvent(cs,robot);
                
                }
                else
                {
                    verify.writeUTF("Invalid Password");
                    System.out.println("Invalid Password");
                }
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private  void drawGUI()
    {

    }
}

