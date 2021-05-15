package koalabr8.game;

import koalabr8.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;

public class Koala implements Collidable{


    private int x;
    private int y;
    private int vx;
    private int vy;
    private String direction = "D";
    private float angle;
    private int spawnX;
    private int spawnY;
    private boolean moving = false;
    private boolean rescued = false;
    private boolean dead = false;


    private final int R = 2;
    private final float ROTATIONSPEED = 3.0f;

    private Rectangle hitbox;


    private BufferedImage img_up;
    private BufferedImage img_down;
    private BufferedImage img_right;
    private BufferedImage img_left;
    private BufferedImage img_stand;
    private BufferedImage img;
    private BufferedImage transparent;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;



    Koala(int x, int y, int vx, int vy, int angle,
          BufferedImage img_up, BufferedImage img_down, BufferedImage img_right,
          BufferedImage img_left, BufferedImage img_stand) {
        this.spawnX = x;
        this.spawnY = y;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img_up = img_up;
        this.img_down = img_down;
        this.img_right = img_right;
        this.img_left = img_left;
        this.img_stand = img_stand;
        this.img = img_stand;
        this.angle = angle;
        this.hitbox = new Rectangle(x,y,this.img_up.getHeight(),this.img_up.getWidth());
    }

    void save() {
        rescued = true;
        this.img = transparent;
    }

    void kill() {
        dead = true;
        this.img = transparent;
    }

    void respawn() {
        this.x = spawnX;
        this.y = spawnY;
        this.update();
    }

    boolean isRescued() { return rescued; }

    boolean isDead() { return dead; }

    boolean isMoving() { return moving; }

    void reset() {
        respawn();
        img = img_stand;
        moving = false;
        rescued = false;
        dead = false;
    }

    public void setX(int x){ this.x = x; }

    public void setY(int y) { this. y = y;}

    public int getX() { return this.x; }

    public int getY() { return this.y; }

    void toggleUpPressed() {
        if (!moving && !rescued && !dead) {
            MoveStack.push(new Move(this, "U"));
            unToggleUpPressed();
            moving = true;
            setDirection("U");
        }
    }

    void toggleDownPressed() {
        if (!moving && !rescued && !dead) {
            MoveStack.push(new Move(this, "D"));
            unToggleDownPressed();
            moving = true;
            setDirection("D");
        }
    }

    void toggleRightPressed() {
        if (!moving && !rescued && !dead) {
            setDirection("R");
            MoveStack.push(new Move(this, direction));
            unToggleRightPressed();
            moving = true;

        }

    }

    void toggleLeftPressed() {
        if (!moving && !rescued && !dead) {
            setDirection("L");
            MoveStack.push(new Move(this, direction));
            unToggleLeftPressed();
            moving = true;

        }
    }

    void toggleActivatePressed() {
        if (!moving && !rescued && !dead) {
            MoveStack.push(new Activate(this,""));
            untoggleActivatePressed();
            moving = true;
        }
    }

    public String getDirection() { return direction; }

    public void setDirection(String d) {
        this.direction = d;
    }

    void untoggleActivatePressed() {
        this.shootPressed = false;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
        //setStationary();
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
        setStationary();
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
        setStationary();
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
        setStationary();
    }

    public Rectangle getHitbox() { return hitbox.getBounds(); }


    void update() {
        hitbox.setLocation(x,y);
    }

    public void stopMoving() {
        this.moving = false;
    }

    public void moveBackwards() {
        y += 1;
        if (!rescued && !dead) { this.img = img_down; }
        checkBorder();
    }

    public void moveLeft() {
        x -= 1;
        if (!rescued && !dead) { this.img = img_left; }
        checkBorder();
    }

    public void moveRight() {
        x += 1;
        if (!rescued && !dead) { this.img = img_right; }
        checkBorder();
    }


    public void moveForwards() {
        y -= 1;
        if (!rescued && !dead) { this.img = img_up; }
        checkBorder();
    }

    public void setStationary() {
        this.img = img_stand;
    }


    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameConstants.WORLD_WIDTH - 88) {
            x = GameConstants.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameConstants.WORLD_HEIGHT - 80) {
            y = GameConstants.WORLD_HEIGHT - 80;
        }
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.img, rotation, null);

    }

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

    public void checkCollision(ArrayList<Koala> koalas) {
        for (Koala k : koalas) {
            if ( this.getHitbox().intersects(k.getHitbox()) &&
                !this.getHitbox().equals(k.getHitbox()) &&
                !k.isDead() && !k.isRescued()) {
                handleIntersection(k, this);
            }
        }
    }
}