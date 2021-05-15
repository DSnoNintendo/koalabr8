package koalabr8;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class GameConstants {
    public static final int GAME_SCREEN_WIDTH = 640;
    public static final int GAME_SCREEN_HEIGHT = 515;

    public static final int WORLD_WIDTH = 1600;
    public static final int WORLD_HEIGHT = 1200;

    public static final int TILE_SIZE = 40;

    public static final int START_MENU_SCREEN_WIDTH = 500;
    public static final int START_MENU_SCREEN_HEIGHT = 500;

    public static final int END_MENU_SCREEN_WIDTH = 500;
    public static final int END_MENU_SCREEN_HEIGHT = 500;

    static void playClip(AudioInputStream sound) {
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