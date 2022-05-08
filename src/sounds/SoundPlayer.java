package sounds;

import java.io.*;
import javax.sound.sampled.*;

public final class SoundPlayer {
    private SoundPlayer () {
    }

    public static void tocarSom(String audio){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sounds/"+audio).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

         }catch(Exception e1){
             System.out.println(e1.getMessage());
         }
    }
}
