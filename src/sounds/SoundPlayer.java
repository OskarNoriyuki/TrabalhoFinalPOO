package sounds;

import java.io.*;
import javax.sound.sampled.*;

public final class SoundPlayer {

    private static Clip clipLoop;

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

    public static void tocarLoop(String audio){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/sounds/"+audio).getAbsoluteFile());
            clipLoop = AudioSystem.getClip();
            clipLoop.open(audioInputStream);
            clipLoop.start();
            clipLoop.loop(Clip.LOOP_CONTINUOUSLY);

         }catch(Exception e1){
             System.out.println(e1.getMessage());
         }
    }

    public static void pararLoop(){
        try{
            clipLoop.stop();

         }catch(Exception e1){
             System.out.println(e1.getMessage());
         }
    }
}
