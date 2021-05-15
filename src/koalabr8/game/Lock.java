package koalabr8.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lock implements Collidable{
    private int x;
    private int y;
    private boolean locked = true;


    BufferedImage img;
    BufferedImage lockedimg;
    BufferedImage transparent;

    private Rectangle hitbox;



    Lock(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;

        this.lockedimg = img;
        this.hitbox = new Rectangle(x,y,img.getWidth(),img.getHeight());
        this.locked = true;
        this.img = lockedimg;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void drawImage(Graphics2D g) {
        //g2d.setColor(Color.CYAN);
        g.drawImage(this.img, this.x,this.y, null);
    }

    void reset() {
        img = lockedimg;
        locked = true;
    }

    public void activate() {
        GameSounds.playLock();
        if ( !locked ) {
            this.locked = true;
            this.img = this.lockedimg;
        }
        else {
            this.locked = false;
            this.img = this.transparent;
        }
    }

    public boolean isLocked() { return locked; }

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
