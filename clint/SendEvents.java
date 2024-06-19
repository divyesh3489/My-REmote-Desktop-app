
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;


public class SendEvents implements  KeyListener,MouseMotionListener,MouseListener {
    
private Socket cSocket = null;
private JPanel  cPanel = null;
private PrintWriter writer = null;
String width = "";
String height = "";
double w;
double h;
    public SendEvents(Socket cSocket,JPanel  cPanel ,String width, String height)
    {
        this.cSocket = cSocket;
        this.cPanel = cPanel;
        this.width = width;
        this.height = height;
        this.w = Double.valueOf(width.trim()).doubleValue();
        this.h = Double.valueOf(height.trim()).doubleValue();
        cPanel.addKeyListener(this);
        cPanel.addMouseListener(this);
        cPanel.addMouseMotionListener(this);
        try
        {
            writer = new PrintWriter(cSocket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void mouseDragged(MouseEvent e)
    {

    }
    public void mouseMoved(MouseEvent e)
    {
        double x = (double)this.w/cPanel.getWidth();
        double y = (double) this.h/cPanel.getHeight();
        writer.println(Command.MOVE_MOUSE.getAbbev());
        writer.println((int) e.getX()*x);
        writer.println((int) e.getY()*y);
        writer.flush();
    }
    public void mouseClicked (MouseEvent e)
    {

    }
    public void mousePressed(MouseEvent e)
    {
        writer.println(Command.PRESS_MOUSE.getAbbev());
        int button = e.getButton();
        int xButton = 16;
        if (button == 3)
        {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }
    public void mouseReleased(MouseEvent e)
    
    {
    writer.println(Command.RELEASE_MOUSE.getAbbev());
            int button = e.getButton();
            int xButton = 16;
            if (button == 3)
            {
                xButton = 4;
            }
            writer.println(xButton);
            writer.flush();
    }
    public void mouseEntered(MouseEvent e)
    {

    }
    public void mouseExited(MouseEvent e)
    { 
        

    }
    public void keyTyped(KeyEvent e)
    {

    }
    public void keyPressed(KeyEvent e)
    {
        writer.println(Command.PRESS_KEY.getAbbev());
        writer.println(e.getKeyCode());
        writer.flush();
    }
    public void keyReleased(KeyEvent e)
    {
        writer.println(Command.RELEASE_KEY.getAbbev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    


}
