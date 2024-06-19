
import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvent extends  Thread
{
    Socket cs = null;
    Robot robot = null;
    boolean loopFlag = true;

    public ReceiveEvent(Socket cs,Robot robot) {
        this.cs = cs;
        this.robot = robot;
        this.start();
        
    }
    public void run()
    {
        Scanner sc = null;
        try {
            sc = new Scanner(cs.getInputStream());
            while (this.loopFlag) {
                System.out.println(sc.hasNext());
                if (sc.hasNext())
                {
                    int command = Integer.parseInt(sc.next());
                    switch (command) {
                        case -1:
                            robot.mousePress(Integer.parseInt(sc.next()));
                            break;
                        case -2:
                            robot.mouseRelease(Integer.parseInt(sc.next()));
                            break;
                        case -3:
                            robot.keyPress(Integer.parseInt(sc.next()));
                            break;
                        case -4:
                            robot.keyRelease(Integer.parseInt(sc.next()));
                            break;
                        case -5:
                            robot.mouseMove(Integer.parseInt(sc.next()), Integer.parseInt(sc.next()));
                            break;
                        default:
                            throw new AssertionError();
                    }
                }
                
                
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            // e.printStackTrace();

        }
    }
    
} 