package koalabr8.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Switch implements Collidable{
    private int x;
    private int y;
    private boolean activated = false;
    private Lock lock;


    BufferedImage img;
    BufferedImage activatedimg;
    BufferedImage deactivatedimg;
    BufferedImage img2;

    private Rectangle hitbox;



    Switch(int x, int y, BufferedImage img, BufferedImage img2) {
        this.x = x;
        this.y = y;

        this.deactivatedimg = img;
        this.activatedimg = img2;
        this.img = deactivatedimg;
        this.hitbox = new Rectangle(x,y,img.getWidth(),img.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void drawImage(Graphics2D g) {
        //g2d.setColor(Color.CYAN);
        g.drawImage(this.img, this.x,this.y, null);
    }

    public void checkCollision(Collidable c) {

    }

    public void setLock(Lock l) {
        this.lock = l;
    }

    void reset() {
        activated = false;
        img = deactivatedimg;
        this.lock.reset();
    }

    public void activate() {
        if (activated) {
            this.activated = false;
            this.img = deactivatedimg;
        }
        else {
            this.activated = true;
            this.img = activatedimg;
        }

        this.lock.activate();

    }

    public boolean isActivated() { return activated; }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void setX(int n) {
        this.x = n;
    }
}
