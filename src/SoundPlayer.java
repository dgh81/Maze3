import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;


public class SoundPlayer {
    void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                // clip.loop(Clip.LOOP_CONTINUOUSLY);
                // clip.stop();
                // JOptionPane.showMessageDialog(null, "Press OK to stop playing");
            } else {
                System.out.println("Filen blev ikke fundet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// 16 træd i vand
//157
// 193 : gabe
// 102 103 åbn døre
    public static void main(String[] args) {
        // String filepath = "sound.wav";
//        String filepath = "soundStepInWater.wav";
//        String filepath = "soundStepOnDirt.wav";

        String filepath = "soundStepInWater.wav";
        SoundPlayer myPlayer = new SoundPlayer();
        myPlayer.playMusic(filepath);
    }
}