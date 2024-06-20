import javax.sound.sampled.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class AudioSender extends Thread {
    private Socket socket;
    private boolean running = true;

    public AudioSender(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        try (TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
                OutputStream outputStream = socket.getOutputStream()) {

            microphone.open(format);
            microphone.start();

            byte[] buffer = new byte[4096];
            while (running) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
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
