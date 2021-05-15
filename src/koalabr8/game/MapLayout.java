package koalabr8.game;

import koalabr8.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

import static javax.imageio.ImageIO.read;

public class MapLayout {
    private InputStream maptxt;
    private Scanner reader;
    private BufferedImage wallimg;
    private BufferedImage switchRedOnimg;
    private BufferedImage switchRedOffimg;
    private BufferedImage switchBlueOnimg;
    private BufferedImage switchBlueOffimg;
    private BufferedImage lockRedimg;
    private BufferedImage lockBlueimg;
    private BufferedImage sawRightimg;
    private BufferedImage sawLeftimg;
    private BufferedImage sawRoundimg;
    private BufferedImage exitimg;
    private BufferedImage rockimg;
    static ArrayList<Collidable> collidables = new ArrayList<>();

    private Lock lockRed;
    private Lock lockBlue;
    private Switch switchRed;
    private Switch switchBlue;
    private Saw sawLeft;
    private Saw sawRight;
    private Saw sawRound;


    public MapLayout(InputStream txt) {

        this.maptxt = txt;
        reader = new Scanner(maptxt);



    }

    ArrayList<Collidable> buildMap() {

        int x;
        int y = 45;

        try {
            wallimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("wall.png")));
            switchRedOnimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("switch_red_on.png")));
            switchRedOffimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("switch_red_off.png")));
            switchBlueOnimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("switch_blue_on.png")));
            switchBlueOffimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("switch_blue_off.png")));
            lockRedimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("lock_red.png")));
            lockBlueimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("lock_blue.png")));
            sawRightimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("saw_right.png")));
            sawLeftimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("saw_left.png")));
            sawRoundimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("saw_round.png")));
            exitimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("exit.png")));
            rockimg = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("rock.gif")));
        } catch (Exception e) {
            System.out.println("image not found");
        }


        while (reader.hasNextLine()) {
            x = 0;
            String line = reader.nextLine();
            System.out.println(line);
            for (char c : line.toCharArray()) {
                switch(c) {
                    case '1':
                        collidables.add(new Wall(x, y, wallimg));
                        break;
                    case '2':
                        lockRed = new Lock(x, y, lockRedimg);
                        collidables.add(lockRed);
                        break;
                    case '3':
                        lockBlue = new Lock(x, y, lockBlueimg);
                        collidables.add(lockBlue);
                        break;
                    case '4':
                        switchRed = new Switch(x,y,switchRedOffimg,switchRedOnimg);
                        collidables.add(switchRed);
                        break;
                    case '5':
                        switchBlue = new Switch(x,y,switchBlueOffimg,switchBlueOnimg);
                        collidables.add(switchBlue);
                        break;
                    case '6':
                        sawLeft = new Saw(x,y,sawLeftimg);
                        collidables.add(sawLeft);
                        break;
                    case '7':
                        collidables.add(new Exit(x, y, exitimg));
                        break;
                    case '8':
                        sawRight = new Saw(x,y,sawRightimg);
                        collidables.add(sawRight);
                        break;
                    case '9':
                        sawRound = new Saw(x,y,sawRoundimg);
                        collidables.add(sawRound);
                        break;
                }

                x += GameConstants.TILE_SIZE;
            }
            y += GameConstants.TILE_SIZE;
        }
        reader.close();

        switchRed.setLock(lockRed);

        switchBlue.setLock(lockBlue);

        return collidables;
    }

    static ArrayList<Collidable> getCollidables() { return collidables; }

    void handleIntersection(Collidable collidable, Collidable c) {
        Rectangle intersection = collidable.getHitbox().intersection(c.getHitbox());
        if (intersection.height > intersection.width && c.getX() < intersection.x) { //left
            c.setX(c.getX() - intersection.width);
        } else if (intersection.height > intersection.width && c.getX() > collidable.getX()) { //right
            c.setX(c.getX() + intersection.width);
        } else if (intersection.height < intersection.width && c.getY() < intersection.y) { //up
            c.setY(c.getY() - intersection.height);
        } else if (intersection.height < intersection.width && c.getY() > collidable.getY()) { //down
            c.setY(c.getY() + intersection.height);
        }
    }

    void checkCollision(Collidable c) {
        Iterator<Collidable> itr = collidables.iterator();
        while (itr.hasNext()) {
            Collidable collidable = itr.next();
            if (collidable.getHitbox().intersects(c.getHitbox())) {


                if (c instanceof Koala) {

                    if (collidable instanceof Wall || collidable instanceof Switch ) {
                        handleIntersection(collidable, c);
                    }

                    else if (collidable instanceof Lock) {
                        if ( ((Lock) collidable).isLocked() ) {
                            handleIntersection(collidable, c);
                        }
                    }

                    else if (collidable instanceof Exit && !((Koala) c).isRescued()) {
                        if (!((Exit) collidable).isActivated()) {
                            if (collidable.getHitbox().equals(c.getHitbox())) {
                                ((Exit) collidable).activate();
                                ((Koala) c).save();
                                GameSounds.playRescue();
                            }
                        }

                    }

                    else if (collidable instanceof Saw && !((Koala) c).isDead()) {
                        if (collidable.getHitbox().equals(c.getHitbox())) {
                            GameSounds.playSaw();
                            ((Koala) c).kill();
                        }
                    }

                }
            }
        }
    }
}






