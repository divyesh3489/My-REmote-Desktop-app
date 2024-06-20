import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.sound.sampled.*;

public class AudioReceiver extends Thread {
    private String serverIp;

    public AudioReceiver(String serverIp) {
        this.serverIp = serverIp;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(serverIp, 5001)) {
            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            InputStream in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
