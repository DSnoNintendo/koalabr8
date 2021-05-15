/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koalabr8.game;


import koalabr8.GameConstants;
import koalabr8.Launcher;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

import static javax.imageio.ImageIO.read;
import static koalabr8.GameConstants.*;


public class Gameplay extends JPanel implements Runnable {
    private BufferedImage world;
    private BufferedImage background;
    private BufferedImage koalaimg_up;
    private BufferedImage koalaimg_down;
    private BufferedImage koalaimg_left;
    private BufferedImage koalaimg_right;
    private BufferedImage koalaimg_stand;
    private BufferedImage koalaimg_rescued;
    private BufferedImage koalaimg_dead;
    private BufferedImage restartimg;
    private BufferedImage congratsimg;
    private Audio bgMusic = new Audio();
    private int mouseX;
    private int mouseY;
    private int count;
    private JButton restart = new JButton();
    private ArrayList<Collidable> collidables;
    Koala k1;
    Koala k2;
    Koala k3;
    ArrayList<Koala> koalas = new ArrayList<>();
    MapLayout map;
    boolean allSaved = false;
    boolean winSoundPlayed = false;


    private Launcher lf;
    private long tick = 0;



    public Gameplay(Launcher lf){
        this.lf = lf;
        loadResources();
        GameSounds.playMusic();
    }

    void loadResources() {
        try {
            background = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Background.bmp")));
            map = new MapLayout(Gameplay.class.getClassLoader().getResourceAsStream("map.txt"));
            koalaimg_up = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_up.png")));
            koalaimg_right = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_right.png")));
            koalaimg_left = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_left.png")));
            koalaimg_down = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_down.png")));
            koalaimg_stand = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("koala_stand.png")));
            koalaimg_rescued = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_rescued.png")));
            koalaimg_dead = read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Koala_dead.png")));
            restartimg = ImageIO.read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("restart.png")));
            congratsimg = ImageIO.read(Objects.requireNonNull(Gameplay.class.getClassLoader().getResource("Congratulation.png")));
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
               this.tick++;

               if (MoveStack.size() > 0) {
                   handleMoves();
               }

               for (Koala k : koalas) {
                   k.update();
                   this.map.checkCollision(k);
                   k.checkCollision(koalas);
                   if (k.isDead()) {
                       resetGame();
                   }
               }



                /*
               if (this.tick > 5000) {
                   this.lf.setFrame("p1win");
                   return;
               }

                 */



               this.repaint();   // redraw game

               Thread.sleep(1000 / 144); //sleep for a few milliseconds
           }



       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    public void handleMoves() {
        while (!MoveStack.isEmpty()) {
            Move move = MoveStack.pop();
            move.execute();
            // if move is not done executing, put it back in the stack
            if (move.getTicks() > 0) {
                MoveStack.pushToTemp(move);
            }
            else {
                move.getMover().stopMoving();
            }
        }
        MoveStack.update();
    }

    public void resetGame(){
        this.tick = 0;
        this.allSaved = false;
        this.winSoundPlayed = false;

        for (Koala k : koalas) { k.reset(); }

        for (Collidable c : map.getCollidables()) {
            if (c instanceof Switch) {
                ((Switch) c).reset();
            }
            else if (c instanceof Exit) {
                ((Exit) c).reset();
            }
        }

    }

    boolean checkForWin() {
        for (Koala k : this.koalas) {
            if (!k.isRescued()) {
                return false;
            }
        }
        return true;
    }

    public void gameInitialize() {
        this.world = new BufferedImage(GAME_SCREEN_WIDTH,
                GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);



        /*
        restart.addActionListener((actionEvent -> {
            this.resetGame();
        }));
        restart.setBounds(world.getWidth() -restartimg.getWidth(),0,100,40);
        this.add(restart);
         */


        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                mouseX = me.getX();
                mouseY = me.getY();

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println(mouseX + " " + mouseY);
                Point clicked = me.getPoint();

                Rectangle bounds = new Rectangle(540, 0, restartimg.getWidth(), restartimg.getHeight());
                if (bounds.contains(clicked)) {
                    System.out.println("reset");
                    resetGame();
                }
            }
        });

        collidables = map.buildMap();
        k1 = new Koala(40,205,0,0,0,koalaimg_up,koalaimg_down,koalaimg_right,koalaimg_left,koalaimg_stand);
        k2 = new Koala(40,125,0,0,0,koalaimg_up,koalaimg_down,koalaimg_right,koalaimg_left,koalaimg_stand);
        k3 = new Koala(40,285,0,0,0,koalaimg_up,koalaimg_down,koalaimg_right,koalaimg_left,koalaimg_stand);
        KoalaControl kc1 = new KoalaControl(k1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        KoalaControl kc2 = new KoalaControl(k2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        KoalaControl kc3 = new KoalaControl(k3, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);


        koalas.add(k1);
        koalas.add(k2);
        koalas.add(k3);

        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(kc1);
        this.lf.getJf().addKeyListener(kc2);
        this.lf.getJf().addKeyListener(kc3);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        buffer.drawImage(this.background, 0, 40, null);
        buffer.drawImage(this.restartimg, 540, 0, null);

        for (Collidable c : collidables) {
            c.drawImage(buffer);
        }

        this.k1.drawImage(buffer);
        this.k2.drawImage(buffer);
        this.k3.drawImage(buffer);

        int counter = 0;

        for (Koala k : koalas) {
            if (k.isRescued()) {
                buffer.drawImage(this.koalaimg_rescued,counter,0,null);
            }
            else if (k.isDead()) {
                buffer.drawImage(this.koalaimg_dead,counter,0,null);
            }
            else {
                buffer.drawImage(this.koalaimg_stand,counter,0,null);
            }
            counter += 40;
        }

        allSaved = checkForWin();

        if (allSaved) { buffer.drawImage(this.congratsimg,100,115,null); }

        if (allSaved && !winSoundPlayed) {
            GameSounds.playYay();
            winSoundPlayed = true;
        }


        g2.drawImage(world,0,0,null);
    }

}
