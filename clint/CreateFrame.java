import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class CreateFrame extends Thread {

    String width = "";
    String height = "";
    private JFrame frame = new JFrame();
    private JDesktopPane desktop = new JDesktopPane();
    private Socket cSocket = null;
    private JInternalFrame internalFrame = new JInternalFrame("Server Screen",true,true,true); 
    private JPanel cPanel = new JPanel();

    public CreateFrame(Socket cSocket , String width,String  height) {
    
        this.cSocket = cSocket;
        this.height = height;
        this.width = width;
        this.start();
    
    }
    
    public void drowGUI(){
        
        frame.add(desktop,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(frame.getExtendedState() | JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());
        internalFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        internalFrame.setSize(100,100);
        desktop.add(internalFrame);
        try{
            internalFrame.setMaximum(true);
        }
        catch (PropertyVetoException e){
            e.printStackTrace();
        }
        cPanel.setFocusable(true);
        internalFrame.setVisible(true);
    
    }
    
    public void run (){
         
        InputStream input = null;
        drowGUI();
        try{
            input = cSocket.getInputStream();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        new ReceivingScreen(input , cPanel);
        new SendEvents(cSocket,cPanel,width,height);
    
    }

}
