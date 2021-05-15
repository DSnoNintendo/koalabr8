package koalabr8.game;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio
{
    String path;



    void play(AudioInputStream sound) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.start();
                return;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    void playClip(AudioInputStream sound) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.setFramePosition(0);
                clip.open(sound);
                clip.start();
                Thread.sleep(1000);
                clip.stop();
                Thread.currentThread().stop();
                return;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }).start();

    }

}
