package de.frogger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles the sound management in the game
 * plays, loops or stops wav files
 */
public class Sound {

    private Clip clip;

    /*
     * If the file exists, AudioInputStream
     * is generated and new Clip is opened
    */
    public void setFile(String fileName) {

        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            }
            else {
                throw new RuntimeException("Error, missing file: " + fileName);
            }
        }
        // These Exceptions must be present
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error, URL is not correct: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Error unsupported file: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("I/O Error " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Error, Line unavailable: " + e);
        }
    }
    // Plays song once
    public void play(){
        clip.setFramePosition(0);
        clip.start();
    }
    // Loops song
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /*
     * Stops song and closes Clip so that another song
     * can be played
     */
    public void stopSound(){
        clip.stop();
        clip.drain();
        clip.close();
    }

}
