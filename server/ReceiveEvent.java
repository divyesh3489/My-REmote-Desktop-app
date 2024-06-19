
import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvent extends  Thread {

    Socket socket = null;
    Robot robot = null;
    boolean loopFlag = true;

    public ReceiveEvent(Socket cs,Robot robot) {

        this.socket = cs;
        this.robot = robot;
        this.start();
        
    }

    public void run(){

        Scanner sc = null;
        try {
            System.out.println(socket.getInputStream().read());
            sc = new Scanner(socket.getInputStream());
            while (this.loopFlag) {
                int command = sc.nextInt();
                switch (command) {
                    case-1:
                        robot.mousePress(sc.nextInt());
                        break;
                    case-2:
                        robot.mouseRelease(sc.nextInt());
                        break;
                    case-3:
                        robot.keyPress(sc.nextInt());
                        break;
                    case-4:
                        robot.keyRelease(sc.nextInt());
                        break;
                    case-5:
                        robot.mouseMove(sc.nextInt(), sc.nextInt());
                        break;
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
} 