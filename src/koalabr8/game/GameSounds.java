package koalabr8.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.util.Objects;

public class GameSounds {
    public static AudioInputStream GAME_MUSIC;
    public static AudioInputStream SAW_SOUND;
    public static AudioInputStream LOCK_SOUND;
    public static AudioInputStream SAVE_SOUND;
    public static AudioInputStream YAY_SOUND;




    static void playClip(AudioInputStream sound) {
        //initializeSounds();
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(sound);
                clip.setFramePosition(0);
                clip.start();
                Thread.sleep(4000);
                clip.stop();
                clip.close();
                Thread.currentThread().interrupt();

            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        return;
    }

    static void playSaw() {
        try {
            BufferedInputStream stream = new BufferedInputStream((Objects.requireNonNull(GameSounds.class.getClassLoader().getResourceAsStream("Saw.wav"))));
            SAW_SOUND = AudioSystem.getAudioInputStream(stream);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        playClip(SAW_SOUND);
    }

    static void playMusic() {
        new Thread(() -> {
            try {
                while (true) {
                    BufferedInputStream stream = new BufferedInputStream((Objects.requireNonNull(GameSounds.class.getClassLoader().getResourceAsStream("Music.mid"))));
                    GAME_MUSIC = AudioSystem.getAudioInputStream(stream);
                    Clip clip = AudioSystem.getClip();
                    clip.open(GAME_MUSIC);
                    clip.setFramePosition(0);
                    clip.start();
                    Thread.sleep(210000);
                    clip.stop();
                    clip.close();
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }).start();
        return;
    }

    static void playLock() {
        try {
            BufferedInputStream stream = new BufferedInputStream((Objects.requireNonNull(GameSounds.class.getClassLoader().getResourceAsStream("Lock.wav"))));
            LOCK_SOUND = AudioSystem.getAudioInputStream(stream);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        playClip(LOCK_SOUND);
    }

    static void playRescue() {
        try {
            BufferedInputStream stream = new BufferedInputStream((Objects.requireNonNull(GameSounds.class.getClassLoader().getResourceAsStream("Saved.wav"))));
            SAVE_SOUND = AudioSystem.getAudioInputStream(stream);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        playClip(SAVE_SOUND);
    }

    static void playYay() {
        try {
            BufferedInputStream stream = new BufferedInputStream((Objects.requireNonNull(GameSounds.class.getClassLoader().getResourceAsStream("Congratulation.wav"))));
            YAY_SOUND = AudioSystem.getAudioInputStream(stream);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        playClip(YAY_SOUND);
    }
}
