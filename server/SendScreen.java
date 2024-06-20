import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;

public class SendScreen extends Thread {
    Socket cs = null;
    Robot robot = null;
    Rectangle rect = null;
    boolean loopFlag = true;
    OutputStream outstream = null;
    AudioCapture audioCapture;

    public SendScreen(Socket cs, Robot robot, Rectangle rect) {
        this.cs = cs;
        this.robot = robot;
        this.rect = rect;
        this.audioCapture = new AudioCapture(); // Initialize audio capture
        this.start();
    }

    public void run() {
        try {
            outstream = cs.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (this.loopFlag) {
            // Capture screen
            BufferedImage bImg = robot.createScreenCapture(rect);

            // Convert image to byte array and send
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bImg, "jpeg", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                outstream.write(imageBytes);
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Capture audio
            byte[] audioBuffer = new byte[4096]; // Adjust buffer size as necessary
            int bytesRead = audioCapture.read(audioBuffer, 0, audioBuffer.length);
            if (bytesRead > 0) {
                try {
                    outstream.write(audioBuffer, 0, bytesRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(10); // Adjust delay as necessary
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
