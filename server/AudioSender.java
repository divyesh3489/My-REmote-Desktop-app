import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.*;

public class AudioSender extends Thread {
    private static final int AUDIO_PORT = 5001; // Different port for audio

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(AUDIO_PORT)) {
            System.out.println("Audio Server is running...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Audio Client connected!");

            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            OutputStream out = clientSocket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = line.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            line.close();
            out.close();
            clientSocket.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
