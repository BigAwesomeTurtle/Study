package core;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {

    public static void playMusic(String file) {
        Clip clipSound;
        try {
            File f = new File(file);
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
            AudioFormat af = aff.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, af);
            if (AudioSystem.isLineSupported(info)) {
                clipSound = (Clip) AudioSystem.getLine(info);
                AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                clipSound.open(ais);
                clipSound.start();
            } else {
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
