import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class AudioReceiver extends Thread {
    private Socket socket;
    private boolean running = true;

    public AudioReceiver(String serverIp, int port) {
        try {
            socket = new Socket(serverIp, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try (SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
             InputStream inputStream = socket.getInputStream()) {

            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[4096];
            while (running) {
                int bytesRead = inputStream.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    speakers.write(buffer, 0, bytesRead);
                }
            }
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public void stopRunning() {
        running = false;
    }
}
