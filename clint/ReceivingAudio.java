import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.*;

public class ReceivingAudio extends Thread {
    private final InputStream inputStream;
    private SourceDataLine line;

    public ReceivingAudio(InputStream inputStream) {
        this.inputStream = inputStream;
        this.start();
    }

    public void run() {
        try {
            // Define audio format
            AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100, 16, 2, 4, 44100, false);

            // Get line for playback
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();

            // Read and play audio
            byte[] buffer = new byte[4096]; // Adjust buffer size as necessary
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Clean up
            line.drain();
            line.stop();
            line.close();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
