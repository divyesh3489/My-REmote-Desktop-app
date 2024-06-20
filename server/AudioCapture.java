import javax.sound.sampled.*;

public class AudioCapture {
    private TargetDataLine line;
    private AudioFormat audioFormat;

    public AudioCapture() {
        try {
            // Define audio format
            audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100, 16, 2, 4, 44100, false);

            // Get line for capture
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to read audio data
    public int read(byte[] buffer, int offset, int bufferSize) {
        return line.read(buffer, offset, bufferSize);
    }

    // Method to close the capture line
    public void close() {
        line.stop();
        line.close();
    }
}
